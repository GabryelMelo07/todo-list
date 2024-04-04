package com.todolist.application.dto;

import java.time.LocalDate;

public record TodoDTO(String descricao, LocalDate data) {
}
