package domhwangcha.voca.web.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import domhwangcha.voca.domain.Vocabulary;
import domhwangcha.voca.repository.VocabularyRepository;
import lombok.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final ObjectMapper objectMapper;
    private final VocabularyRepository vocaRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    void init() throws IOException {
        File file = new File("src/main/resources/voca.json");
        if(file.exists()){
            VocaData[] vocaData = objectMapper.readValue(file, VocaData[].class);
            for (VocaData vocaDatum : vocaData) {
                Vocabulary build = Vocabulary.builder()
                        .korean(vocaDatum.korean)
                        .english(vocaDatum.english)
                        .build();
                vocaRepository.save(build);
            }
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    private static class VocaData {
        private String english;
        private String korean;
    }
}
