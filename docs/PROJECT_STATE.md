# ReelTide — Project State

**Last updated:** 2026-09-15
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current code checkpoint:** `b382501e0b415a7ba6383ba9d68bbfd9d235a454`

> Live execution checkpoint. Repository/code/build/test evidence outranks stale documentation or conversation memory.

## Current Phase

**Phase 5 — Screen-by-screen presentation replacement: Home/Search checkpoints under runtime isolation**

## DONE

- Active branch remains based on `master`; old `ui/2026-modern-redesign` is not reused.
- Shared Liquid Glass foundation and reusable presentation primitives exist.
- Direct Compose launch-shell NavPill integration was retired after a user-reported launch regression.
- Settings presentation was replaced while preserving SettingsFragment IDs and behavior.
- Search presentation and bounded empty/error state rendering were integrated while preserving SearchViewModel, adapters, history, suggestions, filters, intent handling and existing search/retry entry points.
- Search checkpoint `0cda31fdc5d15757c1a0dc52add1c92bf170865b` passed GitHub Actions run #217 (`34847250153`).
- `activity_main.xml` remains restored to the stable `master` shell for runtime isolation.
- `fragment_home.xml` remains restored to the stable `master` loading/state shell for runtime isolation.
- Home header/hero Liquid Glass checkpoint reintroduced incrementally without changing Home data/state/business ownership.
- Home hero now has no enclosing top toolbar; search and profile are individual floating controls over the hero.
- Home hero height was increased from the redesigned 560dp checkpoint to 728dp (~30%).
- Hero action group is left-aligned near the bottom while retaining the existing `home_preview_bookmark`, `home_preview_play`, and `home_preview_info` IDs.
- Home section headers now use a compact genre/title glass pill with a separate right-side `View all` treatment rather than one full-width glass bar.

## IN PROGRESS

- Verify GitHub Actions for the latest Home presentation commits and then use the resulting build for device/runtime verification.
- Continue Home visual refinement only after the current checkpoint is confirmed build-safe.

## REMAINING

1. Verify latest Home checkpoint in CI and on-device.
2. Finish the Home screen presentation: section rhythm, poster/card treatment, floating controls, navigation material and motion.
3. Replace Details presentation coherently.
4. Replace Library and Downloads presentation.
5. Finish Settings sub-screens/account presentation.
6. Audit player presentation without changing playback behavior.
7. Replace remaining secondary legacy Material surfaces.
8. Add bounded blur only where justified and measurable; do not fake backdrop blur with `Modifier.blur`.
9. Accessibility, reduced-motion, contrast, dynamic text and performance verification.
10. Functional + visual regression matrix and cleanup of obsolete presentation bridges/dead code.

## BLOCKED

- Local Gradle execution is unavailable because outbound repository/network resolution is unavailable.
- No device/runtime automation is available in the execution environment.
- GitHub Actions is the executable build authority.

## REGRESSIONS / KNOWN RISKS

- Direct Compose launch-shell NavPill remains retired.
- The launch shell is intentionally still the stable native `master` shell while navigation redesign is isolated separately.
- The earlier CI regression caused by `itemActiveIndicatorColor` remains fixed; that attribute must not be reintroduced.
- True backdrop blur is not being faked; current Home glass materials use bounded translucency, borders and shadows.
- `home_child_more_info` remains a TextView so existing ViewBinding/click behavior is preserved; the visual `View all` affordance is a separate adjacent view.
- The new 728dp hero should be checked across compact phones/tablets/landscape because it is intentionally taller.

## FAILED APPROACHES

- Direct Compose NavPill injection into the launch shell before device verification: rejected after launch regression.
- Broad multi-surface UI patching without runtime checkpoints: rejected.
- Source-only confidence without runtime evidence: rejected.
- `itemActiveIndicatorColor` navigation-shell attribute: removed after CI regression tracing.
- Repeated Termux-only logcat inspection: not useful for the ReelTide crash because the captured process was `com.termux`.

## DECISIONS

- Existing navigation IDs, graph, menu, state, adapters, ViewModels, repositories, player behavior and business ownership remain authoritative.
- Native Android View boundaries are preferred for shell/Home/Settings/Search migration while the app remains XML/ViewBinding-heavy.
- Compose Liquid Glass primitives remain the reusable design-system foundation for safe future presentation boundaries.
- Liquid Glass is a functional floating hierarchy, not universal decoration or blur.
- Monochrome black/white/grey remains the active redesign direction.
- Content uses standard/content-layer treatment; glass is reserved for functional floating controls and transient interaction/state surfaces.
- Home hero controls float directly over media instead of being enclosed in a persistent toolbar.
- Section genre/title and `View all` are separate visual affordances.

## VERIFICATION

- `activity_main.xml` diagnostic restore is known stable from the user's successful launch report.
- `fragment_home.xml` diagnostic restore is in place.
- Latest Home presentation code is committed at `b382501e0b415a7ba6383ba9d68bbfd9d235a454`.
- CI result for the latest commit has not yet been observed; no green result is claimed.
- No ReelTide crash stacktrace has been captured from the user's earlier logs.

## NEXT ACTION

Poll CI for the latest Home checkpoint. If green, use the build on-device and then continue the Home screen component/motion pass. If the build fails, fix the actual CI error before adding more UI.
