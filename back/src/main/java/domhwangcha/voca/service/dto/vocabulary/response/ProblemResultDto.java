package domhwangcha.voca.service.dto.vocabulary.response;

import domhwangcha.voca.domain.Problem;
import domhwangcha.voca.domain.ProblemStatus;
import domhwangcha.voca.domain.Vocabulary;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProblemResultDto {
    private final Long id;
    private final String english;
    private final String korean;
    private final ProblemStatus status;
    private final List<PassageResultDto> passages;

    public ProblemResultDto(Problem problem) {
        id = problem.getId();
        Vocabulary answer = problem.getAnswer();
        english = answer.getEnglish();
        korean = answer.getKorean();
        status = problem.getStatus();
        passages = problem.getPassages().stream()
                .map(PassageResultDto::new).collect(Collectors.toList());
    }
}
