package roomescape.exception.reservation;

import org.springframework.http.HttpStatus;
import roomescape.exception.RoomescapeException;

public class NullThemeIdException extends RoomescapeException {
    public NullThemeIdException() {
        super("태마 id가 비어있습니다.", HttpStatus.BAD_REQUEST);
    }
}
