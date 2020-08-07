package com.cytophone.services.handlers;

import com.cytophone.services.dao.*;
import com.cytophone.services.entities.*;
import android.os.AsyncTask;

import java.util.Date;

public class UnlockCodeHandler implements IHandler {
    public UnlockCodeHandler(Persistence db) {
        unlockCodeDAO = db.unlockCodeDAO();
    }

    private EventEntity createEvent(String sourceNumber, String targetNumber, Date messageDate) {
        return new EventEntity(sourceNumber, targetNumber, "SMS", messageDate);
    }

    private UnlockCodeEntity createUnlockCode(SMSEntity message) {
        try {
            UnlockCodeEntity unLockCode = message.getUnlockCodeObject();
            return unLockCode;
        } catch (Exception e) {
            return null;
        }
    }

    private static class insertUnlockCodeAsyncTask extends AsyncTask<IEntityBase, Void, Void> {
        insertUnlockCodeAsyncTask(UnlockCodeDAO DAO) {
            unlockCodeDAO = DAO;
        }

        @Override
        protected Void doInBackground(final IEntityBase... entities) {
            try {
            //if(null != unlockCodeDAO.getPartByNumberAndRole(message[0].getSourceNumber())) {
                unlockCodeDAO.add((UnlockCodeEntity) entities[0], (EventEntity) entities[1]);
            //}
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
        private UnlockCodeDAO unlockCodeDAO;
    }

    public void insertUnlockCode(SMSEntity message) {
        UnlockCodeEntity unLockCode = createUnlockCode(message);
        if (null != unLockCode) {
            EventEntity event = createEvent(message.getSourceNumber(),
                    unLockCode.getMsisdn(),
                    message.getMesageDate());
            new insertUnlockCodeAsyncTask(unlockCodeDAO).execute(unLockCode, event);
        }
    }

    private UnlockCodeDAO unlockCodeDAO;
}