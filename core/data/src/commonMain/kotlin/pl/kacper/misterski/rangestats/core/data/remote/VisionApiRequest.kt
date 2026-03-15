package pl.kacper.misterski.rangestats.core.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VisionApiRequest(
    val model: String,
    @SerialName("max_tokens") val maxTokens: Int,
    val messages: List<MessageRequest>,
)

@Serializable
data class MessageRequest(
    val role: String,
    val content: List<ContentRequest>,
)

@Serializable
data class ContentRequest(
    val type: String,
    val source: ImageSourceRequest? = null,
    val text: String? = null,
)

@Serializable
data class ImageSourceRequest(
    val type: String = "base64",
    @SerialName("media_type") val mediaType: String,
    val data: String,
)
