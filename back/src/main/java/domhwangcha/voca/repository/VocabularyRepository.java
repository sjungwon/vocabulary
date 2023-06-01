package domhwangcha.voca.repository;


import domhwangcha.voca.domain.Vocabulary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {

//    @Query("select v from Vocabulary v WHERE v.id NOT IN (SELECT t.vocabulary.id FROM TestResult t WHERE t.resultStatus = 'CORRECT' AND t.member.id = :memberId)")
    @Query("select v from Vocabulary v " +
            "WHERE v.id NOT IN " +
            "(SELECT p.answer.id FROM Problem p JOIN p.exam e WHERE p.status = 'CORRECT' AND e.member.id = :memberId)")
    List<Vocabulary> findAllMemberVocabulary(@Param("memberId") Long memberId, Pageable pageable);

    List<Vocabulary> findByIdNotIn(Collection<Long> ids, Pageable pageable);
    @Query("SELECT distinct v " +
            "FROM Problem p " +
            "JOIN p.answer v " +
            "WHERE p.status = 'WRONG' " +
            "AND p.member.id = :memberId " +
            "AND v.id NOT IN (SELECT v2.id FROM Problem p2 JOIN p2.answer v2 WHERE p2.status = 'CORRECT' AND p2.member.id = :memberId)")
    List<Vocabulary> findWrongVocabularyByMemberId(@Param("memberId") Long memberId, Pageable pageable);
}

//
//,