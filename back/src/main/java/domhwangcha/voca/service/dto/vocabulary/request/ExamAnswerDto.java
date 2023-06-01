package domhwangcha.voca.service.dto.vocabulary.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ExamAnswerDto {
    private final Long memberId;
    private final Long examId;
    private final List<ProblemAnswerDto> answers;
}
