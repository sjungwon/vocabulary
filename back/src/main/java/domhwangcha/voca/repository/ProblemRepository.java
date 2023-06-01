package domhwangcha.voca.repository;

import domhwangcha.voca.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem,Long> {
}
