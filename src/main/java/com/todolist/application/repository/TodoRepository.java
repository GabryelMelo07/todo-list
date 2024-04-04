package com.todolist.application.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.todolist.application.model.Todo;

public interface TodoRepository extends PagingAndSortingRepository<Todo, Long> {
    Todo save(Todo todo);
    Page<Todo> findAll(Pageable pageable);
    Optional<Todo> findById(Long id);
    void deleteById(Long id);
}
