package com.hboard.answer;

import com.hboard.question.QuestionDto;
import com.hboard.question.QuestionService;
import com.hboard.user.SiteUser;
import com.hboard.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/answers/create/{id}")
    public String createAnswer(Model model, @PathVariable Long id,
                               @Valid AnswerDto answerDto, BindingResult bindingResult, Principal principal) {
        QuestionDto questionDto = this.questionService.getQuestionDto(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("questionDto", questionDto);
            return "question_detail";
        }

        this.answerService.create(answerDto, questionDto, siteUser);

        return String.format("redirect:/questions/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/answers/modify")
    public String answerModify(AnswerDto answerDto, Principal principal) {
        AnswerDto curAnswerDto = this.answerService.getAnswer(answerDto.getId());
        if (!curAnswerDto.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        answerDto.setContent(curAnswerDto.getContent());
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/answers/modify")
    public String answerModify(@Valid AnswerDto answerDto, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        AnswerDto curAnswerDto = this.answerService.getAnswer(answerDto.getId());
        if (!curAnswerDto.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        this.answerService.modify(curAnswerDto, answerDto.getContent());
        return String.format("redirect:/questions/detail/%s", curAnswerDto.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/answers/delete")
    public String questionDelete(AnswerDto answerDto, Principal principal) {
        AnswerDto curAnswerDto = this.answerService.getAnswer(answerDto.getId());
        if (!curAnswerDto.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        this.answerService.delete(curAnswerDto);
        return String.format("redirect:/questions/detail/%s", curAnswerDto.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/answers/vote")
    public String questionVote(AnswerDto answerDto, Principal principal) {
        AnswerDto curAnswerDto = this.answerService.getAnswer(answerDto.getId());
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.answerService.vote(curAnswerDto, siteUser);
        return String.format("redirect:/questions/detail/%s", curAnswerDto.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/answers/unvote")
    public String questionUnvote(AnswerDto answerDto, Principal principal) {
        AnswerDto curAnswerDto = this.answerService.getAnswer(answerDto.getId());
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.answerService.unvote(curAnswerDto, siteUser);
        return String.format("redirect:/questions/detail/%s", curAnswerDto.getQuestion().getId());
    }
}
