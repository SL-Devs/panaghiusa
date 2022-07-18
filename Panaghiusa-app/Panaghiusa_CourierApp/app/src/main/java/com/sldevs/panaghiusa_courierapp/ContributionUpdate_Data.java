package com.sldevs.panaghiusa_courierapp;

public class ContributionUpdate_Data {

    public String contributionid,contribution,userId,fullname,address,number,date,time;

    public ContributionUpdate_Data(){

    }

    public ContributionUpdate_Data(String contributionid, String contribution, String userId, String fullname, String address, String number, String date, String time){
        this.contributionid = contributionid;
        this.contribution = contribution;
        this.userId = userId;
        this.fullname = fullname;
        this.address = address;
        this.number = number;
        this.date = date;
        this.time = time;
    }

}
