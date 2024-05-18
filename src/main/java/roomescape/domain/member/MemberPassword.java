package roomescape.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import roomescape.exception.member.InvalidMemberPasswordLengthException;
import roomescape.exception.member.NullMemberPasswordException;

@Embeddable
public class MemberPassword {
    private static final int MIN_LENGTH = 10;
    private static final int MAX_LENGTH = 16;

    @Column(nullable = false)
    private String password;

    protected MemberPassword() {
    }

    public MemberPassword(String password) {
        validateNonNull(password);
        validateLength(password);
        this.password = password;
    }

    private void validateNonNull(String password) {
        if (password == null) {
            throw new NullMemberPasswordException();
        }
    }

    private void validateLength(String password) {
        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            throw new InvalidMemberPasswordLengthException();
        }
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberPassword that = (MemberPassword) o;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
