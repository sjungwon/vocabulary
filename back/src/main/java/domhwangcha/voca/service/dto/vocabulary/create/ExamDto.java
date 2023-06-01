package domhwangcha.voca.service.dto.vocabulary.create;

import domhwangcha.voca.domain.Exam;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ExamDto {
    private final Long id;
    private final List<ProblemDto> problems;

    public ExamDto(Exam exam) {
        id = exam.getId();
        problems = exam.getProblems().stream().map(ProblemDto::new).collect(Collectors.toList());
    }
}
