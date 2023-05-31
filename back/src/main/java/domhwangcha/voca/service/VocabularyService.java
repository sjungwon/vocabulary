package domhwangcha.voca.service;

import domhwangcha.voca.domain.Vocabulary;
import domhwangcha.voca.exception.BadRequestException;
import domhwangcha.voca.repository.MemberRepository;
import domhwangcha.voca.repository.VocabularyRepository;
import domhwangcha.voca.service.dto.vocabulary.VocaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VocabularyService {
    private final MemberRepository memberRepository;
    private final VocabularyRepository vocabularyRepository;

    public List<VocaDto> getDailyVocabulary(Long memberId, Integer size){
        if(size!=null && size > 40){
            throw new BadRequestException("단어는 40개까지 요청가능합니다.");
        }
        List<Vocabulary> byMemberId = this.vocabularyRepository.findAllMemberVocabulary(memberId, PageRequest.of(0,size != null ? size : 20));

        return byMemberId.stream().map(VocaDto::new).collect(Collectors.toList());
    }
}
