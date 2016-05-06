package org.transfer.agent.system;

import java.io.*;

public class ProcessRunner {

    private static final String RUN_COMMAND = "manifest:run";
    private static final String CHAIN_FLAG = "--chain=";

    private final String transferExec;
    private final String manifestName;
    private final String chain;
    private final String workingDirectory;

    private StreamConsumer inputStreamConsumer;
    private StreamConsumer errorStreamConsumer;

    public ProcessRunner(String transferExec, String manifestName, String chain, String workingDirectory) {
        this.transferExec = transferExec;
        this.manifestName = manifestName;
        this.chain = chain;
        this.workingDirectory = workingDirectory;
    }

    public void setInputStreamConsumer(StreamConsumer consumer) {
        inputStreamConsumer = consumer;
    }

    public void setErrorStreamConsumer(StreamConsumer consumer) {
        errorStreamConsumer = consumer;
    }

    public void start() throws IOException, InterruptedException {

        ProcessBuilder builder = new ProcessBuilder(transferExec, RUN_COMMAND, manifestName, CHAIN_FLAG + chain);
        builder.directory(new File(workingDirectory));

        Process process = builder.start();

        StreamThread inputStreamThread = new StreamThread(process.getInputStream(), inputStreamConsumer);
        StreamThread errorStreamThread = new StreamThread(process.getErrorStream(), errorStreamConsumer);

        inputStreamThread.start();
        errorStreamThread.start();

        int exitValue = process.waitFor();

        inputStreamThread.join();
        errorStreamThread.join();

        if (exitValue > 0) {
            // ?
        }
    }

    class StreamThread extends Thread {
        private final InputStream stream;
        private final StreamConsumer consumer;

        public StreamThread(InputStream stream, StreamConsumer consumer) {
            this.stream = stream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            try {
                while ((line = bufferedReader.readLine()) != null) {
                    consumer.consume(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            consumer.done();
        }
    }
}
