package com.clubconnect.eventservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.clubconnect.eventservice.dto.EventDTO;
import com.clubconnect.eventservice.model.Event;
import com.clubconnect.eventservice.repository.EventRepository;

@Service(value = "EventService")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class EventServiceImpl implements EventService {

    EventRepository _EventRepository;
    ModelMapper _ModelMapper;
    RestClient _RestClient;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ModelMapper modelMapper, RestClient restClient) {
        this._EventRepository = eventRepository;
        this._ModelMapper = modelMapper;
        this._RestClient = restClient;
    }

    @Override
    public List<EventDTO> GetAllEvents() {
        List<Event> events = _EventRepository.findAll();
        List<EventDTO> eventDtos = new ArrayList<>();
        for (Event event : events) {
            eventDtos.add(_ModelMapper.map(event, EventDTO.class));
        }
        return eventDtos;
    }

    @Override
    public EventDTO GetEventById(long id) {
        Event event = _EventRepository.findById(id);
        if (event != null) {
            return _ModelMapper.map(event, EventDTO.class);
        }
        return null;
    }

    @Override
    public List<EventDTO> GetEventsByClub(String clubName) {
        try {
            Map<String, Object> club = _RestClient.get()
                    .uri("lb://club-service/clubs/name/{clubName}", clubName)
                    .retrieve()
                    .body(Map.class);
            
            if (club != null && club.containsKey("id")) {
                int clubId = (Integer) club.get("id");
                List<Event> events = _EventRepository.findByClubId(clubId);
                List<EventDTO> eventDtos = new ArrayList<>();
                for (Event event : events) {
                    eventDtos.add(_ModelMapper.map(event, EventDTO.class));
                }
                return eventDtos;
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<EventDTO> GetUpcomingEvents() {
        List<Event> events = _EventRepository.findUpcoming();
        List<EventDTO> eventDtos = new ArrayList<>();
        for (Event event : events) {
            eventDtos.add(_ModelMapper.map(event, EventDTO.class));
        }
        return eventDtos;
    }

    @Override
    public EventDTO CreateEvent(EventDTO eventDTO) {
        Event event = _ModelMapper.map(eventDTO, Event.class);
        Event savedEvent = _EventRepository.save(event);
        return _ModelMapper.map(savedEvent, EventDTO.class);
    }

    @Override
    public EventDTO UpdateEvent(long id, EventDTO eventDTO) {
        Event event = _ModelMapper.map(eventDTO, Event.class);
        event.set_Id(id);
        Event updatedEvent = _EventRepository.update(event);
        return _ModelMapper.map(updatedEvent, EventDTO.class);
    }



    @Override
    public boolean DeleteEvent(long id) {
        return _EventRepository.deleteById(id);
    }
    
    @Override
    public boolean RegisterMemberForEvent(long eventId, long memberId) {
        if (_EventRepository.findById(eventId) == null) {
            return false;
        }

        try {
            Map<String, Long> requestBody = Map.of("memberId", memberId, "eventId", eventId);
            
            Map response = _RestClient.post()
                    .uri("lb://registration-service/registrations")
                    .body(requestBody)
                    .retrieve()
                    .body(Map.class);
                    
            return response != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Map<String, Object> GetEventStatistics(long id) {
        Map<String, Object> stats = new HashMap<>();
        Event event = _EventRepository.findById(id);

        if (event == null) {
            stats.put("error", "Event not found");
            return stats;
        }

        stats.put("eventName", event.get_Name());

        try {
            Map<String, Object> registrationStats = _RestClient.get()
                    .uri("lb://registration-service/registrations/event/{id}/statistics", id)
                    .retrieve()
                    .body(Map.class);
            
            stats.put("registeredMembers", registrationStats.getOrDefault("count", 0));
        } catch (Exception e) {
            stats.put("registeredMembers", "Error fetching data");
        }
        
        return stats;
    }
}