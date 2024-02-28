package com.hboard.answer;

import com.hboard.question.Question;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class AnswerDto {

    private Long id;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;

    private LocalDateTime createDate;

    private Question question;

    public static Answer toEntity(AnswerDto answerDto) {
        Answer answer = new Answer(
                answerDto.getId(),
                answerDto.getContent(),
                answerDto.getCreateDate(),
                answerDto.getQuestion()
        );

        return answer;
    }

    public static AnswerDto toDto(Answer answer) {
        AnswerDto answerDto = new AnswerDto(
                answer.getId(),
                answer.getContent(),
                answer.getCreateDate(),
                answer.getQuestion()
        );

        return answerDto;
    }
}
