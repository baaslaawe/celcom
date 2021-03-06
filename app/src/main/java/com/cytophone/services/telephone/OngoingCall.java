package com.cytophone.services.telephone;

import io.reactivex.subjects.BehaviorSubject;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import android.content.Intent;
import android.net.Uri;
import android.telecom.Call;
import android.util.Log;

import com.cytophone.services.CellCommApp;
import com.cytophone.services.utilities.Utils;

public class OngoingCall {
    static {
        _callback = new Call.Callback() {
            public void onStateChanged(@NotNull Call call, int newState) {
                Log.d("OngoingCall.onStateChanged", "");
                if (null != call) {
                    OngoingCall.INSTANCE.getState().onNext(newState);
                }
            }
        };
        _state = BehaviorSubject.create();
        INSTANCE = new OngoingCall();
    }

    @NotNull
    public final BehaviorSubject getState() {
        return _state;
    }

    @Nullable
    public final Call getCall() {
        return _call;
    }

    public final void setCall(@Nullable Call value) {
        Log.d("OngoingCall.setCall", "");
        if (null != _call) {
            _call.unregisterCallback((Call.Callback)_callback);
        }

        if (null != value ) {
            value.registerCallback((Call.Callback)_callback);
            _state.onNext(value.getState());
        }
        _call = value;
    }

    public final void answer() {
        if (null != _call) {
            Log.d("OngoingCall.answer", "");
            _call.answer(0);
        }
    }

    public final void hangup() {
        if (null != _call) {
            Log.d("OngoingCall.hangup", "");
            _call.disconnect();
        }
    }

    public final void reject() {
        if (null != _call) {
            Log.d("OngoingCall.reject", "");
            _call.reject(false,"ds");
        }
    }

    public final void dial(String phoneNumber){
        Uri uri = Uri.parse("tel:" + phoneNumber);

        CellCommApp.getInstanceApp().getApplicationContext().startActivity (
                new Intent("android.intent.action.CALL"
                , uri));
    }

    @NotNull private static BehaviorSubject<Integer> _state;
    private static Call.Callback _callback;
    @Nullable private static Call _call;
    public static OngoingCall INSTANCE;
}