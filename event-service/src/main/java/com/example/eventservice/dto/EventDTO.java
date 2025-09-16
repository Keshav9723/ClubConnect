package com.example.eventservice.dto;

import java.time.LocalDateTime;

public class EventDTO {
    long _Id;
    String _Name;
    String _Description;
    String _Location;
    LocalDateTime _DateTime;
    int _ClubId;

    // Default constructor
    public EventDTO() {}
    
    // Parameterized constructor (Added for consistency)
    public EventDTO(long id, String name, String description, String location, LocalDateTime dateTime, int clubId) {
        _Id = id;
        _Name = name;
        _Description = description;
        _Location = location;
        _DateTime = dateTime;
        _ClubId = clubId;
    }

    // Getters and Setters
    public long get_Id() { return _Id; }
    public void set_Id(long id) { _Id = id; }
    public String get_Name() { return _Name; }
    public void set_Name(String name) { _Name = name; }
    public String get_Description() { return _Description; }
    public void set_Description(String description) { _Description = description; }
    public String get_Location() { return _Location; }
    public void set_Location(String location) { _Location = location; }
    public LocalDateTime get_DateTime() { return _DateTime; }
    public void set_DateTime(LocalDateTime dateTime) { _DateTime = dateTime; }
    public int get_ClubId() { return _ClubId; }
    public void set_ClubId(int clubId) { _ClubId = clubId; }
}