# ReelTide — Project State

**Last updated:** 2026-09-14
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current head:** `aae42b0e72e3d0335da8db4344c2173e9826fc3d`

> Live execution checkpoint. Repository/code/build/test evidence outranks stale documentation or conversation memory.

## Current Phase

**Phase 3 → Phase 5 — Core presentation foundation, shell, Home and Settings replacement**

## COMPLETED / RECONSTRUCTED

- Active branch reconstructed directly from GitHub; it remains based on `master` and the old redesign branch is not being reused.
- Shared Liquid Glass Compose foundation exists, but risky direct Compose launch-shell integration is retired.
- Previous custom Compose `LiquidGlassNavPillView` launch integration caused a user-reported launch regression and was removed; the shell remains at a native View boundary.
- Phone navigation remains owned by the existing `BottomNavigationView`, menu and navigation graph, now presented inside a floating monochrome glass shell.
- Settings presentation was replaced as a coherent XML/ViewBinding subsystem while preserving all navigation IDs used by `SettingsFragment`.
- Home presentation is now treated as one subsystem: hero framing, floating header, action controls, section presentation, loading skeleton, error surface, and phone provider/random controls.
- HomeFragment state/data/business logic remains untouched; the existing `HomeParentItemAdapterPreview`, `HomeScrollAdapter`, ViewPager2 and HomeViewModel ownership remain authoritative.

## CHANGED IN CURRENT EXECUTION

- `activity_main.xml`: removed the unsupported `itemActiveIndicatorColor` attribute after tracing the first persistent CI regression to the navigation-shell commit; monochrome icon/text selector remains.
- `fragment_home.xml`: replaced the legacy loading skeleton with a cinematic hero/content skeleton, replaced the raw centered error layout with a bounded glass state surface, and restyled phone provider/random actions as restrained glass pills.
- Added `liquid_glass_state_surface.xml` for reusable Home state hierarchy.
- Added `liquid_glass_fab.xml` for shared provider/random action material.
- Preserved all existing Home IDs and click targets, including provider selection/reload, search, account switching, random playback and error actions.
- `docs/PROJECT_STATE.md` updated to this checkpoint.

## IN PROGRESS

- Final CI verification for the current Home checkpoint.
- Device/runtime visual verification is still unavailable in this execution environment.
- Home visual quality still needs verification against real content and scrolling on-device.

## REMAINING

1. Verify current Home checkpoint with GitHub Actions and inspect any failure before adding more UI.
2. Verify floating navigation, back behavior, insets and cast mini-controller coexistence.
3. Replace Search presentation as a coherent subsystem.
4. Replace Details presentation.
5. Replace Library and Downloads presentation.
6. Finish Settings sub-screens/account presentation consistently.
7. Audit player presentation without changing playback behavior.
8. Replace remaining secondary legacy Material surfaces.
9. Add bounded blur only where justified and measurable; do not fake backdrop blur with `Modifier.blur`.
10. Accessibility, reduced-motion, contrast, dynamic text and performance verification.
11. Functional + visual regression matrix.
12. Remove obsolete presentation bridges/dead code after stable replacements.

## BLOCKED

- Local Gradle execution is unavailable because outbound repository/network resolution is unavailable.
- No device/runtime automation is available in the current execution environment.
- GitHub Actions is the executable build authority.

## REGRESSIONS / KNOWN RISKS

- Direct Compose launch-shell NavPill remains retired after the previous launch regression.
- Source changes are not device-verified.
- The supplied runtime screenshots remain evidence that the earlier installed checkpoint was below the intended visual quality bar; those screenshots predate the latest Home subsystem pass.
- True backdrop blur is not being faked; current materials use bounded translucency, borders, shadows and hierarchy.
- CI runs 175–184 failed after the navigation-shell change; chronology isolated the first failure to `59976113...`, whose semantic shell change introduced the active-indicator navigation attribute. That attribute has now been removed. A fresh CI run for the current Home checkpoint is in progress; its result is not yet claimed.

## FAILED APPROACHES

- Wiring a custom Compose NavPill directly into the launch shell before device verification: rejected after launch regression.
- Treating small XML polish as sufficient redesign: rejected by runtime visual feedback.
- Source-only confidence: not accepted as verification.
- Search mutation using a stale blob SHA: rejected by GitHub; Search remains unchanged.
- `itemActiveIndicatorColor` in the phone navigation shell: removed after CI regression tracing; native tint selector is retained for monochrome navigation without relying on that attribute.

## DECISIONS

- Existing navigation IDs, graph, menu, state, adapters, ViewModels, repositories, player behavior and business ownership remain authoritative.
- Native Android View boundaries are preferred for the shell/Home/Settings migration while the app remains heavily XML/ViewBinding based.
- Compose Liquid Glass primitives remain the reusable design-system foundation for future safe presentation boundaries.
- Liquid Glass is a functional floating hierarchy, not universal decoration or blur.
- Monochrome black/white/grey is the active redesign direction; theme-primary blue must not appear as an accidental navigation selection treatment.
- Home content uses standard/content-layer treatment; glass is reserved for functional floating controls and transient state surfaces.

## VERIFICATION

- Current branch/ref and implementation reconstructed directly from GitHub.
- The previous successful checkpoint was CI run #174 (`4289619...`). Runs #175–184 failed; chronology isolated the first failure to the navigation-shell commit, and the active-indicator attribute was removed.
- Current Home checkpoint head is `aae42b0e72e3d0335da8db4344c2173e9826fc3d`.
- GitHub Actions run #189 targets the current head and is queued/in progress; no green result is claimed yet.
- No device/runtime verification is claimed.

## NEXT RESUME ACTION

Check run #189 to completion. If green, perform shell/inset/cast regression inspection and then begin the Search presentation replacement as one coherent subsystem. If CI fails, diagnose the exact failure before adding further UI work.
