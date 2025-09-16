package com.example.memberservice.dto;

public class MemberDTO {
    long _Id;
    String _Name;
    String _Email;
    String _Phone;
    int _ClubId;

    public MemberDTO() {
        // Default constructor
    }

    public MemberDTO(long id, String name, String email, String phone, int clubId) {
        _Id = id;
        _Name = name;
        _Email = email;
        _Phone = phone;
        _ClubId = clubId;
    }

    // Setters and Getters
    public long get_Id() { return _Id; }
    public void set_Id(long id) { _Id = id; }
    public String get_Name() { return _Name; }
    public void set_Name(String name) { _Name = name; }
    public String get_Email() { return _Email; }
    public void set_Email(String email) { _Email = email; }
    public String get_Phone() { return _Phone; }
    public void set_Phone(String phone) { _Phone = phone; }
    public int get_ClubId() { return _ClubId; }
    public void set_ClubId(int clubId) { _ClubId = clubId; }
}