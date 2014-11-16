package com.example.alexey.parsecrashproject;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.Date;
import java.util.UUID;

@ParseClassName("CalendarEvent")
public class CalendarEvent extends ParseObject {
    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }

    public ParseUser getAuthor() {
        return getParseUser("author");
    }

    public void setAuthor(ParseUser currentUser) {
        put("author", currentUser);
    }

    public void setUuidString() {
        UUID uuid = UUID.randomUUID();
        put("uuid", uuid.toString());
    }

    public String getUuidString() {
        return getString("uuid");
    }

    public static ParseQuery<CalendarEvent> getQuery() {
        return ParseQuery.getQuery(CalendarEvent.class);
    }

    public void setData(Date date) {
        put("date", date);
    }

    public void setEventCategory(int category) {
        put("category", category);
    }

    public int getEventCategory() {
        return getInt("category");
    }

    public Date getDate() {
        return getDate("date");
    }

    public void setDescription(String description) {
        put("description", description);
    }

    public String getDescription() {
        return getString("description");

    }
}


