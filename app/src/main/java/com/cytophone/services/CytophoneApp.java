package com.cytophone.services;

import com.cytophone.services.dao.Persistence;
import com.cytophone.services.handlers.PartyHandler;
import com.cytophone.services.handlers.UnlockCodeHandler;
import com.cytophone.services.telephone.CallService;

import android.app.Application;
import android.content.Intent;

public class CytophoneApp extends Application {
    // region public methods declaration
    public static CytophoneApp getInstanceApp() {
        return _instanceApp;
    }

    public static Persistence getInstanceDB() {
        return _instanceDB;
    }

    public static UnlockCodeHandler getUnlockHandlerDB() {
        return _unlockHandlerDB;
    }

    public static PartyHandler getPartyHandlerDB() {
        return _partyHandlerDB;
    }
    // endregion

    @Override
    public void onCreate() {
        super.onCreate();

        initializeHandlers();
        initializeServices();

        setSMSDefaultApp();
    }

    //Private methods declaration
    private void initializeHandlers() {
        try {
            _instanceDB = Persistence.getInstance(this);

            _unlockHandlerDB = new UnlockCodeHandler(_instanceDB);
            _partyHandlerDB = new PartyHandler(_instanceDB);
            _instanceApp = this;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeServices() {
        Intent i = new Intent(this, CallService.class);
        startService(i);
    }

    private void setSMSDefaultApp(){

    }

    //region fields declaration
    private static UnlockCodeHandler _unlockHandlerDB;
    private static PartyHandler _partyHandlerDB;
    private static CytophoneApp _instanceApp;
    private static Persistence _instanceDB;
    //endregion
}