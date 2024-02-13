package com.hboard.question;

import com.hboard.answer.Answer;
import com.hboard.answer.AnswerDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class QuestionDto {
    private Long id;

    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(max = 100)
    private String title;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;

    private LocalDateTime createDate;

    private List<AnswerDto> answerList;
}
