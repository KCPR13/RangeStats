package pl.kacper.misterski.rangestats.core.testing.fake

import pl.kacper.misterski.rangestats.core.data.datasource.vision.VisionDataSource
import pl.kacper.misterski.rangestats.core.domain.enums.TargetZone
import pl.kacper.misterski.rangestats.core.domain.models.AnalysisResult

class FakeVisionDataSource : VisionDataSource {

    var result: AnalysisResult = AnalysisResult(
        shotCount = 5,
        zones = mapOf(
            TargetZone.X to 1,
            TargetZone.TEN to 2,
            TargetZone.NINE to 1,
            TargetZone.EIGHT to 1,
        ),
        score = 9.2f,
    )

    var shouldThrow: Boolean = false

    override suspend fun analyzeTarget(imageBytes: ByteArray): AnalysisResult {
        if (shouldThrow) error("FakeVisionDataSource: simulated error")
        return result
    }
}
