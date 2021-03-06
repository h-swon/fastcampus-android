package com.swon.todo_app.di

import com.swon.todo_app.data.repository.TestToDoRepository
import com.swon.todo_app.data.repository.ToDoRepository
import com.swon.todo_app.domain.todo.*
import com.swon.todo_app.presentation.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    // ViewModel
    viewModel { ListViewModel(get(), get(), get()) }

    // UseCase
    factory { GetToDoListUseCase(get()) }
    factory { InsertToDoListUseCase(get()) }
    factory { UpdateToDoUseCase(get()) }
    factory { GetToDoItemUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }
    // Repository
    single<ToDoRepository> { TestToDoRepository() }
}