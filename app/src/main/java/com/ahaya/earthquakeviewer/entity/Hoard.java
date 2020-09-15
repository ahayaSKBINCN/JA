package com.ahaya.earthquakeviewer.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Hoard {
    @NonNull
    @PrimaryKey
    public String hoarName;
    public int goldHoarded;
    public boolean hoardAccessible;

    public String getHoarName (){
        return hoarName;
    }

    public void setHoarName(String hoarName){
        this.hoarName = hoarName;
    }

    public int getGoldHoarded(){
        return goldHoarded;
    }

    public void setGoldHoarded (int goldHoarded) {
        this.goldHoarded = goldHoarded;
    }

    public boolean getHoardAccessible(){
        return hoardAccessible;
    }

    public void setHoardAccessible(boolean accessible){
        this.hoardAccessible = accessible;
    }

    public Hoard(String hoarName,int goldHoarded,boolean hoardAccessible){
        this.hoarName = hoarName;
        this.hoardAccessible = hoardAccessible;
        this.goldHoarded = goldHoarded;
    }

}
