package com.hboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(HboardApplication.class, args);
    }


    /**
    * ModelMapper를 이용한 변환을 Dto클래스에 메서드를 생성하여 대신하였다.
    * */

    /*
    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper;
    }*/

}
