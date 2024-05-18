package roomescape.service.login.dto;

import roomescape.domain.member.Member;
import roomescape.domain.member.MemberEmail;
import roomescape.domain.member.MemberName;
import roomescape.domain.member.MemberPassword;
import roomescape.domain.member.MemberRole;

public class SignupRequest {
    private final String email;
    private final String password;
    private final String name;

    public SignupRequest(String email, String password, String name) {
        validate(email, password, name);
        this.email = email;
        this.password = password;
        this.name = name;
    }

    private void validate(String email, String password, String name) {
        if (email.isBlank() || password.isBlank() || name.isBlank()) {
            throw new IllegalArgumentException();
        }
    }

    public Member toMember(MemberRole role) {
        return new Member(
                new MemberName(name),
                new MemberEmail(email),
                new MemberPassword(password),
                role
        );
    }

    public MemberEmail toMemberEmail() {
        return new MemberEmail(email);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}