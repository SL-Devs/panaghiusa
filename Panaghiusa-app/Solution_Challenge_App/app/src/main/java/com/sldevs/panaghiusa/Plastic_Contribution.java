package com.sldevs.panaghiusa;


public class Plastic_Contribution {

    public String plasticURL,itemNo,contributionid,userid,contribution,fullname,number,address,longandlat,date,time;

    public Plastic_Contribution(){

    }

    public Plastic_Contribution(String plasticURL,String itemNo,String contributionid,String contribution, String userid,String fullname,String number,String address,String longandlat,String date, String time){
        this.plasticURL = plasticURL;
        this.itemNo = itemNo;
        this.contributionid = contributionid;
        this.userid = userid;
        this.contribution = contribution;
        this.fullname = fullname;
        this.number = number;
        this.address = address;
        this.longandlat = longandlat;
        this.date = date;
        this.time = time;
    }

}