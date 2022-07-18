package com.sldevs.panaghiusa;

public class Organic_Contribution {

    public String contribution,days,contributionid,userid,fullname,number,address,longandlat,date,time;

    public Organic_Contribution(){

    }

    public Organic_Contribution(String contribution,String days,String contributionid,String userid,String fullname,String number,String address,String longandlat,String date, String time){
        this.contribution = contribution;
        this.days = days;
        this.contributionid= contributionid;
        this.userid = userid;
        this.fullname = fullname;
        this.number = number;
        this.address = address;
        this.longandlat = longandlat;
        this.date = date;
        this.time = time;
    }

}