package com.clubconnect.registrationservice.service;

import com.clubconnect.registrationservice.dto.RegistrationDTO;
import java.util.List;
import java.util.Map;

public interface RegistrationService {

    List<RegistrationDTO> GetAllRegistrations();
    RegistrationDTO GetRegistrationById(long id);
    List<RegistrationDTO> GetRegistrationsByMember(long memberId);
    List<RegistrationDTO> GetRegistrationsByEvent(long eventId);
    RegistrationDTO CreateRegistration(RegistrationDTO registrationDTO);
    RegistrationDTO RegisterMemberForEvent(long memberId, long eventId);
    boolean UnregisterMemberFromEvent(long memberId, long eventId);
    RegistrationDTO UpdateRegistrationStatus(long id, String status);
    boolean DeleteRegistration(long id);
    Map<String, Object> GetRegistrationStatistics();
    Map<String, Object> GetEventStatistics(long eventId);
}