package com.swon.todo_app.viewmodel.todo

import com.swon.todo_app.data.entity.ToDoEntity
import com.swon.todo_app.domain.todo.GetToDoItemUseCase
import com.swon.todo_app.domain.todo.InsertToDoListUseCase
import com.swon.todo_app.presentation.list.ListViewModel
import com.swon.todo_app.presentation.list.ToDoListState
import com.swon.todo_app.viewmodel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.koin.test.inject

@ExperimentalCoroutinesApi
internal class ListViewModelTest : ViewModelTest() {

    private val viewModel: ListViewModel by inject()

    private val insertToDoListUseCase: InsertToDoListUseCase by inject()
    private val getToDoItemUseCase: GetToDoItemUseCase by inject()

    private val mockList = (0 until 10).map {
        ToDoEntity(
            id = it.toLong(),
            title = "title $it",
            description = "description $it",
            hasCompleted = false
        )
    }

    @Before
    fun init() {
        initData()
    }

    private fun initData() = runTest {
        insertToDoListUseCase(mockList)
    }

    // Test: 입력된 데이터를 잘 불러오는지
    @Test
    fun `test viewModel fetch`(): Unit = runTest {
        val testObservable = viewModel.toDoListLiveData.test()
        viewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Success(mockList)
            )
        )
    }

    // Test: 데이터 업데이트 시 잘 반영되는지
    @Test
    fun `test Item update`(): Unit = runTest {
        val todo = ToDoEntity(
            id = 1,
            title = "title 1",
            description = "description 1",
            hasCompleted = true
        )
        viewModel.updateEntity(todo)
        assert(getToDoItemUseCase(todo.id)?.hasCompleted ?: false == todo.hasCompleted)
    }

    // Test: 데이터 삭제 시 빈 상태로 보이는지
    @Test
    fun `test Item Delete All`(): Unit = runTest {
        val testObservable = viewModel.toDoListLiveData.test()
        viewModel.deleteAll()
        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Success(listOf())
            )
        )
    }
}