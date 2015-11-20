package com.fly.firefly.ui.object;

/**
 * Created by Dell on 11/4/2015.
 */
public class PasswordRequest {

    /*Local Data Send To Server*/
    String email;


    /*Initiate Class*/
    public PasswordRequest(){
    }

    public PasswordRequest(PasswordRequest data){
        email = data.getEmail();

    }
    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }



    /*Response Data From Server*/
    String status;

    public String getStatus() {
        return status;
    }


}
