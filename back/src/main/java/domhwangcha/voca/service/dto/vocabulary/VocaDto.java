package domhwangcha.voca.service.dto.vocabulary;


import domhwangcha.voca.domain.Vocabulary;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VocaDto {

    private final String english;
    private final String korean;

    @Builder
    public VocaDto(String english, String korean) {
        this.english = english;
        this.korean = korean;
    }

    public VocaDto(Vocabulary vocabulary) {
        this.english = vocabulary.getEnglish();
        this.korean = vocabulary.getKorean();
    }
}
