package com.sldevs.panaghiusa;

public class Cart_Temp_Log {

    private String plasticType;
    private String plasticImage;

    public Cart_Temp_Log(){

    }

    public Cart_Temp_Log(String plasticType,String plasticImage){
        this.plasticType = plasticType;
        this.plasticImage = plasticImage;

    }

    public String getPlasticType(){
        return plasticType;
    }

    public void setPlasticType(String plasticType){
        this.plasticType = plasticType;
    }

    public String getPlasticImage(){
        return plasticImage;
    }

    public void setPlasticImage(String plasticImage){
        this.plasticImage = plasticImage;
    }
}
