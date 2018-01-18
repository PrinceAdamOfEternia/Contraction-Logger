package com.example.andrew.contractionlogger;


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

    public ContractionRecord(Date stTime, Date lastTime) {
        dtStartTime = stTime;

        //Create a text representation of the start time
        startTime = df.format(dtStartTime);
        stopTime = dash;
        duration = dash;
        frequency = createDurationString(dtStartTime.getTime() - lastTime.getTime());
    }

    public ContractionRecord(Date stTime) {
        dtStartTime = stTime;

        //Create a text representation of the start time
        startTime = df.format(dtStartTime);
        stopTime = dash;
        duration = dash;
        frequency = dash;
    }

    public ContractionRecord(String start, String stop, String dur, String freq, String date) {
        startTime = start;
        stopTime = stop;
        duration = dur;
        frequency = freq;
        dtStartTime = df.parse(date);
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

    public Date getDtStartTime() {
        return dtStartTime;
    }

    public String getDuration() {
        return duration;
    }

    public String getFrequency() {
        return frequency;
    }

    /**
     * This function takes is the long representation of the time generated by Date.getTime() and converts
     * it to a String representation in the form "#m #s" (ie minutes and seconds)
     *
     * @param lgTime - long representation of milliseconds since midnight
     * @return - String in format "#m #s"
     */
    private String createDurationString(Long lgTime) {
        long msInMinute = 60 * 1000;
        long minutes = lgTime / msInMinute;
        long seconds = (lgTime - minutes * msInMinute) / (1000);

        return (minutes + "m " + seconds + "s");
    }

    @Override
    public String toString() {
        return startTime + "," + stopTime + "," + duration + "," + frequency + "," + dtStartTime.toString();
    }
}
