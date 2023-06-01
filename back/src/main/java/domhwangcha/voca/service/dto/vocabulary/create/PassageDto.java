package domhwangcha.voca.service.dto.vocabulary.create;

import domhwangcha.voca.domain.Passage;
import domhwangcha.voca.domain.Vocabulary;
import lombok.Getter;

@Getter
public class PassageDto {
    private final Long vocabularyId;
    private final String korean;

    public PassageDto(Passage passage) {
        this.vocabularyId = passage.getVocabulary().getId();
        this.korean = passage.getVocabulary().getKorean();
    }

    public PassageDto(Vocabulary vocabulary){
        this.vocabularyId = vocabulary.getId();
        this.korean = vocabulary.getKorean();
    }
}
