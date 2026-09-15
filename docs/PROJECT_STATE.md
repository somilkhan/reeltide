# ReelTide — Project State

**Last updated:** 2026-09-16
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current code checkpoint:** `ed729f2924e2d7f5debbeee2ef7fe2623bfd4b3c`

> Live execution checkpoint. Repository/code/build/test evidence outranks stale documentation or conversation memory.

## Current Phase

**Phase 5 — Home presentation refinement under runtime isolation**

## DONE

- Active branch remains based on the known-good `master` merge-base; old `ui/2026-modern-redesign` is not reused.
- Shared Liquid Glass foundation and reusable presentation primitives exist.
- Direct Compose launch-shell NavPill integration was retired after a user-reported launch regression.
- Settings and Search presentation checkpoints were integrated while preserving existing behavior and state ownership.
- `activity_main.xml` remains the stable native shell with the Liquid Glass NavPill surface around the existing `BottomNavigationView`.
- Home hero presentation is incrementally reintroduced while preserving Home data/state/business ownership.
- Home hero is 728dp (~30% taller than the earlier redesigned checkpoint).
- Persistent top branding/header container was removed from the Home hero.
- Profile remains the single persistent floating Liquid Glass control over the hero.
- Home search remains as a hidden legacy behavior anchor rather than a visible hero control.
- Hero actions remain left-aligned; Play is solid and bookmark/info use opaque solid dark circles instead of Liquid Glass.
- Home section title/genre uses a compact glass treatment while `View all` is a separate plain right-side affordance.
- Home watch/bookmark section containers no longer use one combined full-width glass background.
- Fresh-install theme initialization defaults to `Amoled` + `White` without overwriting existing user selections.
- Search filter chips were converted from the prior magenta treatment to monochrome black/white/grey states.
- Setup navigation-shell visibility is now explicitly hidden during language, extensions, provider-language, media, and layout setup screens and restored when leaving setup.
- Setup Extensions behavior was preserved from the master implementation while adding only the shell-visibility lifecycle hooks.

## IN PROGRESS

- Verify the latest setup-navigation checkpoint in GitHub Actions.
- Continue Home visual/motion refinement only after build safety is confirmed.

## REMAINING

1. Verify latest setup-navigation checkpoint in CI and on-device.
2. Finish Home poster/card spacing, section rhythm, floating profile treatment and navigation material/motion.
3. Audit Search empty/loading/error states and reduce any unnecessary glass surfaces.
4. Replace Details presentation coherently.
5. Replace Library and Downloads presentation.
6. Finish Settings sub-screens/account presentation.
7. Audit player presentation without changing playback behavior.
8. Replace remaining secondary legacy Material surfaces.
9. Add bounded blur only where justified and measurable; do not fake backdrop blur with `Modifier.blur`.
10. Accessibility, reduced-motion, contrast, dynamic text and performance verification.
11. Functional + visual regression matrix and obsolete presentation cleanup.

## BLOCKED

- Local Gradle execution is unavailable because outbound repository/network resolution is unavailable.
- No device/runtime automation is available in the execution environment.
- GitHub Actions is the executable build authority.

## REGRESSIONS / KNOWN RISKS

- Direct Compose launch-shell NavPill remains retired.
- The launch shell intentionally remains the stable native shell while navigation redesign is isolated separately.
- The earlier CI regression caused by `itemActiveIndicatorColor` remains fixed; do not reintroduce it.
- True backdrop blur is not being faked; current glass uses bounded translucency, borders and shadows.
- `home_search` is retained as a hidden compatibility anchor to avoid breaking existing ViewBinding/behavior references.
- The 728dp hero must be checked across compact phones/tablets/landscape.
- Setup visibility currently uses fragment lifecycle hooks; verify there is no transient reappearance when moving between setup destinations.

## FAILED APPROACHES

- Direct Compose NavPill injection into the launch shell before device verification: rejected after launch regression.
- Broad multi-surface UI patching without runtime checkpoints: rejected.
- Source-only confidence without runtime evidence: rejected.
- Excessive Liquid Glass treatment: rejected; target is restrained, approximately 25% or less of visible UI surfaces.
- Rewriting setup extension flow while adding visibility hooks: caught by source comparison and restored to master behavior before proceeding.

## DESIGN DECISIONS

- Liquid Glass is a functional floating hierarchy, not universal decoration.
- Keep Liquid Glass usage restrained to high-value floating controls/navigation/selected section affordances; content remains dominant.
- Monochrome black/white/grey remains the active redesign direction.
- Home profile is the primary floating glass control; hero action buttons are intentionally solid.
- Section genre/title and `View all` are separate visual affordances.
- Preserve existing navigation IDs, graph, menu, state, adapters, ViewModels, repositories, player behavior and business ownership.

## VERIFICATION

- User confirmed the stable diagnostic build launches successfully.
- Home refinement checkpoint `029bad1738c97158d23752ad5b5d0dc15509ee3b` passed GitHub Actions run #232.
- Setup-navigation checkpoint `ed729f2924e2d7f5debbeee2ef7fe2623bfd4b3c` is pushed; no workflow run has appeared yet, so no green result is claimed.
- No ReelTide crash stacktrace has been captured from earlier logs.

## NEXT ACTION

Poll CI for `ed729f2924e2d7f5debbeee2ef7fe2623bfd4b3c`. If green, continue the Home/Search refinement pass. If CI fails, fix the actual CI error before adding more UI.
