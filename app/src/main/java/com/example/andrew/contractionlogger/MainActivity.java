package com.example.andrew.contractionlogger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ContractionRecord> records = new ArrayList<>();

        /**
         * Instantiate an adapter to display data from the ContractionRecord
         * objects in the ListView
         */

        //TODO: write the RecordAdapter to finish this declation
        RecordAdapter adapter = new RecordAdapter(this, records);

        // Create a new ListView object to attach to the layout and connect the adapter
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        final Button btnTimer = (Button) findViewById(R.id.btn_timer);

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
    }

    private void stopTimer() {
        //TODO: Create detail for stopTimer method
        isTimerRunning = false;
    }
}
