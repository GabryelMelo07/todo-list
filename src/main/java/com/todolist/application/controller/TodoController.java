package com.todolist.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.application.dto.TodoDTO;
import com.todolist.application.model.Todo;
import com.todolist.application.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("todo")
public class TodoController {
    
    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    public ResponseEntity<Page<Todo>> getAll(Pageable pageable) {
        return ResponseEntity.ok().body(todoRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo não encontrado.")));
    }
    
    @PostMapping
    public ResponseEntity<Todo> save(@RequestBody TodoDTO dto) {
        return ResponseEntity.ok().body(todoRepository.save(new Todo(dto)));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/done")
    public ResponseEntity<Todo> done(@PathVariable Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo não existe."));
        todo.setConcluido(true);
        return ResponseEntity.ok().body(todoRepository.save(todo));
    }
    
    @PutMapping("/{id}/undo")
    public ResponseEntity<Todo> undo(@PathVariable Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo não existe."));
        todo.setConcluido(false);
        return ResponseEntity.ok().body(todoRepository.save(todo));
    }

}
