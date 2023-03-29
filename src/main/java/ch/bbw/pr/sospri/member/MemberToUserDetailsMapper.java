package ch.bbw.pr.sospri.member;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class MemberToUserDetailsMapper {
    static public UserDetails toUserDetails(Member member) {
        User user = null;
        if (member != null) {
            System.out.println("MemberToUserDetailsMapper: " + member.getUsername());
            Collection<MemberGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new MemberGrantedAuthority(member.getAuthority()));
            System.out.println("MemberToUserDetailsMapper.toUserDetails(): authorities: " + Arrays.toString(authorities.toArray()));
            user = new User(member.getUsername(), member.getPassword(), true, true, true, true, authorities);
        }
        return user;
    }
}
