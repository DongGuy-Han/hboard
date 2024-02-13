package com.hboard.question;

import com.hboard.answer.AnswerDto;
import com.hboard.answer.AnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping("/questions/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<QuestionDto> paging = this.questionService.getList(page);

        model.addAttribute("paging", paging);

        return "question_list";
    }

    @GetMapping("/questions/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id, AnswerDto answerDto) {
        QuestionDto questionDto = this.questionService.getQuestionDto(id);

        model.addAttribute("questionDto", questionDto);

        return "question_detail";
    }

    @GetMapping("/questions/create")
    public String questionCreate(QuestionDto questionDto) {
        return "question_form";
    }

    @PostMapping("/questions/create")
    public String questionCreate(@Valid QuestionDto questionDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        this.questionService.create(questionDto);
        return "redirect:/questions/list";
    }
}
