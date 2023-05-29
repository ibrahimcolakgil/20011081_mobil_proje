package com.example.mobilproje;

import java.util.ArrayList;

public class Student {

    private String picture, department, grade, status;
    private String distance_from_campus, share_time;
    private String email, phone_number, password, uid;

    public Student(String picture, String department, String grade, String status, String distance_from_campus, String share_time, String email, String password, String phone_number, String uid) {
        this.picture = picture;
        this.department = department;
        this.grade = grade;
        this.status = status;
        this.distance_from_campus = distance_from_campus;
        this.share_time = share_time;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.uid = uid;
    }

    public Student() {
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDistance_from_campus() {
        return distance_from_campus;
    }

    public void setDistance_from_campus(String distance_from_campus) {
        this.distance_from_campus = distance_from_campus;
    }

    public String getShare_time() {
        return share_time;
    }

    public void setShare_time(String share_time) {
        this.share_time = share_time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }
}
