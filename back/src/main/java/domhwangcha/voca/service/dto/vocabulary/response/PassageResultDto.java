package domhwangcha.voca.service.dto.vocabulary.response;

import domhwangcha.voca.domain.Passage;
import domhwangcha.voca.domain.PassageStatus;
import lombok.Getter;

@Getter
public class PassageResultDto {
    private final String korean;
    private final PassageStatus status;

    public PassageResultDto(Passage passage) {
        korean = passage.getVocabulary().getKorean();
        status = passage.getStatus();
    }
}
