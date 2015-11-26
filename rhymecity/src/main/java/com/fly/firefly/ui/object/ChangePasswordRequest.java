package com.fly.firefly.ui.object;

/**
 * Created by Metch user
 */
public class ChangePasswordRequest  {

    private String username;
    private String currentPassword;
    private String newPassword;
    /*Initiate Class*/
    public ChangePasswordRequest(){
    }

    public ChangePasswordRequest(ChangePasswordRequest data){
        username = data.getEmail();
        currentPassword = data.getCurrentPassword();
        newPassword = data.getNewPassword();

    }
    public String getEmail() {

        return username;
    }

    public void setEmail(String email) {

        this.username = email;
    }


    public String getCurrentPassword() {

        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {

        this.currentPassword = currentPassword;
    }
    public String getNewPassword() {

        return newPassword ;
    }

    public void setNewPassword(String newPassword ) {

        this.newPassword  = newPassword ;
    }


    /*Response Data From Server*/
    String status;

    public String getStatus() {
        return status;
    }


}
