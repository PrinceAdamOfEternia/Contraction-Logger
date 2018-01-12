package com.example.andrew.contractionlogger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    Button btnTimer;
    Calendar myCal = Calendar.getInstance();
    ArrayList<ContractionRecord> records;
    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        records = new ArrayList<>();

        records.add(new ContractionRecord("start1", "stop1", "dur1", "freq1"));
        records.add(new ContractionRecord("start2", "stop2", "dur2", "freq2"));

        //Instantiate a new adapter to load the record items into the list
        final RecordAdapter adapter = new RecordAdapter(this, records);

        // Instantiate a new ListView object to attach to the layout and connect the adapter
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        btnTimer = (Button) findViewById(R.id.btn_timer);

        btnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    stopTimer();
                } else {
                    startTimer(adapter);
                }

            }
        });
    }

    private void startTimer(RecordAdapter mAdapter) {
        //TODO: Create detail for startTimer method


        //use this code to format the time into a string
        DateFormat df1 = DateFormat.getTimeInstance(DateFormat.SHORT);
        DateFormat df2 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

        //Use this code to get the current time
        Date currentTime = myCal.getTime();

        Log.v("The time: ", df1.format(currentTime));
        Log.v("The time: ", df2.format(currentTime));
        //TODO: call adapter method to update data_view

        String sTime = df1.format(currentTime);

        records.add(0, new ContractionRecord(sTime, sTime, sTime, sTime));
        mAdapter.notifyDataSetChanged();

        //Update the state and timer button before exiting
        isTimerRunning = true;
        btnTimer.setText("Stop");
    }

    private void stopTimer() {
        //TODO: Create detail for stopTimer method


        //TODO: call adapter method to update data_view

        //Update the state and timer button before exiting
        isTimerRunning = false;
        btnTimer.setText("Start");
    }
}
