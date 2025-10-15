package com.clubconnect.registrationservice.controller;

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

import com.clubconnect.registrationservice.dto.RegistrationDTO;
import com.clubconnect.registrationservice.service.RegistrationService;

@RestController
public class RegistrationController {

    RegistrationService _RegistrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this._RegistrationService = registrationService;
    }

    @GetMapping("/")
    public Map<String, String> home() {
        try {
            Map<String, String> response = new HashMap<>();
            response.put("service", "Registration Service");
            response.put("port", "8084");
            response.put("status", "running");
            return response;
        } catch (Exception e) {
            System.out.println("Error in home endpoint: " + e.getMessage());
            throw new RuntimeException("Error in home endpoint", e);
        }
    }

    @GetMapping("/registrations")
    public List<RegistrationDTO> getAllRegistrations() {
        try {
            return _RegistrationService.GetAllRegistrations();
        } catch (Exception e) {
            System.out.println("Error fetching all registrations: " + e.getMessage());
            throw new RuntimeException("Error fetching all registrations", e);
        }
    }

    @GetMapping("/registrations/{id}")
    public ResponseEntity<RegistrationDTO> getRegistrationById(@PathVariable("id") long id) {
        try {
            RegistrationDTO registration = _RegistrationService.GetRegistrationById(id);
            if (registration != null) {
                return ResponseEntity.ok(registration);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Error fetching registration by ID: " + e.getMessage());
            throw new RuntimeException("Error fetching registration by ID", e);
        }
    }

    @GetMapping("/registrations/member/{memberId}")
    public List<RegistrationDTO> getRegistrationsByMember(@PathVariable("memberId") long memberId) {
        try {
            return _RegistrationService.GetRegistrationsByMember(memberId);
        } catch (Exception e) {
            System.out.println("Error fetching registrations by member ID: " + e.getMessage());
            throw new RuntimeException("Error fetching registrations by member ID", e);
        }
    }

    @GetMapping("/registrations/event/{eventId}")
    public List<RegistrationDTO> getRegistrationsByEvent(@PathVariable("eventId") long eventId) {
        try {
            return _RegistrationService.GetRegistrationsByEvent(eventId);
        } catch (Exception e) {
            System.out.println("Error fetching registrations by event ID: " + e.getMessage());
            throw new RuntimeException("Error fetching registrations by event ID", e);
        }
    }
    
    @PostMapping("/registrations/member/{memberId}/event/{eventId}")
    public ResponseEntity<RegistrationDTO> registerMemberForEvent(@PathVariable("memberId") long memberId, @PathVariable("eventId") long eventId) {
        try {
            RegistrationDTO registration = _RegistrationService.RegisterMemberForEvent(memberId, eventId);
            if (registration != null) {
                return ResponseEntity.ok(registration);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            System.out.println("Error creating registration: " + e.getMessage());
            throw new RuntimeException("Error creating registration", e);
        }
    }

    @DeleteMapping("/registrations/member/{memberId}/event/{eventId}")
    public Map<String, Object> unregisterMemberFromEvent(@PathVariable("memberId") long memberId, @PathVariable("eventId") long eventId) {
        try {
            boolean unregistered = _RegistrationService.UnregisterMemberFromEvent(memberId, eventId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", unregistered);
            return response;
        } catch (Exception e) {
            System.out.println("Error unregistering member: " + e.getMessage());
            throw new RuntimeException("Error unregistering member", e);
        }
    }
    
    @PutMapping("/registrations/{id}/status")
    public ResponseEntity<RegistrationDTO> updateRegistrationStatus(@PathVariable("id") long id, @RequestBody Map<String, String> statusUpdate) {
        try {
            String status = statusUpdate.get("status");
            RegistrationDTO registration = _RegistrationService.UpdateRegistrationStatus(id, status);
            if (registration != null) {
                return ResponseEntity.ok(registration);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Error updating registration status: " + e.getMessage());
            throw new RuntimeException("Error updating registration status", e);
        }
    }

    @DeleteMapping("/registrations/{id}")
    public Map<String, Object> deleteRegistration(@PathVariable("id") long id) {
        try {
            boolean deleted = _RegistrationService.DeleteRegistration(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", deleted);
            return response;
        } catch (Exception e) {
            System.out.println("Error deleting registration: " + e.getMessage());
            throw new RuntimeException("Error deleting registration", e);
        }
    }

    @GetMapping("/registrations/statistics")
    public Map<String, Object> getRegistrationStatistics() {
        try {
            return _RegistrationService.GetRegistrationStatistics();
        } catch (Exception e) {
            System.out.println("Error fetching registration statistics: " + e.getMessage());
            throw new RuntimeException("Error fetching registration statistics", e);
        }
    }

    @GetMapping("/registrations/event/{eventId}/statistics")
    public Map<String, Object> getEventStatistics(@PathVariable("eventId") long eventId) {
        try {
            return _RegistrationService.GetEventStatistics(eventId);
        } catch (Exception e) {
            System.out.println("Error fetching event statistics: " + e.getMessage());
            throw new RuntimeException("Error fetching event statistics", e);
        }
    }
}