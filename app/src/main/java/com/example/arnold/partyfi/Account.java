package com.example.arnold.partyfi;

/**
 * Created by Verbal on 03/12/2014.
 */
public class Account {
    int id;
    String uName;
    String pWord;

    //constructors
    public Account()
    {

    }
    public Account(String uName, String pWord)
    {
        this.uName = uName;
        this.pWord = pWord;

    }
    public Account(int id, String uName, String pWord)
    {
        this.id = id;
        this.uName = uName;
        this.pWord = pWord;
    }

    //setters
    public void setAccount(int id, String uName, String pWord)
    {
        this.id = id;
        this.uName = uName;
        this.pWord = pWord;
    }
    public void setPassword( String pWord)
    {
        this.pWord = pWord;
    }

    //getters
    public int getId()
    {
        return this.id;
    }
    public String getUserName()
    {
        return this.uName;
    }

    public String getPassword()
    {
        return this.pWord;
    }
}
