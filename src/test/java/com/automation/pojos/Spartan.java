package com.automation.pojos;


import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * This class represents spartan POJO
 */
public class Spartan {

    private int id;
    private String name;
    private String gender;
    @SerializedName("phone")
    private long phoneNumber;

    public Spartan (String name, String gender, long phoneNumber){
        this.name=name;
        this.gender=gender;
        this.phoneNumber=phoneNumber;
    }

    public Spartan (int id, String name, String gender, long phoneNumber){
        this.id=id;
        this.name=name;
        this.gender=gender;
        this.phoneNumber=phoneNumber;
    }


    public Spartan (){

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Spartan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (!(o instanceof Spartan)) return  false;
        Spartan spartan = (Spartan) o;
        return id == spartan.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gender, phoneNumber);
    }
}
