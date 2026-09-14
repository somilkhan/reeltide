# ReelTide — Project State

**Last updated:** 2026-09-14
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current code checkpoint:** `a79dc6499aeaf8731d3b574bf5170ca846c9dc80`

> Live execution checkpoint. Repository/code/build/test evidence outranks stale documentation or conversation memory.

## Current Phase

**Phase 5 — Screen-by-screen presentation replacement: Home/Search checkpoints under runtime isolation**

## DONE

- Active branch remains based on `master`; old `ui/2026-modern-redesign` is not reused.
- Shared Liquid Glass foundation and reusable presentation primitives exist.
- Direct Compose launch-shell NavPill integration was retired after a user-reported launch regression.
- Settings presentation was replaced while preserving SettingsFragment IDs and behavior.
- Home presentation was replaced as a coherent subsystem while leaving Home data/state/business ownership intact.
- Search presentation and bounded empty/error state rendering were integrated while preserving SearchViewModel, adapters, history, suggestions, filters, intent handling and existing search/retry entry points.
- Search checkpoint `0cda31fdc5d15757c1a0dc52add1c92bf170865b` passed GitHub Actions run #217 (`34847250153`).
- For runtime isolation, `activity_main.xml` has now been restored byte-for-byte to the `master` shell presentation in commit `a79dc6499aeaf8731d3b574bf5170ca846c9dc80`. This intentionally removes the floating navigation shell for the diagnostic build only; it does not abandon the Liquid Glass redesign.

## IN PROGRESS

- Diagnostic CI run #219 (`34849938052`) is queued for the master-shell isolation checkpoint.
- Device/runtime verification is still unavailable from the execution environment; user-reported launch crash has no captured ReelTide stacktrace yet.
- After run #219, the diagnostic result will determine whether the launch regression is in the activity shell or elsewhere.

## REMAINING

1. Verify diagnostic run #219 and record its result.
2. If the master shell launches on-device, reintroduce the navigation glass shell incrementally with the smallest safe change set and verify each checkpoint.
3. If the master shell still crashes, isolate the Home loading/presentation subsystem next, then continue screen-by-screen.
4. Replace Details presentation coherently.
5. Replace Library and Downloads presentation.
6. Finish Settings sub-screens/account presentation.
7. Audit player presentation without changing playback behavior.
8. Replace remaining secondary legacy Material surfaces.
9. Add bounded blur only where justified and measurable; do not fake backdrop blur with `Modifier.blur`.
10. Accessibility, reduced-motion, contrast, dynamic text and performance verification.
11. Functional + visual regression matrix and cleanup of obsolete presentation bridges/dead code.

## BLOCKED

- Local Gradle execution is unavailable because outbound repository/network resolution is unavailable.
- No device/runtime automation is available in the execution environment.
- GitHub Actions is the executable build authority.
- The user's current logcat capture contains only `com.termux` PID 16550 output; it does not establish a ReelTide exception or root cause.

## REGRESSIONS / KNOWN RISKS

- Direct Compose launch-shell NavPill remains retired.
- The previous floating native navigation shell is now temporarily removed from `activity_main.xml` solely to isolate the user-reported launch crash.
- The earlier CI regression caused by `itemActiveIndicatorColor` remains fixed; that attribute must not be reintroduced.
- Shell/inset/cast coexistence is not device-verified.
- Search business logic and state ownership remain authoritative; Search Kotlin changes are presentation-state rendering around existing observer outputs.
- True backdrop blur is not being faked; current glass materials rely on bounded translucency, borders, shadows and hierarchy.

## FAILED APPROACHES

- Direct Compose NavPill injection into the launch shell before device verification: rejected after launch regression.
- Small isolated XML polish without subsystem-level verification: rejected.
- Source-only confidence without runtime evidence: rejected.
- `itemActiveIndicatorColor` navigation-shell attribute: removed after CI regression tracing.
- Repeated Termux-only logcat inspection: not useful for the ReelTide crash because the captured process is `com.termux`.

## DECISIONS

- Existing navigation IDs, graph, menu, state, adapters, ViewModels, repositories, player behavior and business ownership remain authoritative.
- Native Android View boundaries are preferred for shell/Home/Settings/Search migration while the app remains XML/ViewBinding-heavy.
- Compose Liquid Glass primitives remain the reusable design-system foundation for safe future presentation boundaries.
- Liquid Glass is a functional floating hierarchy, not universal decoration or blur.
- Monochrome black/white/grey remains the active redesign direction.
- Content uses standard/content-layer treatment; glass is reserved for functional floating controls and transient interaction/state surfaces.
- Runtime regressions are isolated by staged checkpoints rather than speculative multi-file rollback.

## VERIFICATION

- Branch head after diagnostic shell restoration: `a79dc6499aeaf8731d3b574bf5170ca846c9dc80`.
- `activity_main.xml` on the diagnostic checkpoint matches the `master` version, including the original `BottomNavigationView`, original constraints, original tint resource and cast-controller relationship.
- Search checkpoint run #217 completed successfully.
- Diagnostic run #219 is queued; no result is claimed yet.
- No ReelTide crash stacktrace has been captured from the user's provided logs.

## NEXT ACTION

Poll run #219. If green, ask for/install the diagnostic build and use the result to distinguish shell vs Home regression; if the user cannot provide runtime evidence, continue static isolation by comparing the initial Home inflation path against `master` and create the next smallest diagnostic checkpoint.
