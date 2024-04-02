package br.com.rocketseat.passin.repository;

import br.com.rocketseat.passin.domain.attendees.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, String> {
    List<Attendee> findAllByEventId(String eventId);
}
