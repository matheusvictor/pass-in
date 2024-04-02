package br.com.rocketseat.passin.repository;

import br.com.rocketseat.passin.domain.attendees.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, String> {
}
