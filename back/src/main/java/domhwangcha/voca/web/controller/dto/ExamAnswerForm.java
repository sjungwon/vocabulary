package domhwangcha.voca.web.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExamAnswerForm {
    @NotNull
    private Long examId;

    @Size(min=10,max=10)
    private List<ProblemAnswerForm> answers;

}
