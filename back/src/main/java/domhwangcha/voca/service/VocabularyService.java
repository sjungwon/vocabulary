package domhwangcha.voca.service;

import domhwangcha.voca.domain.*;
import domhwangcha.voca.exception.BadRequestException;
import domhwangcha.voca.exception.ForbiddenException;
import domhwangcha.voca.exception.NotFoundException;
import domhwangcha.voca.exception.UnauthorizedException;
import domhwangcha.voca.lib.RandomIndexGenerator;
import domhwangcha.voca.repository.*;
import domhwangcha.voca.service.dto.vocabulary.request.ExamAnswerDto;
import domhwangcha.voca.service.dto.vocabulary.create.ExamDto;
import domhwangcha.voca.service.dto.vocabulary.response.ExamResultDto;
import domhwangcha.voca.service.dto.vocabulary.VocaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VocabularyService {
    private final MemberRepository memberRepository;
    private final VocabularyRepository vocabularyRepository;
    private final ExamRepository examRepository;
    private final ProblemRepository problemRepository;
    private final PassageRepository passageRepository;

    public List<VocaDto> getDailyVocabulary(Long memberId, Integer size){
        if(size!=null && size > 40){
            throw new BadRequestException("단어는 40개까지 요청가능합니다.");
        }
        List<Vocabulary> byMemberId = this.vocabularyRepository.findAllMemberVocabulary(memberId, PageRequest.of(0,size != null ? size : 20));

        return byMemberId.stream().map(VocaDto::new).collect(Collectors.toList());
    }

    @Transactional
    public ExamDto getTest(Long memberId){
        //요청 회원 조회
        Member requestMember = getRequestMember(memberId);

        //진행 중인 테스트 확인
        Optional<Exam> existByMemberId = examRepository.findExistByMemberId(memberId);
        if(existByMemberId.isPresent()){
            return new ExamDto(existByMemberId.get());
        }

        //문제
        List<Vocabulary> forProblem = this.vocabularyRepository.findAllMemberVocabulary(memberId, PageRequest.of(0, 10));

        List<Long> collect = forProblem.stream().map(Vocabulary::getId).collect(Collectors.toList());

        //지문
        int passageSize = collect.size() * 3;

        List<Vocabulary> forPassage = this.vocabularyRepository.findByIdNotIn(collect, PageRequest.of(0, passageSize));

        int[] randomIndex = new RandomIndexGenerator().getRandomIndex(passageSize);

        Exam exam = new Exam(requestMember);
        examRepository.save(exam);
        for(int i = 0;i < forProblem.size();i++){
            Vocabulary vocabulary = forProblem.get(i);
            Problem problem = new Problem(exam, vocabulary);
            problemRepository.save(problem);
//            Passage answerPassage = new Passage(problem, vocabulary);
//            passageRepository.save(answerPassage);

            int offset = (i) * 3;
            for(int j=offset;j<offset+3;j++){
                Vocabulary passageVocabulary = forPassage.get(randomIndex[j]);
                Passage passage = new Passage(problem, passageVocabulary);
                passageRepository.save(passage);
            }
        }

        return new ExamDto(exam);
    }

    @Transactional
    public ExamResultDto markingExam(ExamAnswerDto examAnswerDto){
        Member requestMember = getRequestMember(examAnswerDto.getMemberId());

        Exam exam = this.examRepository.findFetchById(examAnswerDto.getExamId()).orElseThrow(NotFoundException::new);

        if(!exam.getMember().getId().equals(requestMember.getId())){
            throw new ForbiddenException();
        }

        if(exam.getStatus().equals(ExamStatus.DONE)){
            throw new BadRequestException("이미 채점된 시험입니다.");
        }

        exam.markAnswer(examAnswerDto.getAnswers());

        return new ExamResultDto(exam);
    }

    private Member getRequestMember(Long memberId) {
        return this.memberRepository.findById(memberId).orElseThrow(UnauthorizedException::new);
    }
}
