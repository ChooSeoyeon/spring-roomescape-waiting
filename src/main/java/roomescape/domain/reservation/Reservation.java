package roomescape.domain.reservation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import roomescape.domain.member.Member;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.theme.Theme;
import roomescape.exception.member.NullMemberRoleException;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;
    @Column(nullable = false)
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "reservation_time_id", nullable = false)
    private ReservationTime time;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Theme theme;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    public Reservation() {
    }

    public Reservation(
            Long id, ReservationStatus status, LocalDate date, ReservationTime time, Theme theme, Member member) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.time = time;
        this.theme = theme;
        this.member = member;
    }

    public Reservation(ReservationStatus status, LocalDate date, ReservationTime time, Theme theme, Member member) {
        this(null, status, date, time, theme, member);
    }

    private void validateNonNull(
            ReservationStatus status, LocalDate date, ReservationTime time, Theme theme, Member member) {
        if (status == null) {
            throw new NullMemberRoleException();
        }
    }

    public Long getId() {
        return id;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    public Theme getTheme() {
        return theme;
    }

    public Member getMember() {
        return member;
    }
}
