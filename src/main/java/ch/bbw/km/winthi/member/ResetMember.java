package ch.bbw.km.winthi.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
public class ResetMember {


    private String username;

    private String challenge;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[_@$!%*?&])[A-Za-z\\d_@$!%*?&]{8,}$", message = "the password hast to be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number and one special character.")
    private String password;
    private String confirmation;
}

