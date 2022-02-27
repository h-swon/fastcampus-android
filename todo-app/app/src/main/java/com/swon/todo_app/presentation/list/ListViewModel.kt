package com.swon.todo_app.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swon.todo_app.data.entity.ToDoEntity
import com.swon.todo_app.domain.todo.DeleteAllToDoItemUseCase
import com.swon.todo_app.domain.todo.GetToDoListUseCase
import com.swon.todo_app.domain.todo.UpdateToDoUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ListViewModel(
    private val getToDoListUseCase: GetToDoListUseCase,
    private val updateToDoUseCase: UpdateToDoUseCase,
    private val deleteAllToDoItemUseCase: DeleteAllToDoItemUseCase
) : ViewModel() {

    private var _toDoListLiveData = MutableLiveData<List<ToDoEntity>>()
    val toDoListLiveData: LiveData<List<ToDoEntity>> = _toDoListLiveData

    fun fetchData(): Job = viewModelScope.launch {
        _toDoListLiveData.postValue(getToDoListUseCase())
    }

    fun updateEntity(todoEntity: ToDoEntity) = viewModelScope.launch {
        val success = updateToDoUseCase(todoEntity)

    }

    fun deleteAll() = viewModelScope.launch {
        deleteAllToDoItemUseCase()
        _toDoListLiveData.postValue(listOf())
    }
}