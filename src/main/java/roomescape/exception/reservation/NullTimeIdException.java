package roomescape.exception.reservation;

import org.springframework.http.HttpStatus;
import roomescape.exception.RoomescapeException;

public class NullTimeIdException extends RoomescapeException {
    public NullTimeIdException() {
        super("시간 id가 비어있습니다.", HttpStatus.BAD_REQUEST);
    }
}
