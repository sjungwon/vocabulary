package domhwangcha.voca.domain;

import domhwangcha.voca.service.dto.vocabulary.request.ProblemAnswerDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exam {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exam")
    private List<Problem> problems = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    ExamStatus status;

    public Exam(Member member) {
        this.member = member;
        this.status = ExamStatus.CREATED;
    }

    public void markAnswer(List<ProblemAnswerDto> answerDtos){
        Map<Long, ProblemAnswerDto> collect = answerDtos.stream().collect(Collectors.toMap(ProblemAnswerDto::getProblemId, p -> p));
        for (Problem problem : problems) {
            problem.correctAnswer(collect.get(problem.getId()).getVocabularyId());
        }
        this.status = ExamStatus.DONE;
    }
}
