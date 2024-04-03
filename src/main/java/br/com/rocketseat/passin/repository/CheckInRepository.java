package br.com.rocketseat.passin.repository;

import br.com.rocketseat.passin.domain.checkin.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
    Optional<CheckIn> findByAttendeeId(String attendeeId);
}
