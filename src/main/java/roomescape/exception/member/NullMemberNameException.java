package roomescape.exception.member;

import org.springframework.http.HttpStatus;
import roomescape.exception.RoomescapeException;

public class NullMemberNameException extends RoomescapeException {
    public NullMemberNameException() {
        super("사용자 이름이 비어있습니다.", HttpStatus.BAD_REQUEST);
    }
}
