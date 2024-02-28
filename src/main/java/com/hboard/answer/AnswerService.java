package com.hboard.answer;

import com.hboard.question.Question;
import com.hboard.question.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(AnswerDto answerDto, QuestionDto questionDto) {
        Question question = QuestionDto.toEntity(questionDto);
        answerDto.setQuestion(question);
        answerDto.setCreateDate(LocalDateTime.now());

        Answer answer = AnswerDto.toEntity(answerDto);

        this.answerRepository.save(answer);
    }

    public List<AnswerDto> getAnswerDtoList(Long id) {
        List<Answer> answerList = this.answerRepository.findByQuestionId(id);

        return answerList.stream()
                .map((answer -> AnswerDto.toDto(answer)))
                .toList();
    }
}
