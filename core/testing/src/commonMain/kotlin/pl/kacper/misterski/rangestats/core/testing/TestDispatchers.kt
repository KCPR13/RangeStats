package pl.kacper.misterski.rangestats.core.testing

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
val testDispatcher = UnconfinedTestDispatcher()
