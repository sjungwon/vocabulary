package domhwangcha.voca.repository;

import domhwangcha.voca.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
