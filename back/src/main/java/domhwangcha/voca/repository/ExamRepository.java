package domhwangcha.voca.repository;

import domhwangcha.voca.domain.Exam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam,Long> {

    @Query("SELECT e FROM Exam e JOIN FETCH e.problems WHERE e.status = 'CREATED' AND e.member.id = :memberId")
    Optional<Exam> findExistByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT e FROM Exam e JOIN FETCH e.problems WHERE e.id = :id")
    Optional<Exam> findFetchById(@Param("id") Long id);


    @Query("SELECT e FROM Exam e JOIN FETCH e.problems WHERE e.status = 'DONE' AND e.member.id = :memberId")
    List<Exam> findFetchDoneByMemberId(@Param("memberId") Long memberId, Pageable pageable);
}
