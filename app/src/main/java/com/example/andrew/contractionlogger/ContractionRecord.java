package com.example.andrew.contractionlogger;

import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Andrew on 9/01/2018.
 */

public class ContractionRecord {

    private String startTime;
    private String stopTime;
    private String duration;
    private String frequency;

    private Date dtStartTime;
    private Date dtStopTime;

    private String dash = "-";

    //Create a formatter for the time
    private DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);

    //TODO: Work out correct constructor

    public ContractionRecord(Date stTime) {
        dtStartTime = stTime;

        //Create a text representation of the start time
        startTime = df.format(dtStartTime);
        stopTime = dash;
        duration = dash;
        frequency = startTime;

        Log.v("The time: ", startTime);
    }

    public String getStartTime() {
        return startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    /**
     * Set functions are required to update the object when the stop button is pressed
     **/
    public void setStopTime(Date spTime) {
        dtStopTime = spTime;

        //Create a text representation of the stop time
        stopTime = df.format(dtStopTime);

        //duration = stopTime - startTime;
    }

    public String getDuration() {
        return duration;
    }

    public String getFrequency() {
        return frequency;
    }

    //TODO: add function for formatting duration and frequency values
}
