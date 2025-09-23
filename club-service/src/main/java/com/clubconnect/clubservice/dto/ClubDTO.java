package com.clubconnect.clubservice.dto;

public class ClubDTO {
    
    String _Name;
    String _Description;
    String _Category;

    
    public String get_Name() { return _Name; }
    public void set_Name(String name) { _Name = name; }
    public String get_Description() { return _Description; }
    public void set_Description(String description) { _Description = description; }
    public String get_Category() { return _Category; }
    public void set_Category(String category) { _Category = category; }
}