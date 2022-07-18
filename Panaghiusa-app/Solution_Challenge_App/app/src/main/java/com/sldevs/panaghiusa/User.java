package com.sldevs.panaghiusa;

public class User {

    public String id,profileLink,fullname,email,number,city,barangay,password,points,date,time;

    public User(){

    }

    public User(String id,String profileLink,String fullname,String email,String number,String city,String barangay,String password,String points,String date, String time){
        this.id = id;
        this.profileLink = profileLink;
        this.fullname = fullname;
        this.email = email;
        this.number = number;
        this.city = city;
        this.barangay = barangay;
        this.password = password;
        this.points = points;
        this.date = date;
        this.time = time;
    }

}
