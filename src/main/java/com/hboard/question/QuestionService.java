package com.hboard.question;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    public Page<QuestionDto> getList(int page) {
        Sort.Order sort = Sort.Order.desc("createDate");
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sort));
        return this.questionRepository.findAll(pageable)
                .map(question -> modelMapper.map(question, QuestionDto.class));
    }

    public QuestionDto getQuestionDto(Long id) {
        Question question = this.questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("question not found"));

        return modelMapper.map(question, QuestionDto.class);
    }

    public void create(QuestionDto questionDto) {
        questionDto.setCreateDate(LocalDateTime.now());

        Question question = modelMapper.map(questionDto, Question.class);

        this.questionRepository.save(question);
    }
}
