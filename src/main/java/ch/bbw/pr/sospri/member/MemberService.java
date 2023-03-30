package ch.bbw.pr.sospri.member;

import ch.bbw.pr.sospri.security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * MemberService
 *
 * @author Peter Rutschmann
 * @version 15.03.2023
 */
@Service
@Transactional
public class MemberService implements UserDetailsService {
    @Autowired
    private MemberRepository repository;

    @Autowired
    EncryptionService encryptionService;

    public Iterable<Member> getAll() {
        return repository.findAll();
    }

    public void add(RegisterMember registerMember) {
        Member member = new Member();
        member.setPrename(registerMember.getPrename());
        member.setLastname(registerMember.getLastname() );
        member.setPassword(encryptionService.bCryptPasswordEncoder(registerMember.getPassword()));
        member.setAuthority("member");
        member.setUsername(registerMember.getPrename().toLowerCase() + "." + registerMember.getLastname().toLowerCase());
        member.setChallenge(registerMember.getChallenge());
        repository.save(member);
    }

    public void update(Long id, Member member) {
        repository.save(member);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Member getById(Long id) {
        Optional<Member> member = repository.findById(id);
        return member.orElse(null);
    }

    public Member getUserByUsername(String username) {
        Optional<Member> member = repository.findByUsername(username);
        return member.orElse(null);
    }

    public boolean isChallengeAnswerCorrect(String username, String challengeAnswer) {
        Member member = getUserByUsername(username);
        if (member == null || challengeAnswer == null)
            return false;
        return member.getChallenge().equals(challengeAnswer);
    }

    public void resetPassword(String username, String newPassword) {
        Member member = getUserByUsername(username);
        if (member != null) {
            member.setPassword(encryptionService.bCryptPasswordEncoder(newPassword));
            repository.save(member);
        }
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return MemberToUserDetailsMapper.toUserDetails( getUserByUsername(username));
    }

}
