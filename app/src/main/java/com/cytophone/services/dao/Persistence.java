package com.cytophone.services.dao;

import com.cytophone.services.entities.*;
import android.content.Context;

import androidx.room.TypeConverters;
import androidx.room.RoomDatabase;
import androidx.room.Database;
import androidx.room.Room;

@Database(entities = {PartyEntity.class, EventEntity.class, UnlockCodeEntity.class}, version = 6)
@TypeConverters({DateConverter.class})
public abstract class Persistence extends RoomDatabase {
    public static Persistence getInstance(Context context) {
        if (null  == dbInstancce) {
            dbInstancce = Room.databaseBuilder(
                    context.getApplicationContext()
                    ,Persistence.class
                    , "cytophone-db.dat")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // allow queries on the main thread.
                    .build();
        }
        return dbInstancce;
    }

    public static void destroyInstance() {
        dbInstancce = null;
    }

    public abstract UnlockCodeDAO unlockCodeDAO();
    public abstract PartyDAO partyDAO();
    public abstract EventDAO eventDAO();

    private static Persistence dbInstancce;

}