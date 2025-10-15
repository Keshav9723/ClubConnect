package com.clubconnect.eventservice.repository;

import com.clubconnect.eventservice.model.Event;
import java.util.List;

public interface EventRepository {

    List<Event> findAll();
    Event findById(long id);
    List<Event> findByClubId(int clubId);
    List<Event> findUpcoming();
    Event save(Event event);
    Integer GetEventCount();
    Event update(Event event);
    boolean deleteById(long id);
}