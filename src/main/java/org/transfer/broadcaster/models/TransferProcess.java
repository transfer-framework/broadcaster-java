package org.transfer.broadcaster.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransferProcess {

    String id = String.format("%f", Math.random() * 10);
    String manifestName;
    String started = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    String finished;
    Integer progress = 0;
    Integer status = 0;
    String memoryUsage = "0";
    String peakMemoryUsage = "0";
    String errorBuffer = "";

    public TransferProcess(String manifestName) {
        this.manifestName = manifestName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManifestName() {
        return manifestName;
    }

    public String getStarted() {
        return started;
    }

    public String getFinished() {
        return finished;
    }

    public Integer getProgress() {
        return progress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(String memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public String getPeakMemoryUsage() {
        return peakMemoryUsage;
    }

    public void setPeakMemoryUsage(String peakMemoryUsage) {
        this.peakMemoryUsage = peakMemoryUsage;
    }

    public void setAsFinished() {
        finished = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String getErrorBuffer() {
        return errorBuffer;
    }

    public void appendToErrorBuffer(String line) {
        errorBuffer = errorBuffer.concat("\n" + line);
    }
}

