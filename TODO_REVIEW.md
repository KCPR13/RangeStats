# TODO Review

List of all `TODO`s found in the code (excluding `/build/`), ranked from easiest to hardest.

## 1. Trivial (cosmetic / hardcoded text)

### 1.1 Hardcoded Polish string instead of a resource
`feature/session/.../dashboard/DashboardScreen.kt:294`
```kotlin
text = "Brak sesji", //TODO
```
The text "Brak sesji" ("No sessions") is hardcoded instead of using `stringResource(Res.string.xxx)` like the rest of the screen (see `dashboard_last_session` one line above). To do: add a string to the `feature:session` module's `composeResources/values/strings.xml` and swap it in.

## 2. Easy (local change, no architectural impact)

### 2.1 Sealed interface living in the same file as the ViewModel
`composeApp/.../core/AppViewModel.kt:14`
```kotlin
//TODO K separate file
sealed interface AppStartDestination { ... }
```
`AppStartDestination` should move to its own `AppStartDestination.kt` file. Purely mechanical extraction.

### 2.2 `FocusRequester` flagged as "ugly"
`feature/ballistics/.../BallisticsScreen.kt:173`
```kotlin
val muzzleVelocityFocus = remember { FocusRequester() } // TODO ugly
```
6 separate `remember { FocusRequester() }` calls in one function. Could be shortened, e.g. with a list/array of `FocusRequester`s or `remember { List(6) { FocusRequester() } }`, but requires matching each field and its `onNext` order carefully. Mostly cosmetic, with some risk of mixing up field order.

### 2.3 Hand-rolled date formatting
`feature/history/.../HistoryViewModel.kt:57-76`
```kotlin
//TODO
private fun formatTimestamp(millis: Long): String { ... }
```
Manually computes year/month/day from `millis` instead of using `kotlinx-datetime` (`Instant.toLocalDateTime`, etc.), which the project likely already has as a KMP dependency. To do: switch to `kotlinx-datetime`, or document why not (e.g. missing dependency on some targets). Risk: timezone / edge-case behavior may differ once switched to a library.

## 3. Medium (touches several places or needs a design decision)

### 3.1 No dependency injection for the validator
`feature/ballistics/.../validator/BallisticsInputValidator.kt:6`
```kotlin
//TODO DI
object BallisticsInputValidator { ... }
```
The validator is an `object` (singleton) instead of a class injected via Koin, which makes it harder to test/swap. Requires: converting it to `class BallisticsInputValidator`, registering it in the `feature:ballistics` Koin module, and updating all call sites (likely just `BallisticsViewModel`).

### 3.2 `Job` held manually instead of a debounced Flow operator
`feature/settings/.../SettingsViewModel.kt:36`
```kotlin
private var persistDistanceJob: Job? = null // TODO review this
```
Manual `cancel()` + `launch` to debounce persisting the distance value (lines 124-128). Could consider a `MutableSharedFlow` + `.debounce()` instead of holding a `Job` field — more idiomatic Kotlin Flow — but requires reworking the save flow (`applyDistance` is also called synchronously from the UI, not only from the debounce path).

### 3.3 Business logic mixed into the UI layer (Composable)
`feature/session/.../DashboardScreen.kt:134` and `:219`
```kotlin
private fun StatsGrid(state: DashboardUiModel) { // TODO business logic
...
val isPositive = delta.startsWith("▲")  // TODO business logic
```
Two spots in the same file: formatting `avgScoreStr`/`bestScoreStr` (`"${state.avgScore.toInt()}%"` vs `"—"`) and parsing the `▲`/`▼` character out of the `delta` string to decide the color happen inside the Composable instead of in `DashboardUiModel`/a mapper. This violates the project's UI Mapper Pattern convention (core:ui components should receive a ready-made `*UiModel`). To do: move formatting into `toUiModel()` / add an `isPositive: Boolean` field and pre-built strings to `DashboardUiModel`, and stop using `String.startsWith("▲")` as the source of truth for trend direction.

