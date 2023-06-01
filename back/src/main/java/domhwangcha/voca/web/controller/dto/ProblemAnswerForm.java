package domhwangcha.voca.web.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ProblemAnswerForm {
    @NotNull
    private Long problemId;

    @NotNull
    private Long vocabularyId;
}
