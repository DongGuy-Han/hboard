package com.hboard;

import com.hboard.question.Question;
import com.hboard.question.QuestionDto;
import com.hboard.question.QuestionRepository;
import com.hboard.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class HboardApplicationTests {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private ModelMapper modelMapper;

    @Test
    void test() {
        for (int i = 1; i <=100; i++) {
            String title = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "테스트 중입니다.";
            QuestionDto questionDto = new QuestionDto();
            questionDto.setTitle(title);
            questionDto.setContent(content);
            this.questionService.create(questionDto);
        }
    }

}
