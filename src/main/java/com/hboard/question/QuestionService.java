package com.hboard.question;

import lombok.RequiredArgsConstructor;
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

    public Page<QuestionDto> getList(int page, String kw) {
        Sort.Order sort = Sort.Order.desc("createDate");
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sort));
        return this.questionRepository.findAllByKeyword(kw, pageable)
                .map(question -> QuestionDto.toDto(question));
    }

    public QuestionDto getQuestionDto(Long id) {
        Question question = this.questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("question not found"));

        return QuestionDto.toDto(question);
    }

    public void create(QuestionDto questionDto) {
        questionDto.setCreateDate(LocalDateTime.now());

        Question question = QuestionDto.toEntity(questionDto);

        this.questionRepository.save(question);
    }
}
