package com.example.andrew.contractionlogger;

import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

import static java.lang.Math.round;

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

    //TODO: Add last start time to the constructor

    public ContractionRecord(Date stTime) {
        dtStartTime = stTime;

        //Create a text representation of the start time
        startTime = df.format(dtStartTime);
        stopTime = dash;
        duration = dash;
        frequency = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    /**
     * Update the object when the stop button is pressed
     **/
    public void setStopTime(Date spTime) {
        dtStopTime = spTime;

        //Create a text representation of the stop time
        stopTime = df.format(dtStopTime);

        duration = createDurationString(dtStopTime.getTime() - dtStartTime.getTime());
    }

    public String getDuration() {
        return duration;
    }

    public String getFrequency() {
        return frequency;
    }

    //TODO: add function for formatting duration and frequency values
    private String createDurationString(Long lgTime) {
        long minutes = lgTime / (60 * 1000);
        long seconds = (lgTime - minutes) / (1000);

        return (minutes + "m " + seconds + "s");
    }
}
