package com.example.Data;

public class PageData {
    public PageData(int image, String cat_tital, String cat_discription, String cat_day, String cat_persanag, int cat_steg, int cat_progress) {
        this.image = image;
        this.cat_tital = cat_tital;
        this.cat_discription = cat_discription;
        this.cat_day = cat_day;
        this.cat_persanag = cat_persanag;
        this.cat_steg = cat_steg;
        this.cat_progress = cat_progress;
    }

    int image;
    String cat_tital;
    String cat_discription;
    String cat_day;
    String cat_persanag;
    int cat_steg;
    int cat_progress;
    int adapter_position_cat;

    //Remainder Timer


    public PageData(String timer_Remainder, String day, boolean isswitch) {
        this.timer_Remainder = timer_Remainder;
        this.day = day;
        this.isswitch = isswitch;
    }


    public String getTimer_Remainder() {
        return timer_Remainder;
    }

    public void setTimer_Remainder(String timer_Remainder) {
        this.timer_Remainder = timer_Remainder;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isIsswitch() {
        return isswitch;
    }

    public void setIsswitch(boolean isswitch) {
        this.isswitch = isswitch;
    }

    String timer_Remainder;
    String day;
    boolean isswitch;


    public int getAdapter_position_cat() {
        return adapter_position_cat;
    }

    public PageData(String cat_day, String cat_persanag, int cat_progress, int adapter_position_cat) {
        this.cat_day = cat_day;
        this.cat_persanag = cat_persanag;
        this.cat_progress = cat_progress;
        this.adapter_position_cat = adapter_position_cat;
    }

    public PageData(){}

    public void setAdapter_position_cat(int adapter_position_cat) {
        this.adapter_position_cat = adapter_position_cat;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCat_tital() {
        return cat_tital;
    }

    public void setCat_tital(String cat_tital) {
        this.cat_tital = cat_tital;
    }

    public String getCat_discription() {
        return cat_discription;
    }

    public void setCat_discription(String cat_discription) {
        this.cat_discription = cat_discription;
    }

    public String getCat_day() {
        return cat_day;
    }

    public void setCat_day(String cat_day) {
        this.cat_day = cat_day;
    }

    public String getCat_persanag() {
        return cat_persanag;
    }

    public void setCat_persanag(String cat_persanag) {
        this.cat_persanag = cat_persanag;
    }

    public int getCat_steg() {
        return cat_steg;
    }

    public void setCat_steg(int cat_steg) {
        this.cat_steg = cat_steg;
    }

    public int getCat_progress() {
        return cat_progress;
    }

    public void setCat_progress(int cat_progress) {
        this.cat_progress = cat_progress;
    }
}
