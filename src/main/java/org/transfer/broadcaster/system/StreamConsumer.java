package org.transfer.broadcaster.system;

public interface StreamConsumer {
    void consume(String line);
    void done();
}
