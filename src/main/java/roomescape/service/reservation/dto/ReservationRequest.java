package roomescape.service.reservation.dto;

import java.time.DateTimeException;
import java.time.LocalDate;
import roomescape.controller.reservation.dto.AdminReservationRequest;
import roomescape.domain.member.Member;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationStatus;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.theme.Theme;
import roomescape.exception.reservation.NullDateException;
import roomescape.exception.reservation.NullThemeIdException;
import roomescape.exception.reservation.NullTimeIdException;

public class ReservationRequest {
    private final LocalDate date;
    private final Long timeId;
    private final Long themeId;

    public ReservationRequest(String date, String timeId, String themeId) {
        validate(date, timeId, themeId);
        this.date = LocalDate.parse(date);
        this.timeId = Long.parseLong(timeId);
        this.themeId = Long.parseLong(themeId);
    }

    public ReservationRequest(AdminReservationRequest request) {
        this.date = request.getDate();
        this.timeId = request.getTimeId();
        this.themeId = request.getThemeId();
    }

    private void validate(String date, String timeId, String themeId) {
        validateNull(date, timeId, themeId);
        validateType(date);
    }

    private void validateNull(String date, String timeId, String themeId) {
        if (date == null) {
            throw new NullDateException();
        }
        if (timeId == null) {
            throw new NullTimeIdException();
        }
        if (themeId == null) {
            throw new NullThemeIdException();
        }
    }

    private void validateType(String date) {
        try {
            LocalDate.parse(date);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException();
        }
    }

    public Reservation toReservation(
            ReservationStatus status, ReservationTime reservationTime, Theme theme, Member member) {
        return new Reservation(status, date, reservationTime, theme, member);
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }

    public Long getThemeId() {
        return themeId;
    }
}
