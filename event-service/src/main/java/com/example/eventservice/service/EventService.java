package com.example.eventservice.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.eventservice.dto.EventDTO;

public interface EventService {
    List<EventDTO> getAllEvents();
    Optional<EventDTO> getEventById(long id);
    List<EventDTO> getEventsByClub(String clubName);
    List<EventDTO> getUpcomingEvents();
    EventDTO createEvent(EventDTO eventDTO);
    EventDTO updateEvent(long id, EventDTO eventDTO);
    void deleteEvent(long id);
    boolean registerMemberForEvent(long eventId, long memberId);
    Map<String, Object> getEventStatistics(long id);
    // You can add an unregister method here as well
}