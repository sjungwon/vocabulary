package domhwangcha.voca.service.dto.vocabulary.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProblemAnswerDto {
    private final Long problemId;
    private final Long vocabularyId;
}
