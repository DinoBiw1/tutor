package com.example.melodyhacker.tutor.Model;

/**
 * Created by melodyhacker on 1/31/19.
 */

public class GetSetArea {

    private String name_class;
    private String area_class;
    private String degree_class;
    private String email_class;
    private String tel_class;
    private String etc_class;
    private String name_user;
    private String last_name_user;
    private String image_user;
    private String line_class;
    private String clash_class;
//         json.getString("name_class"),
//         json.getString("area_class"),
//         json.getString("degree_class"),
//         json.getString("email_class"),
//         json.getString("tel_class"),
//         json.getString("etc_class"),
//         json.getString("name_user"),
//         json.getString("last_name"))
    public GetSetArea(String name_class,
                      String area_class,
                      String clash_class,
                      String degree_class,
                      String line_class,
                      String email_class,
                      String tel_class,
                      String etc_class,
                      String name_user,
                      String last_name_user,
                      String image_user) {
        this.name_class = name_class;
        this.area_class = area_class;
        this.degree_class = degree_class;
        this.line_class = line_class;
        this.email_class = email_class;
        this.clash_class = clash_class;
        this.tel_class = tel_class;
        this.etc_class = etc_class;
        this.name_user = name_user;
        this.last_name_user = last_name_user;
        this.image_user = image_user;
    }
    //////////////////////////////////////////////////////////////////////////
    public String getNameClass() {
        return name_class;
    }
    public void setNameClass(String name_class) {
        this.name_class = name_class;
    }
    public String getArea() {
        return area_class;
    }
    public void setArea(String area_class) {
        this.area_class = area_class;
    }
    public String getDegree() {
        return degree_class;
    }
    public void setDegree(String degree_class) {
        this.degree_class = degree_class;
    }
    public String getEmail() {
        return email_class;
    }
    public void setEmail(String email_class) {
        this.email_class = email_class;
    }
    public String getTel() {
        return tel_class;
    }
    public void setTel(String image) {
        this.tel_class = tel_class;
    }
    public String getEtc() {
        return etc_class;
    }
    public void setEtc(String etc_class) {
        this.etc_class = etc_class;
    }
    public String getNameTutor() {
        return name_user;
    }
    public void setNameTutor(String name_user) {
        this.name_user = name_user;
    }
    public String getLastNameTutor() {
        return last_name_user;
    }
    public void setLastNameTutor(String name_user) {
        this.last_name_user = last_name_user;
    }
    public String getImage_tutor() {
        return image_user;
    }
    public void setImage_user(String image_user) {
        this.image_user = image_user;
    }
    public String getClash() {
        return clash_class;
    }
    public void setClash(String line_class) {
        this.line_class = line_class;
    }
    public String getLine() {
        return line_class;
    }
    public void setLine(String line_class) {
        this.line_class = line_class;
    }
}