package com.ahaya.earthquakeviewer.entity;

import android.content.ContentValues;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Earthquake {

    @NonNull
    @PrimaryKey
    private String id;
    @ColumnInfo(name="date")
    private Date mDate;
    @ColumnInfo(name="details")
    private String mDetails;
    @ColumnInfo(name="location")
    private Location mLocation;
    @ColumnInfo(name="magnitude")
    private double mMagnitude;
    @ColumnInfo(name="link")
    private String mLink;


    public Earthquake(
            String id,
            Date date,
            String details,
            Location location,
            double magnitude,
            String link
    ){
        this.id = id;
        mMagnitude = magnitude;
        mDate = date;
        mDetails = details;
        mLocation =location;
        mLink = link;
    }
    private Earthquake(){

    }
    @NonNull
    public String  getId() {
        return id;
    }
    public Date getMDate() {
        return mDate;
    }
    public String getMDetails() {
        return mDetails;
    }
    public Location getMLocation() {
        return mLocation;
    }
    public double getMMagnitude() {
        return mMagnitude;
    }
    public String getMLink() {
        return mLink;
    }

    public static Earthquake fromContentValue (ContentValues contentValues) {
        Earthquake earthquake = new Earthquake();
        if(contentValues.containsKey("id")){
            earthquake.id = contentValues.getAsString("id");
        }
        if(contentValues.containsKey("date")){
            earthquake.mDate = (Date) contentValues.get("date");
        }
        if(contentValues.containsKey("details")){
            earthquake.mDetails = contentValues.getAsString("details");
        }
        if(contentValues.containsKey("location")){
            earthquake.mLocation =(Location) contentValues.get("location");
        }
        if(contentValues.containsKey("link")){
            earthquake.mLink = contentValues.getAsString("link");
        }
        return earthquake;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
        String dateString = sdf.format(mDate);
        return dateString + ": " +mMagnitude +" "+mDetails;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Earthquake){
            return ((Earthquake) obj).getId().equals(id);
        }  else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mDate, mDetails, mLocation, mMagnitude, mLink);
    }

}
