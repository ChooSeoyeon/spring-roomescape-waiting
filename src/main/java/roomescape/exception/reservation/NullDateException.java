package roomescape.exception.reservation;

import org.springframework.http.HttpStatus;
import roomescape.exception.RoomescapeException;

public class NullDateException extends RoomescapeException {
    public NullDateException() {
        super("날짜가 비어있습니다.", HttpStatus.BAD_REQUEST);
    }
}
