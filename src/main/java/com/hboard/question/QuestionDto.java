package com.hboard.question;

import com.hboard.answer.Answer;
import com.hboard.answer.AnswerDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
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

    private List<Answer> answerList;

    public static Question toEntity(QuestionDto questionDto) {
        Question question = new Question(
                questionDto.getId(),
                questionDto.getTitle(),
                questionDto.getContent(),
                questionDto.getCreateDate(),
                questionDto.getAnswerList()
        );

        return question;
    }

    public static QuestionDto toDto(Question question) {
        QuestionDto questionDto = new QuestionDto(
                question.getId(),
                question.getTitle(),
                question.getContent(),
                question.getCreateDate(),
                question.getAnswerList()
        );

        return questionDto;
    }
}
