package com.clubconnect.registrationservice.repository;

import com.clubconnect.registrationservice.model.Registration;
import java.util.List;

public interface RegistrationRepository {

    List<Registration> findAll();
    Registration findById(long id);
    List<Registration> findByMemberId(long memberId);
    List<Registration> findByEventId(long eventId);
    Integer GetRegistrationCount();
    Registration save(Registration reg);
    boolean deleteById(long id);
    boolean deleteByMemberIdAndEventId(long memberId, long eventId);
    Registration updateStatus(long id, String status);
    long count();
}