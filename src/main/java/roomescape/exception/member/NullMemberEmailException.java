package roomescape.exception.member;

import org.springframework.http.HttpStatus;
import roomescape.exception.RoomescapeException;

public class NullMemberEmailException extends RoomescapeException {
    public NullMemberEmailException() {
        super("이메일이 비어있습니다.", HttpStatus.BAD_REQUEST);
    }
}
