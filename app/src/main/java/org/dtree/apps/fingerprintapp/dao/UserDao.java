package org.dtree.apps.fingerprintapp.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.dtree.apps.fingerprintapp.model.User;

/**
 * Author : Isaya Mollel on 04/09/2019.
 */

@Dao
public interface UserDao {

    @Insert
    Long createUser(User user);

    @Query("Select * from User where userId =:userId")
    LiveData<User> getUserById(String userId);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

}
