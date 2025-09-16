package com.example.registrationservice.dto;

import java.time.LocalDateTime;

public class RegistrationDTO {
    long _Id;
    long _MemberId;
    long _EventId;
    LocalDateTime _RegistrationDate;
    String _Status;
    String _MemberName;
    String _EventName;
    
    public RegistrationDTO() {
        // Default constructor
    }

    public RegistrationDTO(long id, long memberId, long eventId, LocalDateTime registrationDate, String status, String memberName, String eventName) {
        _Id = id;
        _MemberId = memberId;
        _EventId = eventId;
        _RegistrationDate = registrationDate;
        _Status = status;
        _MemberName = memberName;
        _EventName = eventName;
    }

    // Getters and Setters
    public long get_Id() { 
        return _Id; }
    public void set_Id(long id) { 
        _Id = id; }
    public long get_MemberId() { 
        return _MemberId; }
    public void set_MemberId(long memberId) { 
        _MemberId = memberId; }
    public long get_EventId() { 
        return _EventId; }
    public void set_EventId(long eventId) { 
        _EventId = eventId; }
    public LocalDateTime get_RegistrationDate() { 
        return _RegistrationDate; }
    public void set_RegistrationDate(LocalDateTime registrationDate) { 
        _RegistrationDate = registrationDate; }
    public String get_Status() { 
        return _Status; }
    public void set_Status(String status) { 
        _Status = status; }
    public String get_MemberName() { 
        return _MemberName; }
    public void set_MemberName(String memberName) { 
        _MemberName = memberName; }
    public String get_EventName() { 
        return _EventName; }
    public void set_EventName(String eventName) { 
        _EventName = eventName; }
}