package com.clubconnect.eventservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.clubconnect.eventservice.dto.EventDTO;
import com.clubconnect.eventservice.service.EventService;

@RestController
public class EventController {

    EventService _EventService;

    @Autowired
    public EventController(EventService eventService) {
        this._EventService = eventService;
    }

    @GetMapping("/")
    public Map<String, String> home() {
        try {
            Map<String, String> response = new HashMap<>();
            response.put("service", "Event Service");
            response.put("port", "8083");
            response.put("status", "running");
            return response;
        } catch (Exception e) {
            System.out.println("Error in home endpoint: " + e.getMessage());
            throw new RuntimeException("Error in home endpoint", e);
        }
    }

    @GetMapping("/events")
    public List<EventDTO> getEvents() {
        try {
            return _EventService.GetAllEvents();
        } catch (Exception e) {
            System.out.println("Error fetching all events: " + e.getMessage());
            throw new RuntimeException("Error fetching all events", e);
        }
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable("id") long id) {
        try {
            EventDTO event = _EventService.GetEventById(id);
            if (event != null) {
                return ResponseEntity.ok(event);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Error fetching event by ID: " + e.getMessage());
            throw new RuntimeException("Error fetching event by ID", e);
        }
    }

    @GetMapping("/events/club/{clubName}")
    public List<EventDTO> getEventsByClub(@PathVariable("clubName") String clubName) {
        try {
            return _EventService.GetEventsByClub(clubName);
        } catch (Exception e) {
            System.out.println("Error fetching events by club name: " + e.getMessage());
            throw new RuntimeException("Error fetching events by club name", e);
        }
    }

    @GetMapping("/events/upcoming")
    public List<EventDTO> getUpcomingEvents() {
        try {
            return _EventService.GetUpcomingEvents();
        } catch (Exception e) {
            System.out.println("Error fetching upcoming events: " + e.getMessage());
            throw new RuntimeException("Error fetching upcoming events", e);
        }
    }

    @PostMapping("/events")
    public EventDTO createEvent(@RequestBody EventDTO eventDTO) {
        try {
            return _EventService.CreateEvent(eventDTO);
        } catch (Exception e) {
            System.out.println("Error creating event: " + e.getMessage());
            throw new RuntimeException("Error creating event", e);
        }
    }

    @PutMapping("/events/{id}")
    public EventDTO updateEvent(@PathVariable("id") long id, @RequestBody EventDTO eventDTO) {
        try {
            return _EventService.UpdateEvent(id, eventDTO);
        } catch (Exception e) {
            System.out.println("Error updating event: " + e.getMessage());
            throw new RuntimeException("Error updating event", e);
        }
    }

    @DeleteMapping("/events/{id}")
    public Map<String, Object> deleteEvent(@PathVariable("id") long id) {
        try {
            boolean deleted = _EventService.DeleteEvent(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", deleted);
            response.put("message", deleted ? "Event deleted successfully" : "Event not found or could not be deleted");
            return response;
        } catch (Exception e) {
            System.out.println("Error deleting event: " + e.getMessage());
            throw new RuntimeException("Error deleting event", e);
        }
    }

    @PostMapping("/events/{eventId}/register/{memberId}")
    public Map<String, Object> registerMemberForEvent(@PathVariable("eventId") long eventId, @PathVariable("memberId") long memberId) {
        try {
            boolean success = _EventService.RegisterMemberForEvent(eventId, memberId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", success);
            response.put("message", success ? "Registration successful" : "Registration failed (event or member may not exist)");
            return response;
        } catch (Exception e) {
            System.out.println("Error registering member for event: " + e.getMessage());
            throw new RuntimeException("Error registering member for event", e);
        }
    }

    @GetMapping("/events/{id}/statistics")
    public Map<String, Object> getEventStatistics(@PathVariable("id") long id) {
        try {
            return _EventService.GetEventStatistics(id);
        } catch (Exception e) {
            System.out.println("Error fetching event statistics: " + e.getMessage());
            throw new RuntimeException("Error fetching event statistics", e);
        }
    }
}