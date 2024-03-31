package com.hboard.question;

import com.hboard.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.scanner.ScannerImpl;

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

    public void create(QuestionDto questionDto, SiteUser author) {
        questionDto.setCreateDate(LocalDateTime.now());
        questionDto.setAuthor(author);

        Question question = QuestionDto.toEntity(questionDto);

        this.questionRepository.save(question);
    }

    public void modify(QuestionDto questionDto, String title, String content) {
        questionDto.setTitle(title);
        questionDto.setContent(content);
        System.out.println(title);
        System.out.println(content);
        Question question = QuestionDto.toEntity(questionDto);

        this.questionRepository.save(question);
    }

    public void delete(QuestionDto questionDto) {
        Question question = QuestionDto.toEntity(questionDto);
        this.questionRepository.delete(question);
    }

    public void vote(QuestionDto questionDto, SiteUser siteUser) {
        questionDto.getVoter().add(siteUser);
        Question question = QuestionDto.toEntity(questionDto);
        this.questionRepository.save(question);
    }

    public void unvote(QuestionDto questionDto, SiteUser siteUser) {
        questionDto.getVoter().remove(siteUser);
        Question question = QuestionDto.toEntity(questionDto);
        this.questionRepository.save(question);
    }
}
