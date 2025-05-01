package com.example.buyer.BUYER.b.models;

import java.io.Serializable;

public class User implements Serializable {


   public User(String name, String image, String token, String email, String id) {
      this.name = name;
      this.image = image;
      this.token = token;
      this.email = email;
      this.id = id;
   }

   public String name, image, token, email, id;

   public User() {
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }
}
