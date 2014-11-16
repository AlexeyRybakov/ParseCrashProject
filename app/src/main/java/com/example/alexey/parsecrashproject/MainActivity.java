package com.example.alexey.parsecrashproject;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.CountCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;



public class MainActivity extends Activity {

    View progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText)findViewById(R.id.editText);
        final Button generate = (Button)findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int eventsCount = Integer.valueOf(editText.getText().toString());
                generateEvents(eventsCount);
            }
        });

        View button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CalendarEvent.getQuery().fromLocalDatastore().countInBackground(new CountCallback() {
                   @Override
                   public void done(int i, ParseException e) {
                       Toast.makeText(getApplicationContext(),"count "+i,Toast.LENGTH_SHORT).show();
                   }
               });
            }
        });
    }

    private void generateEvents(final int count){
        progressBar.setVisibility(View.VISIBLE);

        ParseQuery<CalendarEvent> query = CalendarEvent.getQuery();
        query.fromLocalDatastore();
        query.orderByDescending("date");
        query.getFirstInBackground(new GetCallback<CalendarEvent>() {
            @Override
            public void done(CalendarEvent calendarEvent, ParseException e) {

                Date startDate = null;
                if(calendarEvent!=null){
                    startDate = calendarEvent.getDate();
                }else{
                    startDate = new Date();
                }
                Log.d("myLogs","startDate "+startDate);
                generateEvents(count,startDate);

            }
        });
    }

    private void generateEvents(int count, Date startDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        ArrayList<CalendarEvent> events = new ArrayList<CalendarEvent>(count);

        for (int i = 0; i<count;i++){

            CalendarEvent event = new CalendarEvent();
            event.setUuidString();
            event.setAuthor(ParseUser.getCurrentUser());
            event.setData(calendar.getTime());
            event.setTitle("title "+i);
            event.setDescription("description "+i);
            if(i%2==1){
                event.setEventCategory(1);
            }else{
                event.setEventCategory(2);
            }
            events.add(event);
            calendar.add(Calendar.DATE,1);
        }

        CalendarEvent.pinAllInBackground(events, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                progressBar.setVisibility(View.GONE);
                Log.e("myLogs", "save completed " + e);
            }
        });
    }
}
