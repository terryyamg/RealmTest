package com.terryyamg.realmtest;


import io.realm.RealmObject;

public class Company extends RealmObject{

    private int id;

    private String companyName;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    public String getCompanyName(){
        return companyName;
    }

}
