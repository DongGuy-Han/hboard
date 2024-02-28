package com.hboard.answer;

import com.hboard.question.QuestionDto;
import com.hboard.question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/answers/create/{id}")
    public String createAnswer(Model model, @PathVariable Long id,
                               @Valid AnswerDto answerDto, BindingResult bindingResult) {
        QuestionDto questionDto = this.questionService.getQuestionDto(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("questionDto", questionDto);
            return "question_detail";
        }

        this.answerService.create(answerDto, questionDto);

        return String.format("redirect:/questions/detail/%s", id);
    }
}
