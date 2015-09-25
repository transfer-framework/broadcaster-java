package org.transfer.agent.meteor;

import java.util.HashMap;

public interface Client {
    void insert(String collection, HashMap<String, Object> params);
    void update(String collection, String documentId, HashMap<String, Object> params);
}
