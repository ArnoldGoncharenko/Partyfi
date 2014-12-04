package com.example.arnold.partyfi;

/**
 * Created by James Laverty on 03/12/2014.
 */
public class Party {
    int id;
    double lat;
    double lng;
    String title;
    //DatePickerDialogue
    //TimePickerDialogue
    String description;
    String address;

    //constructors
    public Party()
    {

    }
    public Party(double lat, double lng, String title, String description, String address)
    {
        this.lat = lat;
        this.lng = lng;
        this.title = title;
        this.description = description;
        this.address = address;
    }
    public Party(int id, double lat, double lng, String title, String description,String address)
    {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.title = title;
        this.description = description;
        this.address = address;
    }

    //setters
    public void setId(int id){ this.id = id; }
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
    public String getTitle()
    {
        return this.title;
    }
    public String getDescription() { return this.description; }

}
