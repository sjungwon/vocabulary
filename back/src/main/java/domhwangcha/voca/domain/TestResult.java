package domhwangcha.voca.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TestResult {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="vocabulary_id")
    private Vocabulary vocabulary;

    @Enumerated(value = EnumType.STRING)
    private ResultStatus resultStatus;

    @Builder
    private TestResult(Member member, Vocabulary vocabulary, ResultStatus resultStatus) {
        this.member = member;
        this.vocabulary = vocabulary;
        this.resultStatus = resultStatus;
    }
}
