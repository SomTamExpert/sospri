package ch.bbw.km.winthi.member;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class MemberToUserDetailsMapper {
    static public UserDetails toUserDetails(Member member) {
        User user = null;
        if (member != null) {
            Collection<MemberGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new MemberGrantedAuthority(member.getAuthority()));
            user = new User(member.getUsername(), member.getPassword(), true, true, true, true, authorities);
        }
        return user;
    }
}
