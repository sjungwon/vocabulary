package domhwangcha.voca.repository;

import domhwangcha.voca.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByAccount(String account);
}
