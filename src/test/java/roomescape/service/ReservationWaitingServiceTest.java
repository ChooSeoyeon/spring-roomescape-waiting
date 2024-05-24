package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.member.Member;
import roomescape.domain.member.MemberEmail;
import roomescape.domain.member.MemberName;
import roomescape.domain.member.MemberPassword;
import roomescape.domain.member.MemberRole;
import roomescape.domain.reservationwaiting.ReservationWaiting;
import roomescape.domain.reservationwaiting.ReservationWaitingRepository;
import roomescape.exception.reservationwaiting.CannotDeleteOtherMemberWaiting;
import roomescape.exception.reservationwaiting.DuplicatedReservationWaitingException;
import roomescape.exception.reservationwaiting.NotFoundReservationWaitingException;
import roomescape.service.member.MemberService;
import roomescape.service.reservation.ReservationService;
import roomescape.service.reservationwaiting.ReservationWaitingService;
import roomescape.service.reservationwaiting.dto.ReservationWaitingRequest;

public class ReservationWaitingServiceTest extends ServiceTest {
    @Autowired
    private ReservationWaitingService reservationWaitingService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationWaitingRepository reservationWaitingRepository;

    @Nested
    @DisplayName("예약 대기 추가")
    class SaveReservationWaiting {
        @Test
        void 예약_대기를_추가할_수_있다() {
            ReservationWaitingRequest request = new ReservationWaitingRequest(1L);
            Member member = memberService.findById(2L);

            Long waitingId = reservationWaitingService.saveReservationWaiting(request, member);

            assertThat(waitingId)
                    .isEqualTo(2L);
        }

        @Test
        void 같은_사용자가_같은_예약에_대해선_예약_대기를_두_번_이상_추가_시_예외가_발생한다() {
            ReservationWaitingRequest request = new ReservationWaitingRequest(1L);
            Member member = memberService.findById(1L);

            assertThatThrownBy(() -> reservationWaitingService.saveReservationWaiting(request, member))
                    .isInstanceOf(DuplicatedReservationWaitingException.class);
        }
    }

    @Nested
    @DisplayName("예약 대기 삭제")
    class DeleteReservation {
        @Test
        void 예약_대기를_삭제할_수_있다() {
            Member member = new Member(
                    1L,
                    new MemberName("사용자"),
                    new MemberEmail("user@gmail.com"),
                    new MemberPassword("1234567890"),
                    MemberRole.USER
            );
            reservationWaitingService.deleteReservationWaiting(1L, member);

            List<ReservationWaiting> reservationWaitings = reservationWaitingRepository.findAll();
            assertThat(reservationWaitings.size())
                    .isEqualTo(0);
        }

        @Test
        void 존재하지_않는_예약_대기_삭제_시_예외가_발생한다() {
            Member member = new Member(
                    1L,
                    new MemberName("사용자"),
                    new MemberEmail("user@gmail.com"),
                    new MemberPassword("1234567890"),
                    MemberRole.USER
            );
            assertThatThrownBy(() -> reservationWaitingService.deleteReservationWaiting(10L, member))
                    .isInstanceOf(NotFoundReservationWaitingException.class);
        }

        @Test
        void 다른_사용자의_예약_대기_삭제_시_예외가_발생한다() {
            Member member = new Member(
                    2L,
                    new MemberName("관리자"),
                    new MemberEmail("admin@gmail.com"),
                    new MemberPassword("1234567890"),
                    MemberRole.ADMIN
            );
            assertThatThrownBy(() -> reservationWaitingService.deleteReservationWaiting(1L, member))
                    .isInstanceOf(CannotDeleteOtherMemberWaiting.class);
        }
    }
}