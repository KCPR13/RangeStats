package pl.kacper.misterski.rangestats.core.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VisionApiResponse(
    val id: String,
    val type: String,
    val content: List<ContentResponse>,
    val model: String,
    val usage: UsageResponse? = null,
)

@Serializable
data class ContentResponse(
    val type: String,
    val text: String? = null,
)

@Serializable
data class UsageResponse(
    @SerialName("input_tokens") val inputTokens: Int,
    @SerialName("output_tokens") val outputTokens: Int,
)
