package ch.bbw.pr.sospri.member;

import ch.bbw.pr.sospri.security.EncryptionService;
import lombok.extern.slf4j.Slf4j;
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
 * @author Marco Karpf
 * @version 13.04.2023
 */
@Slf4j
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
        try {
            Member member = null;
            log.info("creating new member {}", registerMember.getPrename() + " " + registerMember.getLastname());
            member = new Member();
            member.setPrename(registerMember.getPrename());
            member.setLastname(registerMember.getLastname());
            member.setPassword(encryptionService.bCryptPasswordEncoder(registerMember.getPassword()));
            member.setAuthority("member");
            member.setUsername(registerMember.getPrename().toLowerCase() + "." + registerMember.getLastname().toLowerCase());
            member.setChallenge(registerMember.getChallenge());
            repository.save(member);
        } catch (Exception e) {
            log.error("Error while creating new member: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void update(Long id, Member member) {
        log.warn("updating member {}", member.getPrename() + " " + member.getLastname());
        repository.save(member);
    }

    public void deleteById(Long id) {
        Member memberToDelete = repository.findById(id).get();
        log.warn("deleting member {}", memberToDelete.getPrename() + " " + memberToDelete.getLastname());
        repository.delete(memberToDelete);
    }

    public Member getById(Long id) {
        log.info("getting member by id {}", id);
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            log.warn("member with id {} not found", id);
            return null;
        }
    }

    public Member getUserByUsername(String username) throws UsernameNotFoundException {
        log.info("getting member by username {}", username);
        Member member = repository.findByUsername(username);
        if (member == null) {
            log.warn("member with username {} not found", username);
            throw new UsernameNotFoundException("member with username " + username + " not found");
        }
        return member;
    }

    public boolean isChallengeAnswerCorrect(String username, String challengeAnswer) {
        log.info("checking challenge answer for member {}", username);
        Member member = getUserByUsername(username);
        if (member == null || challengeAnswer == null) {
            log.error("member or challenge answer is null");
            return false;
        }
        return member.getChallenge().equals(challengeAnswer);
    }

    public void resetPassword(String username, String newPassword) {
        Member member = getUserByUsername(username);
        log.info("resetting password for member {}", username);
        if (member != null) {
            member.setPassword(encryptionService.bCryptPasswordEncoder(newPassword));
            repository.save(member);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            log.info("loading user by username {}", username);
            return MemberToUserDetailsMapper.toUserDetails(getUserByUsername(username));
        } catch (Exception e) {
            log.warn("user with username {} not found", username);
            throw new UsernameNotFoundException("user with username " + username + " not found");
        }
    }
}
