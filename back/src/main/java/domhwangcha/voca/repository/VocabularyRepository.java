package domhwangcha.voca.repository;


import domhwangcha.voca.domain.Vocabulary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {

    @Query("select v from Vocabulary v WHERE v.id NOT IN (SELECT t.vocabulary.id FROM TestResult t WHERE t.resultStatus = 'CORRECT' AND t.member.id = :memberId)")
    List<Vocabulary> findAllMemberVocabulary(@Param("memberId") Long memberId, Pageable pageable);
}

//
//,