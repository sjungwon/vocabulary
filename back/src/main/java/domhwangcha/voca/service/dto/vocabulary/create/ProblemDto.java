package domhwangcha.voca.service.dto.vocabulary.create;

import domhwangcha.voca.domain.Problem;
import domhwangcha.voca.domain.Vocabulary;
import domhwangcha.voca.lib.RandomIndexGenerator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProblemDto {
    private final Long id;
    private final String english;
    private final List<PassageDto> passages;

    public ProblemDto(Problem problem) {
        id = problem.getId();
        english = problem.getAnswer().getEnglish();
        passages = new ArrayList<>(4);

        List<PassageDto> collect = problem.getPassages().stream().map(PassageDto::new).collect(Collectors.toList());
        collect.add(new PassageDto(problem.getAnswer()));

        int[] randomIndex = new RandomIndexGenerator().getRandomIndex(4);
        for(int i = 0; i<4;i++){
            passages.add(collect.get(randomIndex[i]));
        }
    }
}
