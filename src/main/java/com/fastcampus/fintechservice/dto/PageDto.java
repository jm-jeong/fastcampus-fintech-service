package com.fastcampus.fintechservice.dto;


import lombok.Getter;

@Getter
public class PageDto {
    private int page;
    private int size;
    private String sort;
}
