package com.hboard.question;

import com.hboard.answer.AnswerDto;
import com.hboard.answer.AnswerService;
import com.hboard.user.SiteUser;
import com.hboard.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @GetMapping("/questions/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<QuestionDto> paging = this.questionService.getList(page, kw);

        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);

        return "question_list";
    }

    @GetMapping("/questions/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id, AnswerDto answerDto) {
        QuestionDto questionDto = this.questionService.getQuestionDto(id);

        model.addAttribute("questionDto", questionDto);

        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/questions/create")
    public String questionCreate(QuestionDto questionDto) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/questions/create")
    public String questionCreate(@Valid QuestionDto questionDto, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        System.out.println("principal is null");
        SiteUser siteUser = this.userService.getUser(principal.getName());

        this.questionService.create(questionDto, siteUser);
        return "redirect:/questions/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/questions/modify")
    public String questionModify(QuestionDto questionDto, Principal principal) {
        System.out.println(questionDto.getId());
        QuestionDto curQuestionDto = this.questionService.getQuestionDto(questionDto.getId());
        if (!curQuestionDto.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        questionDto.setTitle(curQuestionDto.getTitle());
        questionDto.setContent(curQuestionDto.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/questions/modify")
    public String questionModify(@Valid QuestionDto questionDto, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        QuestionDto curQuestionDto = this.questionService.getQuestionDto(questionDto.getId());
        if (!curQuestionDto.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        this.questionService.modify(curQuestionDto, questionDto.getTitle(), questionDto.getContent());
        return String.format("redirect:/questions/detail/%s", questionDto.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/questions/delete")
    public String questionDelete(QuestionDto questionDto, Principal principal) {
        QuestionDto curQuestionDto = this.questionService.getQuestionDto(questionDto.getId());
        if (!curQuestionDto.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        this.questionService.delete(curQuestionDto);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/questions/vote")
    public String questionVote(QuestionDto questionDto, Principal principal) {
        QuestionDto curQuestionDto = this.questionService.getQuestionDto(questionDto.getId());
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(curQuestionDto, siteUser);
        return String.format("redirect:/questions/detail/%s", questionDto.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/questions/unvote")
    public String questionUnvote(QuestionDto questionDto, Principal principal) {
        QuestionDto curQuestionDto = this.questionService.getQuestionDto(questionDto.getId());
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.unvote(curQuestionDto, siteUser);
        return String.format("redirect:/questions/detail/%s", questionDto.getId());
    }
}
