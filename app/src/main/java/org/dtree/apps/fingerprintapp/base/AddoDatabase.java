package org.dtree.apps.fingerprintapp.base;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import org.dtree.apps.fingerprintapp.dao.UserDao;
import org.dtree.apps.fingerprintapp.model.User;

/**
 * Author : Isaya Mollel on 04/09/2019.
 */

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AddoDatabase extends RoomDatabase {

    private static AddoDatabase instance;

    public static AddoDatabase getInstance(Context context){
        instance = Room.databaseBuilder(context, AddoDatabase.class, "addo_db").build();
        return instance;
    }

    public abstract UserDao userDao();

}
