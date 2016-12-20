package com.terryyamg.realmtest;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Person extends RealmObject{

    @PrimaryKey
    private int id;

    private String name;
    private int age;
    private RealmList<Company> company;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setAge(int age){
        this.age = age;
    }

    public int getAge(){
        return age;
    }

    public void setCompany(RealmList<Company> company){
        this.company = company;
    }

    public RealmList<Company> getCompany(){
        return company;
    }

}
