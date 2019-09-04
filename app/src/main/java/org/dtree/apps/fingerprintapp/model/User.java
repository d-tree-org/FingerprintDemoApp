package org.dtree.apps.fingerprintapp.model;

import com.simprints.libsimprints.Identification;

/**
 * Author : Isaya Mollel on 04/09/2019.
 */
public class User {

    String userName;
    String userImage;
    Identification userIdentification;

    public User(String name, String image, Identification identification){
        this.userName = name;
        this.userImage = image;
        this.userIdentification = identification;
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

    public Identification getUserIdentification() {
        return userIdentification;
    }

    public void setUserIdentification(Identification userIdentification) {
        this.userIdentification = userIdentification;
    }
}
