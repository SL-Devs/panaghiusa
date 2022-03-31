package com.sldevs.panaghiusa;

public class TBC_Items_Organic {

    private String contributionid,contribution,fullname,number,date,time;

    public TBC_Items_Organic(){

    }

    public TBC_Items_Organic(String contributionid, String contribution,String fullname, String number, String date, String time){
        this.contributionid = contributionid;
        this.contribution = contribution;
        this.fullname = fullname;
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


    public String getFullname(){
        return fullname;
    }

    public void setFullname(String fullname){
        this.fullname = fullname;
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