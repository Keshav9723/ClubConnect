package com.example.eventservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventservice.dto.EventDTO;
import com.example.eventservice.service.EventService;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "Event Service");
        response.put("port", "8083");
        response.put("status", "running");
        return response;
    }

    @GetMapping("/events")
    public List<EventDTO> getEvents() {
        return eventService.getAllEvents();
    }



    @GetMapping("/events/{id}")
    public EventDTO getEventById(@PathVariable long id) {
        return eventService.getEventById(id).orElse(null);
    }

    @GetMapping("/events/club/{clubName}")
    public List<EventDTO> getEventsByClub(@PathVariable String clubName) {
        return eventService.getEventsByClub(clubName);
    }

    @GetMapping("/events/upcoming")
    public List<EventDTO> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }

    @PostMapping("/events")
    public EventDTO createEvent(@RequestBody EventDTO eventDTO) {
        return eventService.createEvent(eventDTO);
    }

    @PutMapping("/events/{id}")
    public EventDTO updateEvent(@PathVariable long id, @RequestBody EventDTO eventDTO) {
        return eventService.updateEvent(id, eventDTO);
    }

    @DeleteMapping("/events/{id}")
    public Map<String, String> deleteEvent(@PathVariable long id) {
        eventService.deleteEvent(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Event deleted successfully");
        return response;
    }

    @PostMapping("/events/{eventId}/register/{memberId}")
    public Map<String, Object> registerMemberForEvent(@PathVariable long eventId, @PathVariable long memberId) {
        boolean success = eventService.registerMemberForEvent(eventId, memberId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "Registration successful" : "Registration failed");
        return response;
    }

    // You would add the unregister endpoint here, calling a corresponding service method

    @GetMapping("/events/{id}/statistics")
    public Map<String, Object> getEventStatistics(@PathVariable long id) {
        return eventService.getEventStatistics(id);
    }
}