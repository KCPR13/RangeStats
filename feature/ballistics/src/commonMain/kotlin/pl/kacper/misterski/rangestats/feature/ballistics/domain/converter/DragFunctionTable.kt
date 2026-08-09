package pl.kacper.misterski.rangestats.feature.ballistics.domain.converter

import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BcModel

// Standard G1/G7 drag coefficient tables (Mach -> Cd), McCoy/BRL reference data.
internal fun dragCoefficient(model: BcModel, mach: Double): Double {
    val table = if (model == BcModel.G1) G1_DRAG_TABLE else G7_DRAG_TABLE
    return interpolateCd(table, mach)
}

private fun interpolateCd(table: List<Pair<Double, Double>>, mach: Double): Double {
    if (mach <= table.first().first) return table.first().second
    if (mach >= table.last().first) return table.last().second
    for (i in 0 until table.size - 1) {
        val (m0, cd0) = table[i]
        val (m1, cd1) = table[i + 1]
        if (mach <= m1) {
            val t = (mach - m0) / (m1 - m0)
            return cd0 + t * (cd1 - cd0)
        }
    }
    return table.last().second
}

private val G1_DRAG_TABLE = listOf(
    0.0 to 0.2629, 0.05 to 0.2558, 0.1 to 0.2487, 0.15 to 0.2413,
    0.2 to 0.2344, 0.25 to 0.2278, 0.3 to 0.2214, 0.35 to 0.2155,
    0.4 to 0.2104, 0.45 to 0.2061, 0.5 to 0.2032, 0.55 to 0.2020,
    0.6 to 0.2034, 0.7 to 0.2165, 0.725 to 0.2230, 0.75 to 0.2313,
    0.775 to 0.2417, 0.8 to 0.2546, 0.825 to 0.2706, 0.85 to 0.2901,
    0.875 to 0.3136, 0.9 to 0.3415, 0.925 to 0.3734, 0.95 to 0.4084,
    0.975 to 0.4448, 1.0 to 0.4805, 1.025 to 0.5136, 1.05 to 0.5427,
    1.075 to 0.5677, 1.1 to 0.5883, 1.125 to 0.6053, 1.15 to 0.6191,
    1.2 to 0.6393, 1.25 to 0.6518, 1.3 to 0.6589, 1.35 to 0.6621,
    1.4 to 0.6625, 1.45 to 0.6607, 1.5 to 0.6573, 1.55 to 0.6528,
    1.6 to 0.6474, 1.65 to 0.6413, 1.7 to 0.6347, 1.75 to 0.6280,
    1.8 to 0.6210, 1.85 to 0.6141, 1.9 to 0.6072, 1.95 to 0.6003,
    2.0 to 0.5934, 2.05 to 0.5867, 2.1 to 0.5804, 2.15 to 0.5743,
    2.2 to 0.5685, 2.25 to 0.5630, 2.3 to 0.5577, 2.35 to 0.5527,
    2.4 to 0.5481, 2.45 to 0.5438, 2.5 to 0.5397, 2.6 to 0.5325,
    2.7 to 0.5264, 2.8 to 0.5211, 2.9 to 0.5168, 3.0 to 0.5133,
    3.1 to 0.5105, 3.2 to 0.5084, 3.3 to 0.5067, 3.4 to 0.5054,
    3.5 to 0.5040, 3.6 to 0.5030, 3.7 to 0.5022, 3.8 to 0.5016,
    3.9 to 0.5010, 4.0 to 0.5006, 4.2 to 0.4998, 4.4 to 0.4995,
    4.6 to 0.4992, 4.8 to 0.4990, 5.0 to 0.4988,
)

private val G7_DRAG_TABLE = listOf(
    0.0 to 0.1198, 0.05 to 0.1197, 0.1 to 0.1196, 0.15 to 0.1194,
    0.2 to 0.1193, 0.25 to 0.1194, 0.3 to 0.1194, 0.35 to 0.1194,
    0.4 to 0.1193, 0.45 to 0.1193, 0.5 to 0.1194, 0.55 to 0.1193,
    0.6 to 0.1194, 0.65 to 0.1197, 0.7 to 0.1202, 0.725 to 0.1207,
    0.75 to 0.1215, 0.775 to 0.1226, 0.8 to 0.1242, 0.825 to 0.1266,
    0.85 to 0.1306, 0.875 to 0.1368, 0.9 to 0.1464, 0.925 to 0.1660,
    0.95 to 0.2054, 0.975 to 0.2993, 1.0 to 0.3803, 1.025 to 0.4015,
    1.05 to 0.4043, 1.075 to 0.4034, 1.1 to 0.4014, 1.125 to 0.3987,
    1.15 to 0.3955, 1.2 to 0.3884, 1.25 to 0.3810, 1.3 to 0.3732,
    1.35 to 0.3657, 1.4 to 0.3580, 1.5 to 0.3440, 1.55 to 0.3376,
    1.6 to 0.3315, 1.65 to 0.3260, 1.7 to 0.3209, 1.75 to 0.3160,
    1.8 to 0.3117, 1.85 to 0.3078, 1.9 to 0.3042, 1.95 to 0.3010,
    2.0 to 0.2980, 2.05 to 0.2951, 2.1 to 0.2922, 2.15 to 0.2892,
    2.2 to 0.2864, 2.25 to 0.2835, 2.3 to 0.2807, 2.35 to 0.2779,
    2.4 to 0.2752, 2.45 to 0.2725, 2.5 to 0.2697, 2.55 to 0.2670,
    2.6 to 0.2643, 2.65 to 0.2615, 2.7 to 0.2588, 2.75 to 0.2561,
    2.8 to 0.2533, 2.85 to 0.2506, 2.9 to 0.2479, 2.95 to 0.2451,
    3.0 to 0.2424, 3.1 to 0.2368, 3.2 to 0.2313, 3.3 to 0.2258,
    3.4 to 0.2205, 3.5 to 0.2154, 3.6 to 0.2106, 3.7 to 0.2060,
    3.8 to 0.2017, 3.9 to 0.1975, 4.0 to 0.1935, 4.2 to 0.1861,
    4.4 to 0.1793, 4.6 to 0.1730, 4.8 to 0.1672, 5.0 to 0.1618,
)
