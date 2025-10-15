package com.clubconnect.registrationservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.clubconnect.registrationservice.dto.RegistrationDTO;
import com.clubconnect.registrationservice.model.Registration;
import com.clubconnect.registrationservice.repository.RegistrationRepository;

@Service(value = "RegistrationService")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class RegistrationServiceImpl implements RegistrationService {

    RegistrationRepository _RegistrationRepository;
    ModelMapper _ModelMapper;
    RestClient _RestClient;

    @Autowired
    public RegistrationServiceImpl(RegistrationRepository repo, ModelMapper mapper, RestClient client) {
        this._RegistrationRepository = repo;
        this._ModelMapper = mapper;
        this._RestClient = client;
    }
    
    @Override
    public List<RegistrationDTO> GetAllRegistrations() {
        List<Registration> registrations = _RegistrationRepository.findAll();
        List<RegistrationDTO> dtos = new ArrayList<>();
        for (Registration reg : registrations) {
            dtos.add(_ModelMapper.map(reg, RegistrationDTO.class));
        }
        return dtos;
    }

    @Override
    public RegistrationDTO GetRegistrationById(long id) {
        Registration reg = _RegistrationRepository.findById(id);
        if (reg != null) {
            return _ModelMapper.map(reg, RegistrationDTO.class);
        }
        return null;
    }

    @Override
    public List<RegistrationDTO> GetRegistrationsByMember(long memberId) {
        List<Registration> registrations = _RegistrationRepository.findByMemberId(memberId);
        List<RegistrationDTO> dtos = new ArrayList<>();
        for (Registration reg : registrations) {
            dtos.add(_ModelMapper.map(reg, RegistrationDTO.class));
        }
        return dtos;
    }

    @Override
    public List<RegistrationDTO> GetRegistrationsByEvent(long eventId) {
        List<Registration> registrations = _RegistrationRepository.findByEventId(eventId);
        List<RegistrationDTO> dtos = new ArrayList<>();
        for (Registration reg : registrations) {
            dtos.add(_ModelMapper.map(reg, RegistrationDTO.class));
        }
        return dtos;
    }

    @Override
    public RegistrationDTO CreateRegistration(RegistrationDTO registrationDTO) {
        Registration reg = _ModelMapper.map(registrationDTO, Registration.class);
        Registration savedReg = _RegistrationRepository.save(reg);
        return _ModelMapper.map(savedReg, RegistrationDTO.class);
    }

    @Override
    public RegistrationDTO RegisterMemberForEvent(long memberId, long eventId) {
        try {
            Map<String, Object> member = _RestClient.get().uri("lb://member-service/members/{id}", memberId).retrieve().body(Map.class);
            String memberName = (String) member.get("name");

            Map<String, Object> event = _RestClient.get().uri("lb://event-service/events/{id}", eventId).retrieve().body(Map.class);
            String eventName = (String) event.get("name");
            
            if (memberName == null || eventName == null) {
                return null;
            }

            Registration newReg = new Registration(0, memberId, eventId, LocalDateTime.now(), "CONFIRMED", memberName, eventName);
            Registration savedReg = _RegistrationRepository.save(newReg);
            return _ModelMapper.map(savedReg, RegistrationDTO.class);
            
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean UnregisterMemberFromEvent(long memberId, long eventId) {
        return _RegistrationRepository.deleteByMemberIdAndEventId(memberId, eventId);
    }

    @Override
    public RegistrationDTO UpdateRegistrationStatus(long id, String status) {
        Registration updatedReg = _RegistrationRepository.updateStatus(id, status);
        if (updatedReg != null) {
            return _ModelMapper.map(updatedReg, RegistrationDTO.class);
        }
        return null;
    }

    @Override
    public boolean DeleteRegistration(long id) {
        return _RegistrationRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> GetRegistrationStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRegistrations", _RegistrationRepository.count());
        return stats;
    }

    @Override
    public Map<String, Object> GetEventStatistics(long eventId) {
        Map<String, Object> stats = new HashMap<>();
        long count = _RegistrationRepository.findByEventId(eventId).size();
        stats.put("count", count);
        return stats;
    }
}