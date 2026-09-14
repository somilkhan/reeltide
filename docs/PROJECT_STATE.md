# ReelTide — Project State

**Last updated:** 2026-09-14
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current head:** `30739997af6dcee03ccf15d6ff01dd4e3751e30f`

> Live execution checkpoint. Repository/code/build/test evidence outranks stale documentation or conversation memory.

## Current Phase

**Phase 3 → Phase 5 — Core presentation foundation, shell, Home and Settings replacement**

## COMPLETED / RECONSTRUCTED

- Active branch reconstructed directly from GitHub; current branch remains the Liquid Glass redesign branch based on `master`.
- Shared Liquid Glass Compose foundation exists, but risky direct Compose launch-shell integration is retired.
- Previous custom `LiquidGlassNavPillView` launch integration caused a user-reported launch regression and was removed; the shell remains a native View boundary.
- Phone navigation remains owned by the existing `BottomNavigationView`, menu and navigation graph, presented inside a floating glass shell.
- Settings presentation was replaced as a coherent XML/ViewBinding subsystem while preserving existing binding IDs and `SettingsFragment` navigation ownership.
- Navigation selected-state treatment is monochrome; the Material active indicator is transparent.
- Home phone hero presentation has now been rebuilt around the existing `ViewPager2`/`HomeScrollAdapter` architecture rather than replacing Home state/data logic.
- The legacy duplicate `REELTIDE / HOME` overlay in `fragment_home.xml` is retired; the hero owns its own compact top chrome.

## CHANGED IN CURRENT EXECUTION

- `app/src/main/res/layout/fragment_home_head.xml`
  - rebuilt phone hero chrome with a floating glass top bar
  - compact ReelTide wordmark
  - search/account controls retained under existing IDs
  - larger cinematic hero stage
  - stronger bottom scrim and tighter action hierarchy
  - preserved existing bookmark/play/info IDs and click ownership
- `app/src/main/res/layout/home_scroll_view.xml`
  - refined hero media treatment, scrims, title/logo hierarchy and metadata scale
  - preserved all adapter-bound IDs
- `app/src/main/res/layout/fragment_home.xml`
  - removed the visually conflicting legacy brand header
  - adjusted home content padding for the floating shell
  - preserved Home loading/error/FAB/navigation IDs
- `app/src/main/res/layout/activity_main.xml`
  - tightened the floating phone navigation surface to a more compact 68dp functional layer
  - retained the existing native navigation implementation

## IN PROGRESS

- GitHub Actions verification is running/queued for the latest Home/shell commits.
- No device/runtime verification is available in this execution environment.
- Home loading/error/empty states still need a matching presentation pass.
- Home provider/source control and random action still need integration-level visual verification.

## REMAINING

1. Verify latest Home/shell CI.
2. Complete Home loading/error/empty/provider states as one visual subsystem.
3. Verify floating navigation, back behavior, insets and cast mini-controller coexistence.
4. Replace Search presentation.
5. Replace Details presentation.
6. Replace Library and Downloads presentation.
7. Finish Settings sub-screens/account presentation consistently.
8. Audit player presentation without changing playback behavior.
9. Replace remaining secondary legacy Material surfaces.
10. Add bounded blur only where justified and measurable; do not fake backdrop blur with `Modifier.blur`.
11. Accessibility, reduced-motion, contrast, dynamic text and performance verification.
12. Functional + visual regression matrix.
13. Remove obsolete presentation bridges/dead code after stable replacements.

## BLOCKED

- Local Gradle execution is unavailable because outbound repository/network resolution is unavailable.
- No device/runtime automation is available in the current execution environment.
- GitHub Actions is the executable build authority.

## REGRESSIONS / KNOWN RISKS

- The prior Compose NavPill launch integration remains explicitly retired after the launch regression.
- Current Home/shell source changes are not device-verified.
- User-provided runtime screenshots showed the previous Home result below the intended quality bar; the current implementation specifically removes the duplicate legacy header and strengthens the hero/chrome hierarchy.
- True backdrop blur is not being faked; current materials use bounded translucency, borders, shadow and hierarchy.
- `home_brand_header` is retained only as a hidden compatibility ID; it has no visible presentation role.

## FAILED APPROACHES

- Wiring a custom Compose NavPill directly into the launch shell before device verification: rejected after launch regression.
- Treating small XML polish as sufficient redesign: rejected by runtime visual feedback.
- Source-only confidence: not accepted as verification.
- Search mutation using a stale blob SHA: rejected by GitHub; Search remains unchanged.

## DECISIONS

- Existing navigation IDs, graph, menu, state, adapters, ViewModels, repositories, player behavior and business ownership remain authoritative.
- Native Android View boundaries are preferred for the shell/Home/Settings migration while the app remains heavily XML/ViewBinding based.
- Compose Liquid Glass primitives remain the reusable design-system foundation for future safe presentation boundaries.
- Liquid Glass is a functional floating hierarchy, not universal decoration or blur.
- Monochrome black/white/grey is the active redesign direction; theme-primary blue must not appear as an accidental navigation selection treatment.
- Home presentation work should modify presentation XML and tightly scoped chrome only; HomeFragment/HomeViewModel data/state ownership remains unchanged unless a true interaction requirement requires otherwise.

## VERIFICATION

- Current branch/ref and affected source files inspected directly.
- Current head is `30739997af6dcee03ccf15d6ff01dd4e3751e30f`.
- GitHub Actions run `34843184116` for the Home-head checkpoint is still in progress at Gradle setup.
- GitHub Actions run `34843209254` for the latest shell checkpoint is queued.
- No successful current-head CI result is claimed.
- No device/runtime verification is claimed.

## NEXT RESUME ACTION

Wait/check the current GitHub Actions runs. If green, continue Home state surfaces and then perform shell/inset/cast regression inspection. If any run fails, diagnose that failure before adding further UI work.
