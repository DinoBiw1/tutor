package com.example.melodyhacker.tutor.Model;

/**
 * Created by melodyhacker on 2/17/19.
 */

public class GetSetRatting {
    private String name;
    private String last_name;
    private String image;
    private String id,rate;


    public GetSetRatting(String id,String name, String last_name, String image, String rate) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.image = image;
        this.rate = rate;
    }

    //////////////////////////////////////////////////////////////////////////
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String image) {
        this.rate = rate;
    }
    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}