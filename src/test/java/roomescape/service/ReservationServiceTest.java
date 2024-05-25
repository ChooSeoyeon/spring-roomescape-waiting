package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.member.Member;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.theme.Theme;
import roomescape.exception.reservation.DuplicatedReservationException;
import roomescape.exception.reservation.InvalidDateTimeReservationException;
import roomescape.exception.reservation.NotFoundReservationException;
import roomescape.service.reservation.ReservationService;
import roomescape.service.reservation.dto.ReservationListResponse;
import roomescape.service.reservation.dto.ReservationMineListResponse;
import roomescape.service.reservation.dto.ReservationRequest;
import roomescape.service.reservation.dto.ReservationResponse;

class ReservationServiceTest extends ServiceTest {
    @Autowired
    private ReservationService reservationService;

    @Nested
    @DisplayName("예약 목록 조회")
    class FindAllReservation {
        Theme firstTheme;
        Member user;

        @BeforeEach
        void setUp() {
            ReservationTime time = timeFixture.createFutureTime();
            firstTheme = themeFixture.createFirstTheme();
            user = memberFixture.createUserMember();
            Theme secondTheme = themeFixture.createSecondTheme();
            Member admin = memberFixture.createAdminMember();
            reservationFixture.createPastReservation(time, firstTheme, user);
            reservationFixture.createFutureReservation(time, firstTheme, admin);
            reservationFixture.createPastReservation(time, secondTheme, user);
            reservationFixture.createFutureReservation(time, secondTheme, admin);
        }

        @Test
        void 필터링_없이_전체_예약_목록을_조회할_수_있다() {
            ReservationListResponse response = reservationService.findAllReservation(
                    null, null, null, null);

            assertThat(response.getReservations().size())
                    .isEqualTo(4);
        }

        @Test
        void 예약_목록을_예약자별로_필터링해_조회할_수_있다() {
            ReservationListResponse response = reservationService.findAllReservation(
                    user.getId(), null, null, null);

            assertThat(response.getReservations().size())
                    .isEqualTo(2);
        }

        @Test
        void 예약_목록을_테마별로_필터링해_조회할_수_있다() {
            ReservationListResponse response = reservationService.findAllReservation(
                    null, firstTheme.getId(), null, null);

            assertThat(response.getReservations().size())
                    .isEqualTo(2);
        }

        @Test
        void 예약_목록을_기간별로_필터링해_조회할_수_있다() {
            LocalDate dateFrom = LocalDate.of(2000, 4, 1);
            LocalDate dateTo = LocalDate.of(2000, 4, 7);
            ReservationListResponse response = reservationService.findAllReservation(
                    null, null, dateFrom, dateTo);

            assertThat(response.getReservations().size())
                    .isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("내 예약 목록 조회")
    class FindMyReservation {
        @Test
        void 내_예약_목록을_조회할_수_있다() {
            ReservationTime time = timeFixture.createFutureTime();
            Theme theme = themeFixture.createFirstTheme();
            Member member = memberFixture.createUserMember();
            reservationFixture.createPastReservation(time, theme, member);
            reservationFixture.createFutureReservation(time, theme, member);

            ReservationMineListResponse response = reservationService.findMyReservation(member);

            assertThat(response.getReservations().size())
                    .isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("예약 추가")
    class SaveReservation {
        ReservationTime time;
        Theme theme;
        Member member;
        String timeId;
        String themeId;

        @BeforeEach
        void setUp() {
            time = timeFixture.createFutureTime();
            theme = themeFixture.createFirstTheme();
            member = memberFixture.createUserMember();
            timeId = time.getId().toString();
            themeId = theme.getId().toString();
        }

        @Test
        void 예약을_추가할_수_있다() {
            ReservationRequest request = new ReservationRequest("2000-04-07", timeId, themeId);

            ReservationResponse response = reservationService.saveReservation(request, member);

            assertThat(response.getName())
                    .isEqualTo(member.getName().getName());
        }

        @Test
        void 시간대와_테마가_똑같은_중복된_예약_추가시_예외가_발생한다() {
            Reservation reservation = reservationFixture.createFutureReservation(time, theme, member);
            ReservationRequest request = new ReservationRequest(reservation.getDate().toString(), timeId, themeId);

            assertThatThrownBy(() -> reservationService.saveReservation(request, member))
                    .isInstanceOf(DuplicatedReservationException.class);
        }

        @Test
        void 지나간_날짜와_시간에_대한_예약_추가시_예외가_발생한다() {
            ReservationRequest request = new ReservationRequest("2000-04-06", timeId, themeId);

            assertThatThrownBy(() -> reservationService.saveReservation(request, member))
                    .isInstanceOf(InvalidDateTimeReservationException.class);
        }
    }

    @Nested
    @DisplayName("예약 삭제")
    class DeleteReservation {
        Reservation reservation;

        @BeforeEach
        void setUp() {
            ReservationTime time = timeFixture.createFutureTime();
            Theme theme = themeFixture.createFirstTheme();
            Member member = memberFixture.createUserMember();
            reservation = reservationFixture.createFutureReservation(time, theme, member);
        }

        @Test
        void 예약을_삭제할_수_있다() {
            reservationService.deleteReservation(reservation.getId());

            List<Reservation> reservations = reservationFixture.findAllReservation();
            assertThat(reservations.size())
                    .isEqualTo(0);
        }

        @Test
        void 존재하지_않는_예약은_삭제할_수_없다() {
            assertThatThrownBy(() -> reservationService.deleteReservation(10L))
                    .isInstanceOf(NotFoundReservationException.class);
        }
    }
}
