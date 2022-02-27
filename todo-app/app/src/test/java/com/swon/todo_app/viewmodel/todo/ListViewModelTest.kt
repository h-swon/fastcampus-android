package com.swon.todo_app.viewmodel.todo

import com.swon.todo_app.data.entity.ToDoEntity
import com.swon.todo_app.domain.todo.InsertToDoListUseCase
import com.swon.todo_app.presentation.list.ListViewModel
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

    @Test
    fun `test viewModel fetch`(): Unit = runTest {
        val testObservable = viewModel.toDoListLiveData.test()
        viewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                mockList
            )
        )
    }
}