### 3.4 Mapping logic living in the ViewModel instead of a dedicated mapper
`feature/session/.../SessionSummaryViewModel.kt:37`
```kotlin
.onSuccess { session -> // TODO K UI mapper standards
    val zones = mutableMapOf<TargetZone, Int>()
    ...
```
The whole block (lines 37-83, ~45 lines) computing zone hit counts, `hits`/`misses`, session duration, and building `zoneRows` should, per the project's convention (`feedback_ui_mapper_pattern.md`), live in a dedicated `toUiModel()`/mapper rather than directly inside the ViewModel's `onSuccess`. Requires splitting the logic into pure functions/extensions and rethinking the signature (it also needs the result of `getWeaponByNameUseCase`, so it isn't a pure function of `Session` alone).

## 4. Hard / unclear (need an architectural or security decision)

### 4.1 No secure API key management
`core/data/.../di/DataModule.kt:39` (related to `remote/AnthropicVisionApiService.kt:17`)
```kotlin
single { AnthropicVisionApiService(get(), getProperty("anthropic.api.key", "")) }
// TODO add key
```
The Anthropic API key is currently pulled via `getProperty(..., "")` with an empty fallback — i.e. effectively unconfigured. This requires a decision: where and how to store the key securely (local.properties + BuildConfig? a remote secrets manager? a backend proxy so the key never ships in the mobile app at all?). High security risk if the key ends up in the repo or baked directly into a client-side app — this needs a conversation with the user/project owner, not a mechanical fix.

### 4.2 `// TODO CLEANUP` with no described scope
`core/data/.../remote/AnthropicVisionApiService.kt:17`
```kotlin
//TODO CLEANUP
class AnthropicVisionApiService(...) { ... }
```
No indication of what exactly needs cleaning up in this class (the whole class is flagged, not a specific line). Could refer to: parsing the response via `indexOf('{')`/`lastIndexOf('}')` instead of proper structured output from the API, the hardcoded `MODEL = "claude-opus-4-5"`, missing HTTP error handling (no `try/catch` around `client.post`), or the whole integration in general. Without more context from the author — hardest to interpret unambiguously, placed here per the "if unclear, hardest group" rule.

### 4.3 "Calculation is bad" without saying what specifically
`feature/ballistics/.../BallisticsScreen.kt:82`
```kotlin
//TODO calculation is bad
@Composable
fun BallisticsScreen(...)
```
The TODO hangs over the entire screen Composable, not over the actual calculation function (which lives in `CalculateTrajectoryUseCase`, a separate file with no TODO of its own). Unclear whether this refers to: an incorrect physics model (BC/drag model), rounding errors in how the result is displayed, or something in the UI. Needs clarification on which specific output values are wrong (drop/velocity/energy/time-of-flight) and under what scenario, before anything can be fixed in `CalculateTrajectoryUseCase`.

### 4.4 "This navigation is tricky" without describing the problem
`feature/onboarding/.../OnboardingNavigation.kt:43`
```kotlin
//TODO this navigation is tricky
LaunchedEffect(state.isCompleted) {
    if (state.isCompleted) {
        viewModel.onAction(OnboardingAction.NavigationHandled)
        onComplete()
    }
}
```
No information on what the "trickiness" actually is — possible candidates: a race condition between `NavigationHandled` and `onComplete()` (state gets reset before navigating — order may be intentional or a bug), the effect double-firing on recomposition, or a conflict with the other `LaunchedEffect` below (`navigateToAddWeapon`) if both conditions become true at once. Needs clarification from the user on the specific bug/edge case that was observed.

---

## Summary (easiest to hardest)

| # | File | Difficulty |
|---|------|----------|
| 1.1 | DashboardScreen.kt:294 | trivial |
| 2.1 | AppViewModel.kt:14 | easy |
| 2.2 | BallisticsScreen.kt:173 | easy |
| 2.3 | HistoryViewModel.kt:57 | easy |
| 3.1 | BallisticsInputValidator.kt:6 | medium |
| 3.2 | SettingsViewModel.kt:36 | medium |
| 3.3 | DashboardScreen.kt:134,219 | medium |
| 3.4 | SessionSummaryViewModel.kt:37 | medium |
| 4.1 | DataModule.kt:39 | hard (security, decision needed) |
| 4.2 | AnthropicVisionApiService.kt:17 | hard (unclear scope) |
| 4.3 | BallisticsScreen.kt:82 | hard (unclear scope) |
| 4.4 | OnboardingNavigation.kt:43 | hard (unclear scope) |