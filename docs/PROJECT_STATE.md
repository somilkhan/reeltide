# ReelTide — Project State

**Last updated:** 2026-09-14
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current code checkpoint:** `0cda31fdc5d15757c1a0dc52add1c92bf170865b`

> Live execution checkpoint. Repository/code/build/test evidence outranks stale documentation or conversation memory.

## Current Phase

**Phase 5 — Screen-by-screen presentation replacement: Home complete checkpoint, Search presentation checkpoint in verification**

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

- Search presentation remains a coherent phone subsystem:
  - floating glass search field;
  - separate glass voice and filter controls;
  - monochrome media-type filter chips;
  - glass suggestion surface and interaction rows;
  - refined recent-search rows;
  - refined phone and expanded search result cards;
  - preserved existing SearchView, RecyclerView, adapter, history, suggestion, filter and focus contracts.
- `fragment_search.xml` now owns a bounded Liquid Glass empty/error state surface; it does not duplicate business state ownership.
- `SearchFragment.kt` now renders that presentation from the existing `searchResponse` and `currentSearch` observers:
  - empty direct-search results -> no-results surface;
  - advanced provider-search aggregate empty -> no-results surface;
  - direct-search failure -> retry surface;
  - loading/new query/history -> state surface hidden;
  - retry calls the existing `search(query)` path.
- Added `liquid_glass_search_strings.xml` for state copy.
- The previous unused empty/error prototype was removed before this integrated implementation; the current state is now wired to the real observer paths.
- `tvtypes_chips.xml`, `search_history_item.xml`, `search_suggestion_item.xml`, `search_result_grid.xml`, and `search_result_grid_expanded.xml` remain presentation-only changes.

## IN PROGRESS

- GitHub Actions verification of code checkpoint `0cda31fdc5d15757c1a0dc52add1c92bf170865b` (run #217) is in progress.
- Device/runtime visual verification remains unavailable in this execution environment.
- Shell/inset/cast coexistence still needs device verification.

## REMAINING

1. Finish CI verification for the integrated Search checkpoint and diagnose any actual Gradle failure.
2. If CI is green, perform shell/inset/cast regression audit.
3. Replace Details presentation as one coherent subsystem.
4. Replace Library and Downloads presentation.
5. Finish Settings sub-screens/account presentation consistently.
6. Audit player presentation without changing playback behavior.
7. Replace remaining secondary legacy Material surfaces.
8. Add bounded blur only where justified and measurable; do not fake backdrop blur with `Modifier.blur`.
9. Accessibility, reduced-motion, contrast, dynamic text and performance verification.
10. Functional + visual regression matrix.
11. Remove obsolete presentation bridges/dead code after stable replacements.

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
- Search business logic, provider selection, history, suggestions, intent handling, navigation and adapter ownership remain authoritative; the current Kotlin changes only add presentation-state rendering around existing observer outputs and reuse the existing search/retry entry point.
- The shell currently relies on the existing `fixSystemBarsPadding(navView, ...)` path; exact floating-surface/inset geometry still requires device verification, especially across gesture and three-button navigation modes.

## FAILED APPROACHES

- Wiring a custom Compose NavPill directly into the launch shell before device verification: rejected after launch regression.
- Treating small XML polish as sufficient redesign: rejected by runtime visual feedback.
- Source-only confidence: not accepted as verification.
- Search mutation using stale blob SHAs: rejected by GitHub; later Search changes were applied against current blob SHAs.
- `itemActiveIndicatorColor` in the phone navigation shell: removed after CI regression tracing; native tint selector is retained for monochrome navigation without relying on that attribute.
- Leaving empty/error UI unintegrated: rejected; the state surface is now owned by `SearchFragment` observers and uses the existing `search(query)` retry path.

## DECISIONS

- Existing navigation IDs, graph, menu, state, adapters, ViewModels, repositories, player behavior and business ownership remain authoritative.
- Native Android View boundaries are preferred for the shell/Home/Settings/Search migration while the app remains heavily XML/ViewBinding based.
- Compose Liquid Glass primitives remain the reusable design-system foundation for future safe presentation boundaries.
- Liquid Glass is a functional floating hierarchy, not universal decoration or blur.
- Monochrome black/white/grey is the active redesign direction; theme-primary blue must not appear as an accidental navigation selection treatment.
- Home and Search content use standard/content-layer treatment; glass is reserved for functional floating controls, search affordances, suggestions/history interaction surfaces, and transient state surfaces.

## VERIFICATION

- Current code checkpoint reconstructed directly from GitHub at `0cda31fdc5d15757c1a0dc52add1c92bf170865b`.
- Search architecture and owning state paths were inspected directly before changing presentation/state rendering.
- The previous CI failure at run #213 was followed by commit `b2112ce...` fixing the include-layout lint contract; subsequent run #214 was still in progress when this checkpoint continued.
- Run #217 targets the integrated Search checkpoint `0cda31fdc5d15757c1a0dc52add1c92bf170865b`; at the last poll it was still in progress. No green build is claimed.
- No device/runtime visual verification is claimed.

## NEXT RESUME ACTION

Check run #217 to completion. If green, inspect shell/inset/cast coexistence and then move to Details as the next coherent screen subsystem. If CI fails, diagnose the actual failure before making further UI changes.
