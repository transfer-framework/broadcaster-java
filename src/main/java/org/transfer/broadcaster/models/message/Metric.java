package org.transfer.broadcaster.models.message;

public class Metric extends Message {
    String metric;
    String value;

    public String getMetric() {
        return metric;
    }

    public String getValue() {
        return value;
    }
}