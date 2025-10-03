package com.example.todo.controller;

import com.example.todo.dto.TodoRequestDto;
import com.example.todo.dto.TodoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Todo List App", description = "Todo 리스트")
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Operation(
            summary = "Todo 생성",
            description = "Todo를 생성합니다."
    )
    @ApiResponse(
            responseCode = "201",
            description = "생성 성공",
            content = @Content(schema = @Schema(implementation = TodoResponseDto.class))
    )
    @PostMapping
    public ResponseEntity<TodoResponseDto> create(
            @Valid @RequestBody TodoRequestDto request
    ) {
        TodoResponseDto created = todoService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Todo List 조회",
            description = "등록된 모든 Todo List를 조회합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(schema = @Schema(implementation = TodoResponseDto.class))
    )
    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAll(
            @RequestParam(defaultValue = "newest") String sort
    ) {
        

        String normalized = switch (sort.toLowerCase()) {
            case "newest", "lastest", "desc" -> "newest";
            case "oldest", "asc" -> "oldest";
            default -> "newest";
        };

        List<TodoResponseDto> todoList = todoService.getListBySort(normalized);

        return ResponseEntity.ok(todoList);
    }

    @Operation(
            summary = "Todo List 수정",
            description = "지정한 ID의 Todo를 수정합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "수정 성공",
            content = @Content(schema = @Schema(implementation = TodoResponseDto.class))
    )
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody TodoRequestDto request
    ) {
        TodoResponseDto updated = todoService.update(id, request);

        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Todo List 토글 수정",
            description = "지정한 ID의 Todo를 수정합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "수정 성공",
            content = @Content(schema = @Schema(implementation = TodoResponseDto.class))
    )
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Map<String, Object>> toggle(@PathVariable Long id) {
        boolean newState = todoService.toggle(id);
        return ResponseEntity.ok(Map.of(
            "id", id,
            "completed", newState,
            "message", newState ? "완료로 변경되었습니다." : "미완료로 변경되었습니다."
        ));
    }

    @Operation(
            summary = "Todo List 삭제",
            description = "지정한 ID의 Todo를 삭제합니다."
    )
    @ApiResponse(
            responseCode = "204",
            description = "삭제 성공"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<TodoResponseDto> delete(
            @PathVariable Long id
    ) {
        todoService.delete(id);

        return ResponseEntity.noContent().build();
    }



}
