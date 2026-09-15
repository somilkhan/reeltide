# ReelTide — Baseline Architecture Audit

**Branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Audit date:** 2026-09-13
**Status:** Phase 0 in progress

## Verified repository shape

- Android application module: `app`
- Additional modules: `shared`, `library`, `docs`, `desktopApp`
- `app` uses Kotlin/Gradle Kotlin DSL and Jetpack Compose dependencies alongside an established View/XML presentation stack.
- Navigation is currently AndroidX Navigation with a `NavHostFragment` and XML navigation graph.
- Main app shell is currently ViewBinding/XML-based through `ActivityMainBinding`.
- Main navigation currently uses Material `BottomNavigationView` on phone and `NavigationRailView` on larger layouts.
- The navigation menu currently exposes Home, Search, Library, Downloads and Settings.
- Home is currently `HomeFragment` with `FragmentHomeBinding` and a RecyclerView-driven content presentation.
- Home loading/error states and several actions are currently represented with Material widgets and XML layouts.
- Existing application logic is substantial and centralized around `MainActivity`, fragments, ViewModels, repositories, utilities and navigation; presentation replacement must not duplicate or silently replace these behavioral owners.

## Current presentation architecture

### App shell

`app/src/main/res/layout/activity_main.xml`

Current shell contains:

- `NavigationRailView`
- `BottomNavigationView`
- `NavHostFragment` container
- Cast mini-controller holder

This is the primary replacement boundary for the new presentation shell.

### Navigation

`app/src/main/res/navigation/mobile_navigation.xml`

The graph contains the primary destinations plus many secondary settings, playback, subtitle, download and web destinations. The five primary destinations are wired through `bottom_nav_menu.xml`.

The redesign must preserve route IDs and existing navigation semantics unless a deliberate architecture change is required and verified.

### Home

`HomeFragment.kt` is a large stateful presentation controller. It owns or coordinates:

- homepage provider selection;
- media-type filtering;
- expanded homepage lists;
- SearchAdapter-backed content lists;
- bottom sheets/dialogs;
- account/search/reload actions;
- TV/phone layout differences;
- loading/error handling;
- navigation callbacks.

Therefore Home cannot safely be replaced by changing only its XML. The future redesign needs a presentation boundary that preserves these behaviors while progressively replacing visual components.

`fragment_home.xml` currently contains legacy Material widgets, RecyclerView content, shimmer loading, error actions and floating action buttons. This is a major presentation replacement target.

## Build / verification configuration

The repository already has GitHub Actions for pull-request builds and instrumented tests. The PR build workflow runs:

- `./gradlew library:checkKotlinAbi`
- `./gradlew assemblePrereleaseDebug lint check`

The local environment available to this agent could not clone the public Git repository because outbound DNS/network access was unavailable. Therefore no local Gradle build is claimed from this session. GitHub repository/workflow configuration has been inspected, but build success remains **unverified** until an executable build environment or CI result is available.

## Architectural conclusions

1. The application is not a greenfield Compose app; it is a mature Android application with XML/ViewBinding/Fragments/AndroidX Navigation plus Compose dependencies.
2. The redesign should therefore use a controlled migration/replacement boundary rather than assuming all screens can immediately become Compose-only.
3. Existing navigation IDs, ViewModels, repositories, player behavior, persistence and business logic are protected interfaces.
4. The first implementation subsystem should be the new app presentation shell/navigation material layer, but only after its state and route contract are mapped completely.
5. Home is a high-value next target because it is visually central and currently exposes the strongest legacy presentation surface.
6. NavPill should not be patched into the existing Material navigation widgets; it should be implemented as a coherent new presentation component while binding to the existing navigation contract.

## Unverified items

- Exact current Gradle build result on the active branch.
- Complete screen-by-screen inventory.
- Complete ViewModel/state ownership map.
- Device-level visual verification.
- Performance measurements.
- Accessibility runtime verification.

These remain Phase 0 work, not DONE claims.
