package com.example.registrationservice.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.registrationservice.dto.RegistrationDTO;

public interface RegistrationService {
    List<RegistrationDTO> getAllRegistrations();
    Optional<RegistrationDTO> getRegistrationById(long id);
    List<RegistrationDTO> getRegistrationsByMember(long memberId);
    List<RegistrationDTO> getRegistrationsByEvent(long eventId);
    RegistrationDTO createRegistration(RegistrationDTO registrationDTO);
    Optional<RegistrationDTO> registerMemberForEvent(long memberId, long eventId);
    boolean unregisterMemberFromEvent(long memberId, long eventId);
    Optional<RegistrationDTO> updateRegistrationStatus(long id, String status);
    void deleteRegistration(long id);
    Map<String, Object> getRegistrationStatistics();
}