package roomescape.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import roomescape.exception.member.InvalidMemberNameLengthException;
import roomescape.exception.member.NullMemberNameException;

@Embeddable
public class MemberName {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 5;

    @Column(nullable = false)
    private String name;

    protected MemberName() {
    }

    public MemberName(String name) {
        validateNonNull(name);
        validateLength(name);
        this.name = name;
    }

    private void validateNonNull(String name) {
        if (name == null) {
            throw new NullMemberNameException();
        }
    }

    private void validateLength(String name) {
        if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH) {
            throw new InvalidMemberNameLengthException();
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberName that = (MemberName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
