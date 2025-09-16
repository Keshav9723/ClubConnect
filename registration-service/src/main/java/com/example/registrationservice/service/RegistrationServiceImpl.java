package com.example.registrationservice.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.registrationservice.dto.RegistrationDTO;
import com.example.registrationservice.model.Registration;
import com.example.registrationservice.repository.RegistrationRepository;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final ModelMapper modelMapper;
    private final RestClient restClient;
    private final String memberServiceUrl = "http://localhost:8082";
    private final String eventServiceUrl = "http://localhost:8083";

    @Autowired
    public RegistrationServiceImpl(RegistrationRepository repo, ModelMapper mapper, RestClient client) {
        this.registrationRepository = repo;
        this.modelMapper = mapper;
        this.restClient = client;
    }

    private RegistrationDTO toDto(Registration reg) {
        return modelMapper.map(reg, RegistrationDTO.class);
    }

    private Registration toEntity(RegistrationDTO dto) {
        return modelMapper.map(dto, Registration.class);
    }
    
    @Override
    public List<RegistrationDTO> getAllRegistrations() {
        return registrationRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<RegistrationDTO> getRegistrationById(long id) {
        return registrationRepository.findById(id).map(this::toDto);
    }

    @Override
    public List<RegistrationDTO> getRegistrationsByMember(long memberId) {
        return registrationRepository.findByMemberId(memberId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<RegistrationDTO> getRegistrationsByEvent(long eventId) {
        return registrationRepository.findByEventId(eventId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public RegistrationDTO createRegistration(RegistrationDTO registrationDTO) {
        return toDto(registrationRepository.save(toEntity(registrationDTO)));
    }

    @Override
    public Optional<RegistrationDTO> registerMemberForEvent(long memberId, long eventId) {
        try {
            // 1. Fetch member name from MemberService
            Map<String, Object> member = restClient.get().uri(memberServiceUrl + "/members/" + memberId).retrieve().body(Map.class);
            String memberName = (String) member.get("_Name");

            // 2. Fetch event name from EventService
            Map<String, Object> event = restClient.get().uri(eventServiceUrl + "/events/" + eventId).retrieve().body(Map.class);
            String eventName = (String) event.get("_Name");
            
            if (memberName == null || eventName == null) return Optional.empty();

            // 3. Create and save the new registration
            Registration newReg = new Registration(0, memberId, eventId, LocalDateTime.now(), "CONFIRMED", memberName, eventName);
            Registration savedReg = registrationRepository.save(newReg);
            return Optional.of(toDto(savedReg));
            
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean unregisterMemberFromEvent(long memberId, long eventId) {
        return registrationRepository.deleteByMemberIdAndEventId(memberId, eventId) > 0;
    }

    @Override
    public Optional<RegistrationDTO> updateRegistrationStatus(long id, String status) {
        return registrationRepository.updateStatus(id, status).map(this::toDto);
    }

    @Override
    public void deleteRegistration(long id) {
        registrationRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> getRegistrationStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRegistrations", registrationRepository.count());
        return stats;
    }
}