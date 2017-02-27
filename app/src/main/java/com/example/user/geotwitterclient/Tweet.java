package com.example.user.geotwitterclient;

/**
 * Created by user on 2016.11.29..
 */

import java.io.Serializable;
import java.sql.Date;
import java.util.Comparator;

public class Tweet implements Serializable,Comparator<Tweet> {
    /**
     *
     */
    private static final long serialVersionUID = 2310698779687082782L;

    public String id_str;
    public String message;
    public double longitude;
    public double latitude;
    public String dateString;
    public Date date;
    public long milis;
    public Tweet(){

    }
    public Tweet(String id_str, String message,double longitude, double latitude, String dateString) {
        super();
        this.id_str = id_str;
        this.message = message;
        this.latitude = latitude;
        this.longitude = longitude;
    this.dateString = dateString;

    }
    public int compare(Tweet d, Tweet d1) {
        return d.dateString.compareTo(d1.dateString);
    }
    public String toString() {
        return message+"\n"+ dateString.substring(0,19);
    }
}
