package org.transfer.agent.models.message;

import java.util.HashMap;

public class Log extends Message {

    String message;
    String[] context;
    Integer level;
    String levelName;
    String channel;
    HashMap<String, String> datetime;
    String[] extra;

    public String getMessage() {
        return message;
    }

    public String[] getContext() {
        return context;
    }

    public Integer getLevel() {
        return level;
    }

    public String getLevelName() {
        return levelName;
    }

    public String getChannel() {
        return channel;
    }

    public HashMap<String, String> getDatetime() {
        return datetime;
    }

    public String[] getExtra() {
        return extra;
    }
}