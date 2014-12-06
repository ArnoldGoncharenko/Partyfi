package com.example.arnold.partyfi;

/**
 * Created by James Laverty on 03/12/2014.
 */
public class Party {
    int id;
    double lat;
    double lng;
    String title;
    String date;
    String time;
    String description;
    String address;

    //constructors
    public Party()
    {

    }
    public Party(double lat, double lng, String title, String description, String address, String date, String time)
    {
        this.lat = lat;
        this.lng = lng;
        this.title = title;
        this.description = description;
        this.address = address;
        this.date = date;
        this.time = time;
    }
    public Party(int id, double lat, double lng, String title, String description,String address, String date, String time)
    {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.title = title;
        this.description = description;
        this.address = address;
        this.date = date;
        this.time = time;
    }

    //setters
    public void setId(int id){ this.id = id; }
    public void setDate(String date){ this.date = date; }
    public void setTime(String time){ this.time = time; }
    public void setLat(double lat)
    {
        this.lat = lat;
    }
    public void setLng(double lng)
    {
        this.lng = lng;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public void setAddress(String address) {this.address = address;}


    //getters
    public int getId()
    {
        return this.id;
    }
    public double getLat() { return this.lat; }
    public double getLng() {return this.lng; }
    public String getDate(){ return this.date; }
    public String getTime(){ return this.time; }
    public String getTitle()
    {
        return this.title;
    }
    public String getDescription() { return this.description; }

}
