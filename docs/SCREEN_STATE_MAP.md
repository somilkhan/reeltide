# ReelTide — Screen / State Ownership Map

**Branch:** `ui/2026-liquid-glass-redesign`
**Audit date:** 2026-09-13
**Purpose:** Phase 0/1 implementation boundary. This document records verified presentation/state ownership so the redesign can replace visuals without moving business state accidentally.

## Global shell

| Area | Current owner | Protected contract |
|---|---|---|
| Activity shell | `MainActivity` + `ActivityMainBinding` | lifecycle, global events, intents, navigation setup, account/plugin initialization, cast integration |
| Primary navigation | `MainActivity` + AndroidX `NavController` + `bottom_nav_menu.xml` | destination IDs, back stack, restore-state behavior, secondary-route grouping |
| Navigation visibility/selection | `MainActivity.updateNavBar()` | phone vs landscape/TV presentation rules and selected top-level destination |
| Navigation click semantics | `MainActivity.onNavDestinationSelected()` | 400 ms debounce, singleTop, restoreState, start-destination popUpTo, TV focus behavior |
| Cast mini-controller | `MyMiniControllerFragment` hosted by `cast_mini_controller_holder` | playback/cast behavior and visibility rules |

## Primary destinations

### Home

- Route: `navigation_home`
- Fragment: `ui/home/HomeFragment`
- View state: `FragmentHomeBinding`
- Main state owner: `HomeViewModel`
- Shared dependencies: `SearchAdapter`, `APIRepository`, `AccountViewModel`, `MainActivity` events/utilities
- Verified responsibilities: provider selection, media-type filtering, expandable home lists, content rails, sheets/dialogs, account/search/reload actions, loading/error states, phone/TV layout differences.
- Redesign boundary: replace presentation components while preserving `HomeViewModel`, callbacks, navigation, adapter/data contracts and async states.

### Search

- Route: `navigation_search`
- Fragment: `ui/search/SearchFragment`
- View state: `FragmentSearchBinding`
- Main state owner: activity-scoped `SearchViewModel`
- Related state: search history/suggestions, provider/media filters, `HomeViewModel` for reused expandable list behavior.
- Protected behavior: keyboard/search input, provider filtering, result pagination/expansion, history, suggestions, errors and navigation to results.

### Library

- Route: `navigation_library`
- Fragment: `ui/library/LibraryFragment`
- Main state owner: activity-scoped `LibraryViewModel`
- State: selected sync API, current page, loaded library pages, sorting method, persisted last API/sorting state.
- Protected behavior: sync-provider selection, sorting, reload events and account-dependent data.

### Downloads

- Route: `navigation_downloads`
- Fragment: `ui/download/DownloadFragment`
- Main state owners: activity-scoped `DownloadViewModel` and `DownloadQueueViewModel`
- Protected behavior: download cards/list state, queue, delete actions, offline playback entry, child-folder navigation and player navigation.

### Settings

- Route: `navigation_settings`
- Fragment: `ui/settings/SettingsFragment`
- State is distributed through preference infrastructure and settings-specific fragments/utilities rather than one replacement ViewModel.
- Secondary settings routes include general, player, UI, account, providers, updates, extensions/plugins and subtitle-related destinations.
- Protected behavior: preference persistence, account actions, plugin/provider management and navigation into settings subgraphs.

## Secondary navigation groups

| Group | Routes / examples | Primary behavioral owner |
|---|---|---|
| Results/details | `navigation_results_phone`, `navigation_results_tv` | result fragments + `ResultViewModel2` / `SyncViewModel` where applicable |
| Playback | `navigation_player` | player stack (`GeneratorPlayer`, link generation, playback helpers) + player UI state |
| Subtitles | `navigation_subtitles`, `navigation_chrome_subtitles` | subtitle fragments/helpers |
| Downloads | `navigation_download_child`, `navigation_download_queue` | download + queue ViewModels |
| Settings | general/player/UI/account/providers/updates/extensions/plugins | settings fragments + preference/data-store infrastructure |
| Web | `navigation_webview` | `WebviewFragment` |
| Quick search | `navigation_quick_search` | search/home integration |

## Navigation state rules to preserve

1. Primary destination IDs remain unchanged.
2. Existing `MainActivity.onNavDestinationSelected()` remains the behavioral authority until a replacement navigation implementation is proven equivalent.
3. Top-level selected state must map secondary routes back to Downloads or Settings exactly as `updateNavBar()` currently does.
4. Primary navigation retains `launchSingleTop`, `restoreState`, start-destination `popUpTo` with saved state, and existing enter/exit behavior until the new motion system replaces those transitions deliberately.
5. Existing long-press navigation actions scroll the associated content to the top; the new navigation surface must preserve them.
6. TV/EMULATOR navigation rail remains a separate presentation path; the phone Liquid Glass pill must not remove TV focus/navigation behavior.

## Replacement boundary

The first safe replacement boundary is the **phone primary navigation presentation layer**:

`existing NavController + MainActivity navigation listener + existing menu IDs`

→ `LiquidGlassNavPillView (Compose presentation)`

No business/data ViewModel is moved into the new component. The component observes the existing `NavController` and delegates selection to the existing `BottomNavigationView` contract so the first implementation does not duplicate navigation semantics.

## Known limitations of this audit

- Full route-by-route ViewModel ownership for every secondary fragment still needs completion.
- Runtime/device behavior is not yet verified.
- Local Gradle build remains unavailable in the current environment; CI evidence is therefore still required before implementation claims are treated as build-verified.
