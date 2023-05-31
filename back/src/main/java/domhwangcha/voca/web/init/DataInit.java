package domhwangcha.voca.web.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import domhwangcha.voca.domain.Member;
import domhwangcha.voca.domain.Role;
import domhwangcha.voca.domain.Vocabulary;
import domhwangcha.voca.repository.MemberRepository;
import domhwangcha.voca.repository.VocabularyRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInit {

    private final ObjectMapper objectMapper;
    private final VocabularyRepository vocaRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    void init() throws IOException {
        File file = new File("back/src/main/resources/voca.json");
        if(file.exists()){
            VocaData[] vocaData = objectMapper.readValue(file, VocaData[].class);
            Set<VocaData> collect = Arrays.stream(vocaData).collect(Collectors.toSet());
            for (VocaData data : collect) {
                Vocabulary build = Vocabulary.builder()
                        .korean(data.korean)
                        .english(data.english)
                        .build();
                System.out.println(build);
                vocaRepository.save(build);
            }
        }

        Member build = Member.builder()
                .role(Role.USER)
                .account("test")
                .hashedPassword(passwordEncoder.encode("qwer1234"))
                .build();
        memberRepository.save(build);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    private static class VocaData {
        private String english;
        private String korean;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VocaData vocaData = (VocaData) o;
            return Objects.equals(english, vocaData.english);
        }

        @Override
        public int hashCode() {
            return Objects.hash(english);
        }
    }
}
