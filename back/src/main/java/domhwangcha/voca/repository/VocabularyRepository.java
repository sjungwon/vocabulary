package domhwangcha.voca.repository;


import domhwangcha.voca.domain.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
}
