package org.transfer.broadcaster.meteor;

import com.keysolutions.ddpclient.DDPClient;

import java.net.URISyntaxException;
import java.util.HashMap;

public class DefaultClient implements Client {

    private final DDPClient client;

    public DefaultClient(String host, Integer port) throws URISyntaxException {
        client = new DDPClient(host, port);

        client.connect();
    }

    @Override
    public void insert(String collection, HashMap<String, Object> params) {
        client.collectionInsert(collection, params);
    }

    @Override
    public void update(String collection, String documentId, HashMap<String, Object> params) {
        client.collectionUpdate(collection, documentId, params);
    }
}
