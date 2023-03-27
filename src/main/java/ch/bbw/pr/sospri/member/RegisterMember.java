package ch.bbw.pr.sospri.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * To regist a new Member
 *
 * @author Peter Rutschmann
 * @version 15.03.2023
 */
@Getter
@Setter
@ToString
public class RegisterMember {
   @NotNull(message = "prename may not be empty")
   @Size(min = 2, max = 25, message = "the firstname hast to be between 2 and 25 characters long.")
   private String prename;

   @NotNull(message = "lastname may not be empty")
   @Size(min = 2, max = 25, message = "the lastname hast to be between 2 and 25 characters long.")
   private String lastname;

   @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "the password hast to be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number and one special character.")
   private String password;
   private String confirmation;
}
