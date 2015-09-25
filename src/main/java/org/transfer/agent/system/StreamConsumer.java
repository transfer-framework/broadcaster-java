package org.transfer.agent.system;

public interface StreamConsumer {
    void consume(String line);
    void done();
}
