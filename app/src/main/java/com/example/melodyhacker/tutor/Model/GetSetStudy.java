package com.example.melodyhacker.tutor.Model;

/**
 * Created by melodyhacker on 1/31/19.
 */

public class GetSetStudy {

    private String name_class;
    private String degree_class;
    private String area_class;
    private String time;
    private String position;
    private String clash;
    private String date;
    private String status;


//                                "An_Subjects": "android",
//                                "An_Class": "มัธยมปลาย",
//                                "An_District": "เมือง",
//                                "An_Time": "08:00",
//                                "AN_Position": " ",
//                                "An_Price": "3000",
//                                "An_Details": "andrond   json\n",
//                                "An_Date": "2019-02-17 15:43:11",
//                                "AN_Status": "0"
    public GetSetStudy(String name_class,
                       String degree_class,
                       String area_class,
                       String time,
                       String position,
                       String clash,
                       String date,
                       String status) {
        this.name_class = name_class;
        this.area_class = area_class;
        this.degree_class = degree_class;
        this.time = time;
        this.position = position;
        this.clash = clash;
        this.position = position;
        this.clash = clash;
        this.date = date;
        this.status = status;

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
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String setPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getClash() {
        return clash;
    }
    public void setClash(String clash) {
        this.clash = clash;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}