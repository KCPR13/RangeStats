package pl.kacper.misterski.rangestats.core.data.ktor

import io.ktor.client.engine.HttpClientEngine

expect fun createHttpEngine(): HttpClientEngine
