package com.example.todo.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoResponseDto {

    private Long id;
    private String title;
    private boolean completed;
    private LocalDate createdAt;

}
