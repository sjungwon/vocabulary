package domhwangcha.voca.web.controller;

import domhwangcha.voca.service.VocabularyService;
import domhwangcha.voca.service.dto.vocabulary.VocaDto;
import domhwangcha.voca.web.auth.Authorization;
import domhwangcha.voca.web.auth.LoginMember;
import domhwangcha.voca.web.auth.UserSession;
import domhwangcha.voca.web.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("vocabulary")
@RequiredArgsConstructor
@Authorization
public class VocabularyController {
    private final VocabularyService vocabularyService;

    @GetMapping
    public ApiResponse<List<VocaDto>> getVocabulary(@LoginMember UserSession userSession, Integer size){
        List<VocaDto> dailyVocabulary = this.vocabularyService.getDailyVocabulary(userSession.getId(), size);
        return new ApiResponse<>(dailyVocabulary);
    }
}
