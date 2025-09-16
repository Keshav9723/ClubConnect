package com.example.clubservice.dto;

public class ClubDTO {
    int _Id;
    String _Name;
    String _Description;
    String _Category;

    // Setters and Getters
    public int get_Id() { return _Id; }
    public void set_Id(int id) { _Id = id; }
    public String get_Name() { return _Name; }
    public void set_Name(String name) { _Name = name; }
    public String get_Description() { return _Description; }
    public void set_Description(String description) { _Description = description; }
    public String get_Category() { return _Category; }
    public void set_Category(String category) { _Category = category; }
}