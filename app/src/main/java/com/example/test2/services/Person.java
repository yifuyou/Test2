package com.example.test2.services;

public class Person {

    int id;
    String name;
    String psw;
    public Person(){ }
    public Person(int id,String name,String psw){
        this.id=id;
        this.name=name;
        this.psw=psw;
    }


    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
