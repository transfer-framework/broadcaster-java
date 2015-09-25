package org.transfer.agent.messenger;

import com.google.gson.Gson;
import org.transfer.agent.meteor.Client;
import org.transfer.agent.models.TransferProcess;
import org.transfer.agent.models.message.Event;
import org.transfer.agent.models.message.Log;
import org.transfer.agent.models.message.Message;
import org.transfer.agent.models.message.Metric;

import java.util.HashMap;
import java.util.Objects;

public class ActivityMessenger {

    private final TransferProcess process;
    private final Client client;

    boolean started = false;

    public ActivityMessenger(TransferProcess process, Client client) {
        this.process = process;
        this.client = client;
    }

    public void push(String data) {

        Message message;

        try {
            message = new Gson().fromJson(data, Message.class);
        }
        catch (Exception e) {
            pushError(data);
            return;
        }

        if (message == null) {
            return;
        }

        if (Objects.equals(message.getType(), "event")) {

            Event event = new Gson().fromJson(data, Event.class);

            if (event.getEvent().equals("pre_process")) {
                process.setStatus(1);
            }
            else if (event.getEvent().equals("post_process")) {
                process.setProgress(100);
                process.setStatus(2);
                process.setAsFinished();
            }

            updateProcessDocument();
        }
        else if (Objects.equals(message.getType(), "metric")) {
            Metric metric = new Gson().fromJson(data, Metric.class);

            if (metric.getMetric().equals("memory_usage")) {
                process.setMemoryUsage(metric.getValue());
            }

            if (metric.getMetric().equals("peak_memory_usage")) {
                process.setPeakMemoryUsage(metric.getValue());
            }

            updateProcessDocument();
        }
        else {
            Log log = new Gson().fromJson(data, Log.class);

            insertLogDocument(log);
        }

    }

    public void pushError(String line) {
        process.appendToErrorBuffer(line);
        process.setStatus(3);
        process.setAsFinished();

        updateProcessDocument();
    }

    public void updateProcessDocument() {

        HashMap<String, Object> params = new HashMap<>();

        params.put("manifest_name", process.getManifestName());
        params.put("started", process.getStarted());
        params.put("finished", process.getFinished());
        params.put("status", process.getStatus());
        params.put("progress", process.getProgress());
        params.put("average_memory_usage", process.getMemoryUsage());
        params.put("peak_memory_usage", process.getPeakMemoryUsage());
        params.put("error_buffer", process.getErrorBuffer());

        if (!started) {
            params.put("_id", process.getId());
            client.insert("processes", params);

            started = true;
        }
        else {
            client.update("processes", process.getId(), params);
        }
    }

    public void insertLogDocument(Log log) {

        HashMap<String, Object> params = new HashMap<>();

        params.put("process_id", process.getId());
        params.put("message", log.getMessage());
        params.put("created", log.getDatetime().get("date"));
        params.put("level", log.getLevel());
        params.put("level_name", log.getLevelName());
        params.put("context", log.getContext());

        client.insert("logs", params);

    }
}
