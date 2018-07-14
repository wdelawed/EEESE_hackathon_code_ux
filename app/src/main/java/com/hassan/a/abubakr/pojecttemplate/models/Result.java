package com.hassan.a.abubakr.pojecttemplate.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Result {
    User student;
    @SerializedName("result")
    List<ResultEntry> result;
    @SerializedName("gpa")
    float GPA;

    public Result(User student, List<ResultEntry> result, float GPA) {
        this.student = student;
        this.result = result;
        this.GPA = GPA;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public List<ResultEntry> getResult() {
        return result;
    }

    public void setResult(List<ResultEntry> result) {
        this.result = result;
    }

    public float getGPA() {
        return GPA;
    }

    public void setGPA(float GPA) {
        this.GPA = GPA;
    }

    public class ResultEntry{
        @SerializedName("course_id")
        String courseId;
        @SerializedName("course")
        String courseName;
        String grade;

        public ResultEntry(String courseId, String courseName, String grade) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.grade = grade;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        @Override
        public String toString() {
            return "ResultEntry{" +
                    "courseId='" + courseId + '\'' +
                    ", courseName='" + courseName + '\'' +
                    ", grade='" + grade + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Result{" +
                "student=" + student +
                ", result=" + result.toString() +
                ", GPA=" + GPA +
                '}';
    }
}
