
package com.studybuddy.schemas;

import java.util.List;

public class UserSchema {

    // Registration details
    private String userID;
    private String name;
    private String email;
    private String course;
    private String branch;
    private String semester;
    private String password;

    // Profile details
    private List<String> subjects;
    private List<String> interest;
    private String studyMode;
    private List<String> studyTime;

    // Constructor
    public UserSchema(String userID, String name, String email,
            String course, String branch, String semester,
            String password) {

        this.userID = userID;
        this.name = name;
        this.email = email;
        this.course = course;
        this.branch = branch;
        this.semester = semester;
        this.password = password;
    }

    // Registration getters
    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCourse() {
        return course;
    }

    public String getBranch() {
        return branch;
    }

    public String getSemester() {
        return semester;
    }

    public String getPassword() {
        return password;
    }

    // User ID setter
    public void setUserID(String userID) {
        this.userID = userID;
    }

    // Profile getters
    public List<String> getSubjects() {
        return subjects;
    }

    public List<String> getInterest() {
        return interest;
    }

    public String getStudyMode() {
        return studyMode;
    }

    public List<String> getStudyTime() {
        return studyTime;
    }

    // Profile setters
    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public void setInterest(List<String> interest) {
        this.interest = interest;
    }

    public void setStudyMode(String studyMode) {
        this.studyMode = studyMode;
    }

    public void setStudyTime(List<String> studyTime) {
        this.studyTime = studyTime;
    }
}
