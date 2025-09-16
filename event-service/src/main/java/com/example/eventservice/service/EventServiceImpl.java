package com.example.eventservice.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.eventservice.dto.EventDTO;
import com.example.eventservice.model.Event;
import com.example.eventservice.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final RestClient restClient;
    // You'd typically load these from a config file
    private final String clubServiceUrl = "http://localhost:8081";
    private final String registrationServiceUrl = "http://localhost:8084"; // Assuming port for registration service

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ModelMapper modelMapper, RestClient restClient) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.restClient = restClient;
    }
    
    // Helper method to convert Entity to DTO
    private EventDTO toDto(Event event) {
        return modelMapper.map(event, EventDTO.class);
    }
    
    // Helper method to convert DTO to Entity
    private Event toEntity(EventDTO dto) {
        return modelMapper.map(dto, Event.class);
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<EventDTO> getEventById(long id) {
        return eventRepository.findById(id).map(this::toDto);
    }

    @Override
    public List<EventDTO> getEventsByClub(String clubName) {
        // 1. Call ClubService to get the club's ID from its name
        // This assumes ClubService has a DTO that includes the club's ID
        try {
            Map<String, Object> club = restClient.get()
                .uri(clubServiceUrl + "/clubs/name/" + clubName)
                .retrieve()
                .body(Map.class);
            
            if (club != null && club.containsKey("_Id")) {
                int clubId = (Integer) club.get("_Id");
                // 2. Use the clubId to find events
                return eventRepository.findByClubId(clubId).stream().map(this::toDto).collect(Collectors.toList());
            }
        } catch (Exception e) {
            // Handle cases where club is not found or club-service is down
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<EventDTO> getUpcomingEvents() {
        return eventRepository.findUpcoming().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = toEntity(eventDTO);
        return toDto(eventRepository.save(event));
    }

    @Override
    public EventDTO updateEvent(long id, EventDTO eventDTO) {
        Event event = toEntity(eventDTO);
        event.set_Id(id); // Ensure the ID from the path is used
        return toDto(eventRepository.update(event));
    }

    @Override
    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }
    
    @Override
    public boolean registerMemberForEvent(long eventId, long memberId) {
        // 1. Check if the event exists
        if (eventRepository.findById(eventId).isEmpty()) {
            return false;
        }

        // 2. Call RegistrationService to create the registration
        // This assumes a POST endpoint on RegistrationService like /registrations
        try {
            Map<String, Long> requestBody = Map.of("memberId", memberId, "eventId", eventId);
            
            Map response = restClient.post()
                .uri(registrationServiceUrl + "/registrations")
                .body(requestBody)
                .retrieve()
                .body(Map.class);
                
            return response != null; // Success if we get any response back
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Map<String, Object> getEventStatistics(long id) {
        Map<String, Object> stats = new HashMap<>();
        Optional<Event> event = eventRepository.findById(id);

        if (event.isEmpty()) {
            stats.put("error", "Event not found");
            return stats;
        }

        stats.put("eventName", event.get().get_Name());

        // Call RegistrationService to get the count of registered members
        // Assumes a GET endpoint like /registrations/event/{id}/count
        try {
            Map<String, Object> registrationStats = restClient.get()
                .uri(registrationServiceUrl + "/registrations/event/" + id + "/statistics")
                .retrieve()
                .body(Map.class);
            
            stats.put("registeredMembers", registrationStats.getOrDefault("count", 0));
        } catch (Exception e) {
            stats.put("registeredMembers", "Error fetching data");
        }
        
        return stats;
    }
}