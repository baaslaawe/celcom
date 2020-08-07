package com.cytophone.services.telephone;

import com.cytophone.services.activities.CallView;

import org.jetbrains.annotations.NotNull;

import android.telecom.InCallService;
import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
import android.os.Bundle;
import android.util.Log;
import android.net.Uri;

public final class CallService extends InCallService {
    @Override
    public void onCallAdded(@NotNull Call call) {
        super.onCallAdded(call);

        Log.d("D/ClosedComm", "OnCallAdded");
        Log.d("D/ClosedComm", "Call details: " + call.getDetails());

        OngoingCall.INSTANCE.setCall(call);
        CallView.start((Context)this, call);
    }

    @Override
    public void onCallRemoved(@NotNull Call call) {
        super.onCallRemoved(call);

        Log.d("D/ClosedComm", "OnCallRemoved");
        Log.d("D/ClosedComm", "Call details: " + call.getDetails());

        OngoingCall.INSTANCE.setCall(null);
    }

    @Override
    public void onConnectionEvent(Call call, String event, Bundle extras) {
        super.onConnectionEvent(call, event, extras);

        Log.d("D/ClosedComm", "Disconnect code: " +
                call.getDetails().getDisconnectCause().getCode());
        Log.d("D/ClosedComm", "Disconnect reason: " +
                call.getDetails().getDisconnectCause().getReason());
        Log.d("D/ClosedComm", "Disconnect description: " +
                call.getDetails().getDisconnectCause().getDescription());
        Log.d("D/ClosedComm", "Event: " + event);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d("D/ClosedComm", "OnStart");
        return START_STICKY;
    }
}
