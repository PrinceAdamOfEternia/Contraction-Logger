package com.example.andrew.contractionlogger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnTimer;
    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ContractionRecord> records = new ArrayList<>();

        records.add(new ContractionRecord("start1", "stop1", "dur1", "freq1"));
        records.add(new ContractionRecord("start2", "stop2", "dur2", "freq2"));

        //Instantiate a new adapter to load the record items into the list
        RecordAdapter adapter = new RecordAdapter(this, records);

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
                    startTimer();
                }

            }
        });
    }

    private void startTimer() {
        //TODO: Create detail for startTimer method
        isTimerRunning = true;
        btnTimer.setText("Stop");

        //TODO: call adapter method to update data_view
    }

    private void stopTimer() {
        //TODO: Create detail for stopTimer method
        isTimerRunning = false;
        btnTimer.setText("Start");

        //TODO: call adapter method to update data_view
    }
}
