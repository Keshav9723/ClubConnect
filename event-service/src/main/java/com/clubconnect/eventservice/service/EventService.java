package com.clubconnect.eventservice.service;

import com.clubconnect.eventservice.dto.EventDTO;
import java.util.List;
import java.util.Map;

public interface EventService {

    List<EventDTO> GetAllEvents();
    EventDTO GetEventById(long id);
    List<EventDTO> GetEventsByClub(String clubName);
    List<EventDTO> GetUpcomingEvents();
    EventDTO CreateEvent(EventDTO eventDTO);
    EventDTO UpdateEvent(long id, EventDTO eventDTO);
    boolean DeleteEvent(long id);
    boolean RegisterMemberForEvent(long eventId, long memberId);
    Map<String, Object> GetEventStatistics(long id);
}