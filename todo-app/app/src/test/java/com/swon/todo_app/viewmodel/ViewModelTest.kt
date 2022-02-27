package com.swon.todo_app.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import com.swon.todo_app.di.appTestModule
import com.swon.todo_app.livedata.LiveDataTestObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule




@ExperimentalCoroutinesApi
internal abstract class ViewModelTest : KoinTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var context: Application

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        startKoin {
            androidContext(context)
            modules(appTestModule)
        }
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    protected fun <T> LiveData<T>.test(): LiveDataTestObserver<T> {
        val testObserver = LiveDataTestObserver<T>()
        observeForever(testObserver)
        return testObserver
    }

}