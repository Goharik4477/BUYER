package com.example.buyer.BUYER.SignInSignUp;

import java.io.Serializable;

public class ReadWriteUsersdetails implements Serializable {
    public String name , encodeImage;
    public ReadWriteUsersdetails(){};
    public ReadWriteUsersdetails(String username, String encodeImage){
        this.name = username;
        this.encodeImage = encodeImage;
    }

   
}/*
public class ReadWriteUsersdetails {

    private String fullName;
    private String encodedImage; // Поле для хранения строки Base64 изображения


    public ReadWriteUsersdetails(String fullName, String encodedImage) {
        this.fullName = fullName;
        this.encodedImage = encodedImage;
    }

    // Геттер для строки Base64 изображения
    public String getEncodedImage() {
        return encodedImage;
    }


    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}*/