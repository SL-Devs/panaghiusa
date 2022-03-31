package com.sldevs.panaghiusa_courierapp;


public class TBP_Items {

    private String contributionid,contribution,userId,fullname,address,number,date,time;

    public TBP_Items(){

    }

    public TBP_Items(String contributionid, String contribution, String userId, String fullname, String address, String number, String date, String time){
        this.contributionid = contributionid;
        this.contribution = contribution;
        this.userId = userId;
        this.fullname = fullname;
        this.address = address;
        this.number = number;
        this.date = date;
        this.time = time;
    }

    public String getContributionid(){
        return contributionid;
    }

    public void setContributionid(String contributionid){
        this.contributionid = contributionid;
    }

    public String getContribution(){
        return contribution;
    }

    public void setContribution(String contribution){
        this.contribution = contribution;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullname(){
        return fullname;
    }

    public void setFullname(String fullname){
        this.fullname = fullname;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getNumber(){
        return number;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

}