package domhwangcha.voca.service.dto.vocabulary.response;

import domhwangcha.voca.domain.Exam;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ExamResultDto {
    private final Long id;
    private final List<ProblemResultDto> problems;

    public ExamResultDto(Exam exam) {
        this.id = exam.getId();
        this.problems = exam.getProblems().stream().map(ProblemResultDto::new).collect(Collectors.toList());;
    }
}
