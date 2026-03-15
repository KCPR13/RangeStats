package pl.kacper.misterski.rangestats.core.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import pl.kacper.misterski.rangestats.core.domain.enums.TargetZone
import pl.kacper.misterski.rangestats.core.domain.models.AnalysisResult
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

//TODO CLEANUP
class AnthropicVisionApiService(
    private val client: HttpClient,
    private val apiKey: String = "",
) {

    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalEncodingApi::class)
    suspend fun analyzeTarget(imageBytes: ByteArray): AnalysisResult {
        val base64Image = Base64.encode(imageBytes)

        val request = VisionApiRequest(
            model = MODEL,
            maxTokens = MAX_TOKENS,
            messages = listOf(
                MessageRequest(
                    role = "user",
                    content = listOf(
                        ContentRequest(
                            type = "image",
                            source = ImageSourceRequest(
                                mediaType = "image/jpeg",
                                data = base64Image,
                            ),
                        ),
                        ContentRequest(
                            type = "text",
                            text = ANALYSIS_PROMPT,
                        ),
                    ),
                ),
            ),
        )

        val response: VisionApiResponse = client.post(BASE_URL) {
            contentType(ContentType.Application.Json)
            header("x-api-key", apiKey)
            header("anthropic-version", VERSION)
            setBody(request)
        }.body()

        return parseAnalysisResult(response)
    }

    private fun parseAnalysisResult(response: VisionApiResponse): AnalysisResult {
        val text = response.content.firstOrNull { it.type == "text" }?.text.orEmpty()
        val jsonStart = text.indexOf('{')
        val jsonEnd = text.lastIndexOf('}')
        if (jsonStart == -1 || jsonEnd == -1) return defaultAnalysisResult()

        return try {
            val jsonStr = text.substring(jsonStart, jsonEnd + 1)
            val dto = json.decodeFromString<AnalysisDto>(jsonStr)
            AnalysisResult(
                shotCount = dto.shotCount,
                zones = dto.zones.mapNotNull { (key, value) ->
                    runCatching { TargetZone.valueOf(key) to value }.getOrNull()
                }.toMap(),
                score = dto.score,
            )
        } catch (_: Exception) {
            defaultAnalysisResult()
        }
    }

    private fun defaultAnalysisResult() = AnalysisResult(
        shotCount = 0,
        zones = emptyMap(),
        score = 0f,
    )

    @Serializable
    private data class AnalysisDto(
        val shotCount: Int,
        val zones: Map<String, Int>,
        val score: Float,
    )

    companion object {
        private const val BASE_URL = "https://api.anthropic.com/v1/messages"
        private const val VERSION = "2023-06-01"
        private const val MODEL = "claude-opus-4-5"
        private const val MAX_TOKENS = 1024
        private const val ANALYSIS_PROMPT = """Analyze this shooting target image. Count bullet holes in each scoring zone.
Return ONLY a JSON object with no extra text:
{"shotCount": <total>, "zones": {"X": <n>, "TEN": <n>, "NINE": <n>, "EIGHT": <n>, "SEVEN": <n>, "SIX": <n>, "MISS": <n>}, "score": <float 0-100>}"""
    }
}
