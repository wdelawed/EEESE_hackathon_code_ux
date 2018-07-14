package com.hassan.a.abubakr.pojecttemplate.models;

import com.google.gson.annotations.SerializedName;
public class User {
    int id;
    int type;
    int userId;
    String name;
    String userName;
    int year;
    int batch;
    String faculty;
    String department;
    String password;
    String token;


    public User(int id, int userId, int type, String name, String userName, int year, int batch, String faculty, String department, String password,String token) {
        this.id = id;
        this.type = type;
        this.userId = userId;
        this.name = name;
        this.userName = userName;
        this.year = year;
        this.batch = batch;
        this.faculty = faculty;
        this.department = department;
        this.password = password;
        this.token= token;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", type=" + type +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", year=" + year +
                ", batch=" + batch +
                ", faculty='" + faculty + '\'' +
                ", department='" + department + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
