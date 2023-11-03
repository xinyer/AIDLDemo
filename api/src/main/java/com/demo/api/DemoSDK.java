package com.demo.api;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;

import com.demo.api.model.Element;
import com.demo.api.model.ElementType;
import com.demo.api.model.Input;
import com.demo.api.model.Result;
import com.demo.api.model.Toggle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DemoSDK {

    public interface ResultCallback {
        <T extends Element> void onResult(Messenger messenger, Result<T> result);
    }

    // region singleton
    private static class DemoSDKHolder {
        private static final DemoSDK instance = new DemoSDK();
    }

    public static DemoSDK getInstance() {
        return DemoSDKHolder.instance;
    }

    private DemoSDK() {
        // private constructor
    }
    // endregion

    private static final String TAG = "DemoSDK";
    private Messenger serverMessenger;
    private final Messenger clientMessenger = new Messenger(new IncomingHandler(Looper.getMainLooper()));
    private ResultCallback resultCallback;

    public void connect(Context context, ResultCallback resultCallback) {
        this.resultCallback = resultCallback;
        bindService(context);
    }

    private void bindService(Context context) {
        Intent intent = new Intent();
        intent.setPackage("com.demo.server");
        intent.setComponent(new ComponentName("com.demo.server", "com.demo.server.DemoSdkService"));
        intent.setAction("com.demo.server.SDK");
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "onServiceConnected: " + name);
                serverMessenger = new Messenger(service);
                sayHi(context.getPackageName());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "onServiceDisconnected: " + name);
                serverMessenger = null;
            }
        }, Context.BIND_AUTO_CREATE);
    }

    private void sayHi(String packageName) {
        try {
            serverMessenger.send(new HiMessage(packageName).toMessage(clientMessenger));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private class IncomingHandler extends Handler {

        IncomingHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String type = msg.getData().getString("key_type");
            String result = msg.getData().getString("key_result");
            if (resultCallback != null && type != null && result != null) {
                resultCallback.onResult(serverMessenger, getResult(type, result));
            }
        }

        private Result<? extends Element> getResult(String type, String result) {
            ElementType elementType = ElementType.valueOf(type);
            Gson gson = new Gson();
            Type dataType = null;
            switch (elementType) {
                case INPUT:
                    dataType = new TypeToken<Result<Input>>() {}.getType();
                    break;
                case TOGGLE:
                    dataType = new TypeToken<Result<Toggle>>() {}.getType();
                    break;
                default:
            }
            if (dataType != null) {
                return gson.fromJson(result, dataType);
            } else {
                return null;
            }
        }
    }
}
