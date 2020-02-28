package com.ecamarero.spacex.network.launches.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ecamarero.spacex.domain.launches.datasource.LaunchesCoroutinesDataSource
import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.network.launches.HttpTestClientModule
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LaunchesCoroutinesDataSourceTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val clientModule = HttpTestClientModule()

    private lateinit var dataSource: LaunchesCoroutinesDataSource

    @Before
    fun setUp() {
        dataSource = LaunchesDataSourceImpl(clientModule.providesHttpClient())
    }

    @Test
    fun `Fetching all launches returns a list of launches`() {
        clientModule.success = true
        assert(
            runBlocking {
                dataSource.fetchAllLaunchesSuspend()
            } is List<Launch>
        )
    }

    @Test
    fun `On fetching error, exception is raised`() {
        clientModule.success = false
        try {
            runBlocking {
                dataSource.fetchAllLaunchesSuspend()
            }
            assert(false)
        } catch (e: Throwable) {
            assert(e != null)
        }
    }
}