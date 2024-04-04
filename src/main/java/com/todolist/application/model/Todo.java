package com.todolist.application.model;

import java.time.LocalDate;

import com.todolist.application.dto.TodoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "todo")
@Data
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 200, nullable = false)
    private String descricao;

    @Column
    private LocalDate data;
    
    @Column
    private Boolean concluido;
    
    public Todo(String descricao) {
        this.descricao = descricao;
        this.data = LocalDate.now();
        this.concluido = false;
    }

    public Todo(TodoDTO dto) {
        this.descricao = dto.descricao();
        this.data = dto.data();
        this.concluido = false;
    }
    
}
