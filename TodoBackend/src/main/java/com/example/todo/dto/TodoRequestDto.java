package com.example.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoRequestDto {

    @Schema(description = "Todo", example = "Java 공부하기")
    @NotBlank
    private String title;

    private boolean completed;

    @NotNull
    @PastOrPresent
    private LocalDate createdAt;

}
