package roomescape.domain.reservationwaiting;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import roomescape.domain.member.Member;
import roomescape.domain.reservation.Reservation;

public interface ReservationWaitingRepository extends JpaRepository<ReservationWaiting, Long> {
    boolean existsByReservationAndMember(Reservation reservation, Member member);

    @Query("""
            SELECT new roomescape.domain.reservationwaiting.ReservationWaitingWithRank(
                w, COUNT(*))
            FROM ReservationWaiting w
            LEFT JOIN ReservationWaiting w2
                ON w2.id <= w.id
                AND w2.reservation.id = w.reservation.id
            WHERE w.member.id = :memberId
            GROUP BY w
            """)
    List<ReservationWaitingWithRank> findAllWaitingWithRankByMemberId(Long memberId);
}
