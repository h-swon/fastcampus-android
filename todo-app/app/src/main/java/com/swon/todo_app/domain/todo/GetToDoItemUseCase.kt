package com.swon.todo_app.domain.todo

import com.swon.todo_app.data.entity.ToDoEntity
import com.swon.todo_app.data.repository.ToDoRepository
import com.swon.todo_app.domain.UseCase

internal class GetToDoItemUseCase(
    private val toDoRepository: ToDoRepository
) : UseCase {

    suspend operator fun invoke(itemId: Long): ToDoEntity? {
        return toDoRepository.getToDoItem(itemId)
    }
}