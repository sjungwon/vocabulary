package domhwangcha.voca.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="answer_id")
    private Vocabulary answer;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "problem")
    private List<Passage> passages = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private ProblemStatus status;

    public Problem(Exam exam, Vocabulary answer) {
        this.exam = exam;
        this.member = exam.getMember();
        exam.getProblems().add(this);
        this.answer = answer;
        this.status = ProblemStatus.CREATED;
    }

    public void correctAnswer(Long answerId){
        if(answer.getId().equals(answerId)){
            status = ProblemStatus.CORRECT;
            return;
        }
        Passage passage = passages.stream().filter(p -> p.getVocabulary().getId().equals(answerId)).findAny().orElseThrow(IllegalStateException::new);
        passage.select();
        status = ProblemStatus.WRONG;
    }
}
