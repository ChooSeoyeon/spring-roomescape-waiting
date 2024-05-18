package roomescape.exception.member;

import org.springframework.http.HttpStatus;
import roomescape.exception.RoomescapeException;

public class NullMemberRoleException extends RoomescapeException {
    public NullMemberRoleException() {
        super("역할이 비어있습니다.", HttpStatus.BAD_REQUEST);
    }
}
