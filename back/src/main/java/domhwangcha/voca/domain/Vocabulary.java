package domhwangcha.voca.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Vocabulary {
    @Id
    @GeneratedValue
    private Long id;
    private String korean;
    private String english;

    @Builder
    public Vocabulary(String korean, String english) {
        this.korean = korean;
        this.english = english;
    }
}
