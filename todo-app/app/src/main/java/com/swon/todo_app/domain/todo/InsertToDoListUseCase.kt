package com.swon.todo_app.domain.todo

import com.swon.todo_app.data.entity.ToDoEntity
import com.swon.todo_app.data.repository.ToDoRepository
import com.swon.todo_app.domain.UseCase

internal class InsertToDoListUseCase(
    private val toDoRepository: ToDoRepository
) : UseCase {

    suspend operator fun invoke(toDoList: List<ToDoEntity>) {
        return toDoRepository.insertToDoList(toDoList)
    }
}