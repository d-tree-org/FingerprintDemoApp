package org.dtree.apps.fingerprintapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.simprints.libsimprints.Identification;

import java.io.Serializable;

/**
 * Author : Isaya Mollel on 04/09/2019.
 */

@Entity
public class User implements Serializable {

    @PrimaryKey
    @NonNull
    String userId;

    String userName;
    String userImage;

    public User(){

    }

    public User(String name, String image){
        this.userName = name;
        this.userImage = image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
