package roomescape.service.login.dto;

import roomescape.domain.member.MemberEmail;
import roomescape.domain.member.MemberPassword;
import roomescape.exception.member.NullMemberEmailException;
import roomescape.exception.member.NullMemberPasswordException;

public class LoginRequest {
    private final String email;
    private final String password;

    public LoginRequest(String email, String password) {
        validate(email, password);
        this.email = email;
        this.password = password;
    }

    private void validate(String email, String password) {
        if (email.isBlank()) {
            throw new NullMemberEmailException();
        }
        if (password.isBlank()) {
            throw new NullMemberPasswordException();
        }
    }

    public MemberEmail toMemberEmail() {
        return new MemberEmail(email);
    }

    public MemberPassword toMemberPassword() {
        return new MemberPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
