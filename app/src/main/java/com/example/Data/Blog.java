package com.example.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.Integer;

import java.util.ArrayList;

public class Blog implements Parcelable {


    private String dayName;
    private String exerciseTotal;
    private ArrayList<Integer> imagelistID;
    private ArrayList<String> exerciseName;
    private ArrayList<String> discriptionlist;


    private boolean isrestDay;
    private boolean isChek;
    public  boolean ischekDay;
    private boolean isselectedtab;
    private ArrayList<Integer> timer;
    private int size;
    private int seekbar;


    //for database

    private int adapter_position;
    private int seekbarprogress;
    private int list_size;
    private int restday_code;

    public Blog(int adapter_position, int seekbarprogress, int list_size,int restday_code) {
        this.adapter_position = adapter_position;
        this.seekbarprogress = seekbarprogress;
        this.list_size = list_size;
        this.restday_code = restday_code;
    }


    public int getRestday_code() {
        return restday_code;
    }

    public void setRestday_code(int restday_code) {
        this.restday_code = restday_code;
    }


    public ArrayList<String> getDiscriptionlist() {
        return discriptionlist;
    }

    public void setDiscriptionlist(ArrayList<String> discriptionlist) {
        this.discriptionlist = discriptionlist;
    }

    public int getAdapter_position() {
        return adapter_position;
    }

    public void setAdapter_position(int adapter_position) {
        this.adapter_position = adapter_position;
    }

    public int getSeekbarprogress() {
        return seekbarprogress;
    }

    public void setSeekbarprogress(int seekbarprogress) {
        this.seekbarprogress = seekbarprogress;
    }

    public int getList_size() {
        return list_size;
    }

    public void setList_size(int list_size) {
        this.list_size = list_size;
    }

    public boolean isIsrestDay() {
        return isrestDay;
    }

    public void setIsrestDay(boolean isrestDay) {
        this.isrestDay = isrestDay;
    }


    public Blog(int a_positionDB, int timer_value_DB) {
        this.a_positionDB = a_positionDB;
        this.timer_value_DB = timer_value_DB;
    }

    //for timerdata from db
    int a_positionDB;
    int timer_value_DB;


    public int getA_positionDB() {
        return a_positionDB;
    }

    public void setA_positionDB(int a_positionDB) {
        this.a_positionDB = a_positionDB;
    }

    public int getTimer_value_DB() {
        return timer_value_DB;
    }

    public void setTimer_value_DB(int timer_value_DB) {
        this.timer_value_DB = timer_value_DB;
    }

    public boolean isIsselectedtab() {
        return isselectedtab;
    }

    public void setIsselectedtab(boolean isselectedtab) {
        this.isselectedtab = isselectedtab;
    }

    public boolean isIschekDay() {
        return ischekDay;
    }

    public void setIschekDay(boolean ischekDay) {
        this.ischekDay = ischekDay;
    }



    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }



    public int getSeekbar() {
        return seekbar;
    }

    public void setSeekbar(int seekbar) {
        this.seekbar = seekbar;
    }

    public ArrayList<Integer> getTimer() {
        return timer;
    }

    public void setTimer(ArrayList<Integer> timer) {
        this.timer = timer;
    }

    public static Creator<Blog> getCREATOR() {
        return CREATOR;
    }

    protected Blog(Parcel in) {
        dayName = in.readString();
        exerciseTotal = in.readString();
        exerciseName = in.createStringArrayList();
        isChek = in.readByte() != 0;
    }

    public Blog(){}

    public static final Creator<Blog> CREATOR = new Creator<Blog>() {
        @Override
        public Blog createFromParcel(Parcel in) {
            return new Blog(in);
        }

        @Override
        public Blog[] newArray(int size) {
            return new Blog[size];
        }
    };

    public boolean isChek() {
        return isChek;
    }

    public void setChek(boolean chek) {
        isChek = chek;
    }

    public Blog(String dayName, String exerciseTotal, ArrayList<Integer> imagelistID, ArrayList<String> exerciseName,ArrayList<Integer> timer,ArrayList<String> discriptionlist) {
        this.dayName = dayName;
        this.timer = timer;
        this.exerciseTotal = exerciseTotal;
        this.imagelistID = imagelistID;
        this.exerciseName = exerciseName;
        this.discriptionlist = discriptionlist;
    }


    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getExerciseTotal() {
        return exerciseTotal;
    }

    public void setExerciseTotal(String exerciseTotal) {
        this.exerciseTotal = exerciseTotal;
    }

    public ArrayList<Integer> getImagelistID() {
        return imagelistID;
    }

    public void setImagelistID(ArrayList<Integer> imagelistID) {
        this.imagelistID = imagelistID;
    }

    public ArrayList<String> getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(ArrayList<String> exerciseName) {
        this.exerciseName = exerciseName;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dayName);
        dest.writeString(exerciseTotal);
        dest.writeStringList(exerciseName);
        dest.writeByte((byte) (isChek ? 1 : 0));
    }
}
