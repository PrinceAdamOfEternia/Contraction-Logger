package com.example.andrew.contractionlogger;

/**
 * Created by Andrew on 9/01/2018.
 */

public class ContractionRecord {

    private String startTime;
    private String stopTime;
    private String duration;
    private String frequency;

    public ContractionRecord(String start, String stop, String dur, String freq) {
        startTime = start;
        stopTime = stop;
        duration = dur;
        frequency = freq;
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
    public void setStopTime(String time) {
        stopTime = time;
        duration = stopTime - startTime;
    }

    public String getDuration() {
        return duration;
    }

    public String getFrequency() {
        return frequency;
    }


}
