package com.hboard.answer;

import com.hboard.question.Question;
import com.hboard.user.SiteUser;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@Setter
@Getter
public class AnswerDto {

    private Long id;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;

    private LocalDateTime createDate;

    private Question question;

    private SiteUser author;

    Set<SiteUser> voter;

    public static Answer toEntity(AnswerDto answerDto) {
        Answer answer = new Answer(
                answerDto.getId(),
                answerDto.getContent(),
                answerDto.getCreateDate(),
                answerDto.getQuestion(),
                answerDto.getAuthor(),
                answerDto.getVoter()
        );

        return answer;
    }

    public static AnswerDto toDto(Answer answer) {
        AnswerDto answerDto = new AnswerDto(
                answer.getId(),
                answer.getContent(),
                answer.getCreateDate(),
                answer.getQuestion(),
                answer.getAuthor(),
                answer.getVoter()
        );

        return answerDto;
    }
}
