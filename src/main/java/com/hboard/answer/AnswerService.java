package com.hboard.answer;

import com.hboard.question.Question;
import com.hboard.question.QuestionDto;
import com.hboard.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(AnswerDto answerDto, QuestionDto questionDto, SiteUser author) {
        Question question = QuestionDto.toEntity(questionDto);
        answerDto.setQuestion(question);
        answerDto.setCreateDate(LocalDateTime.now());
        answerDto.setAuthor(author);

        Answer answer = AnswerDto.toEntity(answerDto);

        this.answerRepository.save(answer);
    }

    public List<AnswerDto> getAnswerDtoList(Long id) {
        List<Answer> answerList = this.answerRepository.findByQuestionId(id);

        return answerList.stream()
                .map((answer -> AnswerDto.toDto(answer)))
                .toList();
    }

    public AnswerDto getAnswer(Long id) {
        Answer answer = this.answerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("answer not found"));
        return AnswerDto.toDto(answer);
    }

    public void modify(AnswerDto answerDto, String content) {
        answerDto.setContent(content);
        Answer answer = AnswerDto.toEntity(answerDto);
        this.answerRepository.save(answer);
    }

    public void delete(AnswerDto answerDto) {
        Answer answer = AnswerDto.toEntity(answerDto);
        this.answerRepository.delete(answer);
    }

    public void vote(AnswerDto answerDto, SiteUser siteUser) {
        answerDto.getVoter().add(siteUser);
        Answer answer = AnswerDto.toEntity(answerDto);
        this.answerRepository.save(answer);
    }

    public void unvote(AnswerDto answerDto, SiteUser siteUser) {
        answerDto.getVoter().remove(siteUser);
        Answer answer = AnswerDto.toEntity(answerDto);
        this.answerRepository.save(answer);
    }
}
