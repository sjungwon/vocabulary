package domhwangcha.voca.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Passage {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="problem_id")
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="vocabulary_id")
    private Vocabulary vocabulary;

    @Enumerated(value=EnumType.STRING)
    private PassageStatus status;

    public Passage(Problem problem, Vocabulary vocabulary) {
        this.problem = problem;
        problem.getPassages().add(this);
        this.vocabulary = vocabulary;
        status = PassageStatus.NONE;
    }

    public void select(){
        this.status = PassageStatus.SELECT;
    }
}
