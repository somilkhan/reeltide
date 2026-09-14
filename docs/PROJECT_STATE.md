# ReelTide — Project State

**Last updated:** 2026-09-14
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current head:** `e784c9c54a181ff60379608da7dc0b7485e8258e`

> Live execution checkpoint. Repository/code/build/test evidence outranks stale documentation or conversation memory.

## Current Phase

**Phase 5 — Screen-by-screen presentation replacement: Home complete checkpoint, Search presentation in progress**

## COMPLETED / RECONSTRUCTED

- Active branch reconstructed directly from GitHub; it remains based on `master` and the old `ui/2026-modern-redesign` branch is not being reused.
- Shared Liquid Glass Compose foundation exists, but risky direct Compose launch-shell integration is retired.
- The previous custom Compose `LiquidGlassNavPillView` launch integration caused a user-reported launch regression and was removed; the shell remains at a native View boundary.
- Phone navigation remains owned by the existing `BottomNavigationView`, menu and navigation graph, now presented inside a floating monochrome glass shell.
- Settings presentation was replaced as a coherent XML/ViewBinding subsystem while preserving the IDs used by `SettingsFragment`.
- Home presentation is treated as one subsystem: hero framing, floating header, action controls, section presentation, loading skeleton, error surface, and phone provider/random controls.
- HomeFragment state/data/business logic remains untouched; existing Home adapters, ViewPager2 and HomeViewModel ownership remain authoritative.
- Search behavior/data/business ownership was inspected before the Search presentation replacement. `SearchFragment`, `SearchViewModel`, `SearchAdapter`, `SearchHistoryAdaptor`, `SearchSuggestionAdapter`, provider/type filters, history, voice search, and intent-driven queries remain the behavioral authorities.

## CHANGED IN CURRENT EXECUTION

- `activity_main.xml`: the unsupported `itemActiveIndicatorColor` attribute remains removed; native phone navigation uses the monochrome icon/text selector inside the floating glass shell.
- Search presentation was replaced as a coherent phone subsystem without changing `SearchFragment.kt` behavior:
  - floating glass search field;
  - separate glass voice and filter controls;
  - monochrome media-type filter chips;
  - glass suggestion surface and interaction rows;
  - refined recent-search rows;
  - refined phone and expanded search result cards;
  - preserved existing SearchView, RecyclerView, adapter, history, suggestion, filter and focus IDs/contracts.
- Added reusable Search presentation resources:
  - `liquid_glass_search_field.xml`
  - `liquid_glass_search_action.xml`
  - `liquid_glass_search_item.xml`
  - `liquid_glass_search_surface.xml`
  - `liquid_glass_chip_background.xml`
  - `liquid_glass_chip_stroke.xml`
  - `LiquidGlassFilterChip` style in `liquid_glass_search.xml`
- Updated `fragment_search.xml`, `tvtypes_chips.xml`, `search_history_item.xml`, `search_suggestion_item.xml`, `search_result_grid.xml`, and `search_result_grid_expanded.xml`.
- An unintegrated Search empty/error state prototype was intentionally removed rather than leaving dead UI/resources. Search currently retains its existing loading indicator and behavior; explicit empty/error presentation remains a follow-up integration task because it requires modifying the owning `SearchFragment` observer/state path.

## IN PROGRESS

- GitHub Actions verification of the current Search presentation checkpoint.
- Device/runtime visual verification remains unavailable in this execution environment.
- Search state presentation (explicit no-results/error surface) is still incomplete; current business state handling remains unchanged.
- Shell/inset/cast coexistence still needs device verification.

## REMAINING

1. Complete CI verification for the current Search checkpoint and diagnose any real failure before proceeding.
2. Complete Search empty/error state integration in `SearchFragment` without duplicating state ownership.
3. Verify floating navigation, back behavior, edge-to-edge insets and cast mini-controller coexistence.
4. Replace Details presentation as one coherent subsystem.
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
- Earlier installed screenshots showed the old checkpoint was below the intended visual quality bar; those screenshots predate the latest Home and Search presentation passes.
- True backdrop blur is not being faked; current materials use bounded translucency, borders, shadows and hierarchy.
- CI runs 175–184 failed after the navigation-shell change; chronology isolated the first failure to `59976113...`, whose semantic shell change introduced the active-indicator navigation attribute. That attribute remains removed.
- The current Search pass intentionally changes presentation only. Search business logic, providers, history, suggestions, intent handling, navigation and adapter ownership were not rewritten.
- The shell currently relies on the existing `fixSystemBarsPadding(navView, ...)` path; exact floating-surface/inset geometry still requires device verification, especially across gesture and three-button navigation modes.

## FAILED APPROACHES

- Wiring a custom Compose NavPill directly into the launch shell before device verification: rejected after launch regression.
- Treating small XML polish as sufficient redesign: rejected by runtime visual feedback.
- Source-only confidence: not accepted as verification.
- Search mutation using a stale blob SHA: rejected by GitHub; later Search changes were applied against current blob SHAs.
- `itemActiveIndicatorColor` in the phone navigation shell: removed after CI regression tracing; native tint selector is retained for monochrome navigation without relying on that attribute.
- Adding an unintegrated Search empty/error prototype: removed immediately to avoid dead presentation code; the state integration remains explicitly owned by `SearchFragment` for the next Search checkpoint.

## DECISIONS

- Existing navigation IDs, graph, menu, state, adapters, ViewModels, repositories, player behavior and business ownership remain authoritative.
- Native Android View boundaries are preferred for the shell/Home/Settings/Search migration while the app remains heavily XML/ViewBinding based.
- Compose Liquid Glass primitives remain the reusable design-system foundation for future safe presentation boundaries.
- Liquid Glass is a functional floating hierarchy, not universal decoration or blur.
- Monochrome black/white/grey is the active redesign direction; theme-primary blue must not appear as an accidental navigation selection treatment.
- Home and Search content use standard/content-layer treatment; glass is reserved for functional floating controls, search affordances, suggestions/history interaction surfaces, and transient state surfaces.
- Current Apple guidance for Search supports a glass search field in a toolbar/top functional layer, explicit recent searches/suggestions, visible filtering, and a deliberate no-results state; ReelTide adopts those principles without copying proprietary Apple UI.

## VERIFICATION

- Current branch/ref reconstructed directly from GitHub; latest state checkpoint is `e784c9c54a181ff60379608da7dc0b7485e8258e`, with the latest code checkpoint immediately before state documentation at `834c1387ed2b49f03d92e74dcd61641715b99272`.
- Search architecture and owning state paths were inspected directly before changing presentation.
- Compare against the previous Home checkpoint confirms the Search change set is isolated to Search resources/layouts plus project state documentation; no Search business Kotlin was modified.
- GitHub Actions run #209 targets the Search code checkpoint `834c1387ed2b49f03d92e74dcd61641715b99272` and is currently in progress; checkout, JDK setup, Gradle setup and binary compatibility have completed successfully, while the Gradle step is still running. No green build is claimed.
- No device/runtime visual verification is claimed.

## NEXT RESUME ACTION

Check GitHub Actions run #209 to completion. If green, complete the Search empty/error observer integration, then perform the shell/inset/cast regression audit before moving to Details. If CI fails, diagnose the actual Gradle failure first.
