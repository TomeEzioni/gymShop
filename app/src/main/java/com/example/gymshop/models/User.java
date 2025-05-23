package com.example.gymshop.models;

import java.io.Serializable;

public class User implements Serializable
{
    String id;
    String fName;
    String lName;
    String phone;
    String address;
    String email;
    String password;
    String isAdmin;


    public User(String id, String fName, String lName, String phone, String address, String email, String password, String isAdmin) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User(User user) {
        this.id = user.id;
        this.fName = user.fName;
        this.lName = user.lName;
        this.phone = user.phone;
        this.address = user.address;
        this.email = user.email;

    }

    public User()
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getfName()
    {
        return fName;
    }

    public void setfName(String fName)
    {
        this.fName = fName;
    }

    public String getlName()
    {
        return lName;
    }

    public void setlName(String lName)
    {
        this.lName = lName;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin='" + isAdmin + '\'' +
                '}';
    }
}
