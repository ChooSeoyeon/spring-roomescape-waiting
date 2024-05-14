package roomescape.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.Optional;

public interface ReservationTimeRepository extends JpaRepository<ReservationTime, Long> {
    Optional<ReservationTime> findById(long id);

    boolean existsByStartAt(LocalTime startAt);
}
