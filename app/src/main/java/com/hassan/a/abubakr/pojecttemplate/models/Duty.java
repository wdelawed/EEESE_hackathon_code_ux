package com.hassan.a.abubakr.pojecttemplate.models;

public class Duty {
    String faculty;
    String department;
    String Image;

    public Duty(String faculty, String department, String image) {
        this.faculty = faculty;
        this.department = department;
        Image = image;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    @Override
    public String toString() {
        return "Duty{" +
                "faculty='" + faculty + '\'' +
                ", department='" + department + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }
}
