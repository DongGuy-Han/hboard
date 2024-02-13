package com.hboard.answer;

import com.hboard.question.Question;
import com.hboard.question.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;

    public void create(AnswerDto answerDto, QuestionDto questionDto) {
        Question question = modelMapper.map(questionDto, Question.class);
        answerDto.setQuestion(question);
        answerDto.setCreateDate(LocalDateTime.now());

        Answer answer = modelMapper.map(answerDto, Answer.class);

        this.answerRepository.save(answer);
    }

    public List<AnswerDto> getAnswerDtoList(Long id) {
        List<Answer> answerList = this.answerRepository.findByQuestionId(id);

        return answerList.stream()
                .map((answer -> modelMapper.map(answer, AnswerDto.class)))
                .toList();
    }
}
