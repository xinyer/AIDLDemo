package com.demo.server;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.demo.api.model.Element;
import com.demo.api.model.Result;
import com.google.gson.Gson;

import java.util.concurrent.ConcurrentHashMap;

public class ClientManager {

    // region singleton
    private static class ClientManagerHolder {
        private static final ClientManager instance = new ClientManager();
    }

    public static ClientManager getInstance() {
        return ClientManagerHolder.instance;
    }

    private ClientManager() {
        // private constructor
    }
    // endregion

    private final ConcurrentHashMap<String, Messenger> messengers = new ConcurrentHashMap<>();
    private final Gson gson = new Gson();

    public void add(String packageName, Messenger messenger) {
        messengers.put(packageName, messenger);
    }

    public <T extends Element> void action(String packageName, Result<T> result) {
        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString("key_type", result.getType().name());
        bundle.putString("key_result", gson.toJson(result));
        msg.setData(bundle);
        try {
            Messenger messenger = messengers.get(packageName);
            if (messenger != null) messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
