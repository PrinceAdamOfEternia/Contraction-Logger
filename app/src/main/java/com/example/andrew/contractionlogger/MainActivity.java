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

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class MainActivity extends AppCompatActivity {

    Button btnTimer;
    ArrayList<ContractionRecord> records;
    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        records = new ArrayList<>();

        //records.add(new ContractionRecord("start1", "stop1", "dur1", "freq1"));
        //records.add(new ContractionRecord("start2", "stop2", "dur2", "freq2"));

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
                    stopTimer(adapter);
                } else {
                    startTimer(adapter);
                }

            }
        });
    }

    private void startTimer(RecordAdapter mAdapter) {
        //Get the current time from the calendar
        Date currentTime = Calendar.getInstance().getTime();

        // Create a new record in the list and update the view
        records.add(0, new ContractionRecord(currentTime));
        mAdapter.notifyDataSetChanged();

        //Update the state and timer button before exiting
        isTimerRunning = true;
        btnTimer.setText("Stop");
    }

    private void stopTimer(RecordAdapter mAdapter) {
        //Get the current time from the calendar
        Date currentTime = Calendar.getInstance().getTime();

        // Update the current active record and then update the view
        ContractionRecord record = records.get(0);
        record.setStopTime(currentTime);
        records.set(0, record);
        mAdapter.notifyDataSetChanged();

        //Update the state and timer button before exiting
        isTimerRunning = false;
        btnTimer.setText("Start");
    }
}
