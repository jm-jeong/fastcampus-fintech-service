package com.fastcampus.apiserver.service;

import com.fastcampus.common.enums.CodeEnum;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class DemoService {
    public String save() {
        System.out.println(CodeEnum.SUCCESS.getCode());
        LocalDateTime seoulNow = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        return seoulNow.toString();
    }

    public String find() {
        return "find";
    }
}
