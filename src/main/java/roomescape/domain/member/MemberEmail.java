package roomescape.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import roomescape.exception.member.InvalidMemberEmailPatternException;
import roomescape.exception.member.NullMemberEmailException;

@Embeddable
public class MemberEmail {
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    @Column(nullable = false, unique = true)
    private String email;

    protected MemberEmail() {
    }

    public MemberEmail(String email) {
        validateNonNull(email);
        validatePattern(email);
        this.email = email;
    }

    private void validateNonNull(String address) {
        if (address == null) {
            throw new NullMemberEmailException();
        }
    }

    private void validatePattern(String address) {
        Matcher matcher = ADDRESS_PATTERN.matcher(address);
        if (!matcher.matches()) {
            throw new InvalidMemberEmailPatternException();
        }
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberEmail that = (MemberEmail) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
