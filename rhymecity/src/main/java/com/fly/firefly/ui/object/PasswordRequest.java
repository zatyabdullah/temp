package com.fly.firefly.ui.object;

/**
 * Created by Dell on 11/4/2015.
 */
public class PasswordRequest {

    /*Local Data Send To Server*/
    String email;
    String password;

    /*Initiate Class*/
    public PasswordRequest(){
    }

    //public LoginRequest(String username123){
    //    this.username = username123;
    //}

    public PasswordRequest(PasswordRequest data){
        email = data.getEmail();
        password = data.getPassword();
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
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
