package com.clubconnect.eventservice.repository;

import java.util.List;
import java.util.Optional;

import com.clubconnect.eventservice.model.Event;

public interface EventRepository {
    List<Event> findAll();
    Optional<Event> findById(long id);
    List<Event> findByClubId(int clubId);
    List<Event> findUpcoming();
    Event save(Event event);
    Event update(Event event);
    void deleteById(long id);
    Integer GetEventCount();
}