package com.example.registrationservice.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.registrationservice.dto.RegistrationDTO;
import com.example.registrationservice.service.RegistrationService;

@RestController
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "Registration Service");
        response.put("port", "8084");
        response.put("status", "running");
        return response;
    }

    @GetMapping("/registrations")
    public List<RegistrationDTO> getRegistrations() {
        return registrationService.getAllRegistrations();
    }

    @GetMapping("/registrations/{id}")
    public ResponseEntity<RegistrationDTO> getRegistrationById(@PathVariable long id) {
        return registrationService.getRegistrationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/registrations/member/{memberId}")
    public List<RegistrationDTO> getRegistrationsByMember(@PathVariable long memberId) {
        return registrationService.getRegistrationsByMember(memberId);
    }

    @GetMapping("/registrations/event/{eventId}")
    public List<RegistrationDTO> getRegistrationsByEvent(@PathVariable long eventId) {
        return registrationService.getRegistrationsByEvent(eventId);
    }

    @PostMapping("/registrations")
    public RegistrationDTO createRegistration(@RequestBody RegistrationDTO registrationDTO) {
        return registrationService.createRegistration(registrationDTO);
    }

    @PostMapping("/registrations/register/{memberId}/{eventId}")
    public ResponseEntity<RegistrationDTO> registerMemberForEvent(@PathVariable long memberId, @PathVariable long eventId) {
        return registrationService.registerMemberForEvent(memberId, eventId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/registrations/unregister/{memberId}/{eventId}")
    public Map<String, Object> unregisterMemberFromEvent(@PathVariable long memberId, @PathVariable long eventId) {
        boolean success = registrationService.unregisterMemberFromEvent(memberId, eventId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "Unregistration successful" : "Unregistration failed");
        return response;
    }

    @PutMapping("/registrations/{id}/status")
    public ResponseEntity<RegistrationDTO> updateRegistrationStatus(@PathVariable long id, @RequestParam String status) {
        return registrationService.updateRegistrationStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/registrations/{id}")
    public Map<String, String> deleteRegistration(@PathVariable long id) {
        registrationService.deleteRegistration(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Registration deleted successfully");
        return response;
    }

    @GetMapping("/registrations/statistics")
    public Map<String, Object> getRegistrationStatistics() {
        return registrationService.getRegistrationStatistics();
    }
}