package domhwangcha.voca.web.controller;

import domhwangcha.voca.exception.FormValidateException;
import domhwangcha.voca.service.VocabularyService;
import domhwangcha.voca.service.dto.vocabulary.request.ExamAnswerDto;
import domhwangcha.voca.service.dto.vocabulary.create.ExamDto;
import domhwangcha.voca.service.dto.vocabulary.request.ProblemAnswerDto;
import domhwangcha.voca.service.dto.vocabulary.VocaDto;
import domhwangcha.voca.service.dto.vocabulary.response.ExamResultDto;
import domhwangcha.voca.web.auth.Authorization;
import domhwangcha.voca.web.auth.LoginMember;
import domhwangcha.voca.web.auth.UserSession;
import domhwangcha.voca.web.controller.dto.ExamAnswerForm;
import domhwangcha.voca.web.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("wrong")
    public ApiResponse<List<VocaDto>> getWrongVocabulary(@LoginMember UserSession userSession){
        List<VocaDto> wrongVoca = this.vocabularyService.getWrongVoca(userSession.getId());
        return new ApiResponse<>(wrongVoca);
    }

    @GetMapping("test")
    public ApiResponse<ExamDto> getTest(@LoginMember UserSession userSession){
        ExamDto test = this.vocabularyService.getTest(userSession.getId());
        return new ApiResponse<>(test);
    }

    @PostMapping("test")
    public ApiResponse<ExamResultDto> postAnswer(@LoginMember UserSession userSession, @RequestBody @Validated ExamAnswerForm examAnswerForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new FormValidateException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        List<ProblemAnswerDto> collect = examAnswerForm.getAnswers().stream().map(a -> new ProblemAnswerDto(a.getProblemId(), a.getVocabularyId())).collect(Collectors.toList());

        ExamAnswerDto examAnswerDto = new ExamAnswerDto(userSession.getId(), examAnswerForm.getExamId(), collect);
        ExamResultDto examResultDto = this.vocabularyService.markingExam(examAnswerDto);
        return new ApiResponse<>(examResultDto);
    }

    @GetMapping("test/prev")
    public ApiResponse<ExamResultDto> getPrevResult(@LoginMember UserSession userSession){
        ExamResultDto prevResult = this.vocabularyService.getPrevResult(userSession.getId());
        return new ApiResponse<>(prevResult);
    }
}
