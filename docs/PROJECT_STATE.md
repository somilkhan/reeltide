# ReelTide — Project State

**Last updated:** 2026-09-14
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current head:** `90b91e7f179c4c1dfba26620126423d039831f0e`

> Live execution checkpoint. Repository/code/build/test evidence outranks stale documentation or conversation memory.

## Current Phase

**Phase 3 → Phase 5 — Core presentation foundation, shell and Home replacement**

## Current Objective

Move ReelTide from a subtle Material reskin toward a clearly premium monochrome Liquid Glass presentation while preserving navigation, Home state, adapters, repositories, player behavior and business logic.

## COMPLETED / RECONSTRUCTED

- Active branch reconstructed from GitHub; baseline is `master` with no divergence.
- Architecture, research, UI/UX/design and execution-control documents exist and remain authoritative.
- Shared Liquid Glass Compose foundation exists: theme/tokens, surfaces, controls, media card, modal/content-state primitives and route mapping tests.
- Previous experimental `LiquidGlassNavPillView` launch integration caused a user-reported launch regression; the shell was restored in `e22aa989...`, whose CI run `34764860428` succeeded.
- The obsolete/unintegrated `LiquidGlassNavPillView.kt` has now been removed from the active branch to eliminate the known risky duplicate launch implementation.
- Phone navigation is now presented through a native Android floating glass surface wrapping the existing `BottomNavigationView`; navigation IDs, menu, graph and business ownership remain unchanged.
- Home now has stronger content framing: fixed brand header, edge-to-edge content spacing, stronger section hierarchy and deeper media-card treatment.
- Home changes remain at the existing XML/ViewBinding presentation boundary; HomeFragment state/data/business logic was not changed.

## CHANGED IN CURRENT EXECUTION

- Added `app/src/main/res/drawable/liquid_glass_home_toolbar.xml`.
- Reworked `app/src/main/res/layout/activity_main.xml` to use a native floating glass nav container around the existing `nav_view`.
- Reworked `app/src/main/res/layout/fragment_home.xml` with stronger Home framing and preserved existing IDs.
- Refined `app/src/main/res/layout/home_result_grid.xml` for poster depth, corners and typography.
- Refined `app/src/main/res/layout/homepage_parent.xml` for section rhythm and spacing.
- Removed `app/src/main/java/com/lagradost/cloudstream3/ui/liquidglass/LiquidGlassNavPillView.kt`.
- Removed an unused error-state drawable that was created but intentionally not integrated.
- Reconciled this state document with the actual branch.

## IN PROGRESS

- Current shell/Home checkpoint has no CI result yet; it is not build-verified.
- Search has been inspected and is still legacy presentation; an attempted update was rejected by GitHub's stale blob check and therefore **no Search change was applied**.
- Home loading/error/empty states still need a coherent visual pass.
- Shell edge-to-edge/insets and cast mini-controller coexistence require runtime verification.

## REMAINING

1. Obtain build/CI evidence for the current shell/Home checkpoint.
2. Complete Home loading/error/empty presentation and interaction polish.
3. Verify floating navigation, back behavior, insets and cast mini-controller coexistence.
4. Replace Search presentation.
5. Replace Details presentation.
6. Replace Library and Downloads presentation.
7. Replace Settings/account presentation.
8. Audit player presentation without changing playback behavior.
9. Replace remaining secondary legacy Material surfaces.
10. Add bounded blur only where justified and measurable.
11. Accessibility, reduced-motion, contrast, dynamic text and performance verification.
12. Functional + visual regression matrix.
13. Remove obsolete presentation bridges/dead code after stable replacements.

## BLOCKED

- Local Gradle execution is unavailable because outbound repository/network resolution is unavailable.
- No device/runtime automation is available in the current execution environment.
- GitHub Actions is the executable build authority, but the connector has not exposed a workflow run for the newest API-created commits yet.

## REGRESSIONS / KNOWN RISKS

- The prior Compose NavPill launch integration is explicitly retired after the launch regression.
- The new navigation shell uses standard Android Views rather than a custom ComposeView to reduce runtime integration risk.
- Current UI changes are source-inspected but not device-verified.
- True backdrop blur is not being faked with Compose `Modifier.blur`; current material relies on bounded translucency, borders, shadow and hierarchy.

## FAILED APPROACHES

- Wiring the custom Compose `LiquidGlassNavPillView` directly into the launch shell before device verification: rejected after launch regression.
- Treating a small XML polish as sufficient redesign: rejected after user runtime feedback; shell and Home hierarchy are now being replaced coherently.
- Source-only confidence: not accepted as verification.
- Search update via stale blob SHA: rejected by GitHub; no unverified Search mutation was forced through.

## DECISIONS

- Existing navigation IDs, graph, menu, state and business ownership remain authoritative.
- Native Android View boundaries are preferred for the current shell/Home migration because the app is heavily XML/ViewBinding based.
- Compose Liquid Glass primitives remain the reusable design-system foundation for future safe presentation boundaries.
- Liquid Glass is a functional floating hierarchy, not universal decoration or blur.
- Apple Liquid Glass research reinforces hierarchy, harmony, consistency and a distinct UI layer above content; this is the design direction, not a literal platform API dependency.

## VERIFICATION

- GitHub branch/ref state inspected directly.
- Current branch is 66 commits ahead of `master` and 0 behind.
- Previous restored-shell CI `34764860428` succeeded on `e22aa989...`.
- Current head is `90b91e7f...`.
- Current-head CI/status list is empty; therefore **current changes are not build-verified**.
- No current device/runtime verification is claimed.

## NEXT RESUME ACTION

First check whether CI has appeared for `90b91e7f...`. If it is green, continue Home state completion and shell/inset/cast audit. If no CI is available, continue source-level work on the next coherent presentation subsystem while keeping all unverified changes clearly marked.
