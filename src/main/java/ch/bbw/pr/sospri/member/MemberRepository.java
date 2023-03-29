package ch.bbw.pr.sospri.member;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * MemberRepository
 * 
 * @author Peter Rutschmann
 * @version 15.03.2023
 */

public interface MemberRepository extends CrudRepository<Member, Long>{

   public Optional<Member> findById(Long id);
   public Optional<Member> findByUsername(String username);
}

