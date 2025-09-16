package com.example.registrationservice.repository;

import java.util.List;
import java.util.Optional;

import com.example.registrationservice.model.Registration;

public interface RegistrationRepository {
    List<Registration> findAll();
    Optional<Registration> findById(long id);
    List<Registration> findByMemberId(long memberId);
    List<Registration> findByEventId(long eventId);
    Registration save(Registration registration);
    void deleteById(long id);
    int deleteByMemberIdAndEventId(long memberId, long eventId);
    Optional<Registration> updateStatus(long id, String status);
    long count();
}