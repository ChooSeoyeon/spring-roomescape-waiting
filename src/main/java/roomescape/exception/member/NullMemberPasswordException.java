package roomescape.exception.member;

import org.springframework.http.HttpStatus;
import roomescape.exception.RoomescapeException;

public class NullMemberPasswordException extends RoomescapeException {
    public NullMemberPasswordException() {
        super("비밀번호가 비어있습니다.", HttpStatus.BAD_REQUEST);
    }
}
