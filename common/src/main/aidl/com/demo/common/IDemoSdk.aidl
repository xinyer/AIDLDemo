// IDemoSdk.aidl
package com.demo.common;

import com.demo.common.ICallback;

interface IDemoSdk {

    void registerCallback(String packageName, ICallback callback);
}
