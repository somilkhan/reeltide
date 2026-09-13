# ReelTide — Project State

**Last updated:** 2026-09-13
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current head:** `1d44d99022fb30d9c250028223007f835bcba73e`

> Live execution checkpoint. Repository/build/test evidence outranks stale documentation or conversation memory.

## Current Phase

**Phase 3 — Core UI architecture, with navigation subsystem in compile verification**

## Current Objective

Establish the reusable Liquid Glass presentation foundation, make the phone navigation boundary compile-clean and behaviorally safe, then integrate the verified primitives into the shell and Home.

## DONE

- Baseline, branch, architecture and design-authority documents established and reconciled.
- Current Apple-inspired Liquid Glass direction and Android implementation constraints persisted in project research.
- Primary/secondary navigation responsibilities and screen/state ownership were audited at source level.
- Phone `LiquidGlassNavPillView` integrated into the existing shell while preserving existing navigation ownership behind the compatibility boundary.
- Explicit NavPill interaction states established: `Idle`, `Pressed`, `Selected`, `Transitioning`, `Disabled`, `Scrolling`.
- Shared `ReelTideGlassTokens` / `ReelTideTheme` established.
- Shared `GlassSurface`, `GlassCard`, `GlassPill`, `GlassButton`, `GlassIconButton` established.
- Shared `GlassMediaCard`, `GlassSheet`, `GlassDialog`, `GlassLoadingState`, `GlassEmptyState`, and `GlassErrorState` established.
- Navigation destination grouping extracted into `LiquidGlassNavigation.kt`.
- Navigation route mapping tests added.
- Draft verification PR opened against `master` to obtain executable GitHub Actions build evidence.

## IN PROGRESS

- Fixing compiler failures exposed by the first real CI build.
- Re-running CI after fixes.
- Verifying NavPill behavior beyond source-level inspection.
- Auditing shell edge-to-edge/insets and cast mini-controller interaction.
- Completing secondary route/state ownership audit.

## REMAINING

- Establish passing active-branch build/test/lint evidence.
- Integrate the shared material system into the app shell.
- Rebuild Home using the shared primitives and existing Home state/business logic.
- Rebuild Search, Details, Library, Downloads and Settings presentation.
- Audit player presentation without changing playback behavior.
- Replace remaining secondary legacy Material-default presentation.
- Add/profile bounded blur where justified by hierarchy and verified for performance.
- Accessibility/performance verification.
- Functional and visual regression verification.
- Remove obsolete presentation code and temporary compatibility bridges after stable replacement.

## BLOCKED

No device/runtime visual verification tool is currently available through the execution environment. Local Gradle execution is unavailable, but GitHub Actions provides an executable CI path.

## REGRESSIONS

CI exposed two actual compile issues in the new presentation code; both are being treated as implementation defects rather than ignored:

1. `LiquidGlassNavPillView.kt` referenced `BottomNavigationView.menu` / `selectedItemId` through an untyped Android `View` lookup.
2. `GlassModal.kt` used an experimental Material API without opt-in.

No existing business/data/navigation regression has been demonstrated.

## FAILED APPROACHES

### Untyped NavPill compatibility lookup
The first implementation attempted to recover `menu` and `selectedItemId` from an untyped `View`. Kotlin correctly rejected those members. Replacement: use the concrete child navigation item view for the existing click contract instead of reaching through the untyped parent.

### Source-only API confidence
Source inspection did not catch the two compiler issues. Real CI is now mandatory for presentation changes before treating the component API as verified.

### Legacy redesign patching
The previous redesign branch remains historical and is not used as the implementation base.

## DECISIONS

- Active implementation branch remains `ui/2026-liquid-glass-redesign`.
- `master` remains the comparison baseline.
- Existing business logic, state, repositories, player behavior and navigation ownership remain authoritative.
- Liquid Glass is implemented as hierarchical functional material, not a universal blur/filter.
- Native Compose/Android motion is preferred over web-specific motion libraries.
- Shared primitives must be reused across screens; screen-specific glass copies are rejected.
- CI is the executable build authority while local Gradle/device execution is unavailable.

## VERIFICATION

- GitHub branch inspection confirms the active redesign branch and current head.
- Draft PR #2 targets `master` and contains the active redesign branch.
- CI run `34728590074` executed the real Gradle build.
- `library:checkKotlinAbi` passed.
- The app build progressed through resource generation and reached `:app:compilePrereleaseDebugKotlin`.
- CI reported exactly two new compile problems in the redesign code; those defects were identified and corrected in subsequent commits.
- The corrected branch head is `1d44d99022fb30d9c250028223007f835bcba73e`; a new CI result for this head is pending.
- Runtime/device visual and interaction verification remains pending.

## LAST VERIFIED COMMIT

`1d44d99022fb30d9c250028223007f835bcba73e` — compiler-fix checkpoint. This commit itself is not yet build-verified; CI must run against it before it is considered compile-clean.

## NEXT RESUME ACTION

Inspect the CI run triggered for `1d44d99022fb30d9c250028223007f835bcba73e`. If green, continue with shell/inset/cast audit and Home integration. If red, diagnose the exact compiler/test failure, fix the owning subsystem, and rerun CI. Do not move to screen replacement while the shared foundation remains compile-unverified.
