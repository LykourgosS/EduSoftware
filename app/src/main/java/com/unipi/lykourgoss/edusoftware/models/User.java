package com.unipi.lykourgoss.edusoftware.models;

/**
 * Created by LykourgosS <lpsarantidis@gmail.com>
 * on 25,June,2019.
 */

public class User {

    private String id;

    private String email;

    private String name;

    private String photoUri;

    public User() {
    }

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public User(String id, String email, String name, String photoUri) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.photoUri = photoUri;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUri() {
        return photoUri;
    }
}
