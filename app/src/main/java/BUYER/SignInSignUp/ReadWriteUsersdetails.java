package BUYER.SignInSignUp;

import java.io.Serializable;

public class ReadWriteUsersdetails implements Serializable {
    public String name , encodeImage;
    public ReadWriteUsersdetails(){};
    public ReadWriteUsersdetails(String username, String encodeImage){
        this.name = username;
        this.encodeImage = encodeImage;
    }
}
