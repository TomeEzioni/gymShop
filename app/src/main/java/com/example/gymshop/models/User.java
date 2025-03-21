package com.example.gymshop.models;

public class User
{
    String id;
    String fName;
    String lName;
    String phone;
    String address;
    String email;
    String password;
    boolean isAdmin;



    public User(String id, String fName, String lName, String phone, String address, String email, String password, boolean isAdmin) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
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

    public boolean getisAdmin()
    {
        return isAdmin;
    }


    public void setisAdmin(boolean admin) {
        this.isAdmin = admin;
    }





    @Override
    public String toString()
    {
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

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
