package com.upvmaster.carlos.audiolibros.login.data.datasources;

/**
 * Created by Carlos on 05/02/2017.
 */

public interface FirebaseStorage {
    void saveUser(String name,String email,String provider);
    void removeUser();
    String getNameUser();
}
