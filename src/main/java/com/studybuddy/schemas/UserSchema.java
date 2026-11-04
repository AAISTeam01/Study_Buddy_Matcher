package com.studybuddy.schemas;

public class UserSchema {

    private String userID;
    private String name;
    private String email;
    private String course;
    private String branch;
    private String semester;
    private String password;

    public UserSchema(String userID, String name, String email,
            String course, String branch, String semester, String password) {

        this.userID = userID;
        this.name = name;
        this.email = email;
        this.course = course;
        this.branch = branch;
        this.semester = semester;
        this.password = password;
    }

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
}
