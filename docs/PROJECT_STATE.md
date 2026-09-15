# ReelTide — Project State

**Last updated:** 2026-09-16
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current code checkpoint:** `6b5f4322b634d7a9d1424884786d118ce3809716`

> Live execution checkpoint. Repository/code/build/test evidence outranks stale documentation or conversation memory.

## Current Phase

**Phase 5 — Home/Search presentation refinement**

## DONE

- Active branch remains based on known-good `master`; old `ui/2026-modern-redesign` is not reused.
- Shared Liquid Glass foundation and reusable presentation primitives exist.
- Direct Compose launch-shell NavPill integration is retired; stable native shell remains in place.
- Home hero presentation is incrementally reintroduced while preserving Home data/state/business ownership.
- Home hero is 728dp (~30% taller than the earlier redesigned checkpoint).
- Persistent top branding/header container was removed from the Home hero.
- Profile remains the single persistent floating Liquid Glass control over the hero.
- Home search remains hidden as a compatibility anchor.
- Hero actions are left-aligned; Play is solid and bookmark/info use opaque solid dark circles.
- Home section title uses compact glass treatment while `View all` is plain.
- Home watch/bookmark containers no longer use one combined full-width glass background.
- Fresh-install theme defaults to `Amoled` + `White` without overwriting existing selections.
- Search filter chips use monochrome black/white/grey states instead of magenta.
- Checked search/filter chip text now explicitly switches to black for contrast on the white selected state.
- Setup navigation shell is hidden during setup screens and restored when leaving setup; original setup Extensions flow was preserved.
- GitHub Actions build/check for `b00655cf7dc59168d2894cbd591878043a87863b` completed successfully (run `35034416615`).

## IN PROGRESS

- Search visual/state cleanup.
- Home visual/motion refinement based on current on-device screenshots.

## REMAINING

1. Verify the new chip contrast fix on-device.
2. Finish Home poster/card spacing, section rhythm, profile treatment and navigation motion.
3. Audit Search empty/loading/error states and reduce unnecessary glass surfaces.
4. Replace Details presentation coherently.
5. Replace Library and Downloads presentation.
6. Finish Settings sub-screens/account presentation.
7. Audit player presentation without changing playback behavior.
8. Replace remaining secondary legacy Material surfaces.
9. Add bounded blur only where justified; do not fake backdrop blur with `Modifier.blur`.
10. Accessibility, reduced-motion, contrast, dynamic text and performance verification.
11. Functional + visual regression matrix and obsolete presentation cleanup.

## BLOCKED

- Local Gradle execution is unavailable because outbound repository/network resolution is unavailable.
- No device/runtime automation is available in the execution environment.
- GitHub Actions is the executable build authority.

## REGRESSIONS / KNOWN RISKS

- The screenshots supplied by the user appear to include different build states: the Search screenshot still shows the old magenta chip treatment while the current source is monochrome, and the Home screenshots differ in profile visibility. Do not treat those visual differences as one exact build state.
- The 728dp hero must be checked across compact phones/tablets/landscape.
- Setup lifecycle visibility must be checked for transient reappearance between setup destinations.
- Search checked-chip text contrast required an explicit fix because white selected backgrounds otherwise inherit light text.

## FAILED APPROACHES

- Direct Compose NavPill injection into the launch shell before device verification: rejected after launch regression.
- Broad multi-surface UI patching without runtime checkpoints: rejected.
- Source-only confidence without runtime evidence: rejected.
- Excessive Liquid Glass treatment: rejected; target is restrained, approximately 25% or less of visible UI surfaces.
- Rewriting setup extension flow while adding visibility hooks: caught by source comparison and restored to master behavior.

## DESIGN DECISIONS

- Liquid Glass is a functional floating hierarchy, not universal decoration.
- Keep Liquid Glass restrained to high-value floating controls/navigation/selected section affordances; content remains dominant.
- Monochrome black/white/grey remains the active redesign direction.
- Home profile is the primary floating glass control; hero action buttons are solid.
- Section genre/title and `View all` are separate visual affordances.
- Preserve existing navigation IDs, graph, menu, state, adapters, ViewModels, repositories, player behavior and business ownership.

## VERIFICATION

- User confirmed the stable diagnostic build launches successfully.
- Home refinement checkpoint `029bad1738c97158d23752ad5b5d0dc15509ee3b` passed GitHub Actions run #232.
- CloudStreamApp preference fix `b00655cf7dc59168d2894cbd591878043a87863b` passed GitHub Actions run `35034416615`.
- No ReelTide crash stacktrace has been captured from earlier logs.
- No device verification is claimed for the latest source changes until the user installs the corresponding build.

## NEXT ACTION

Continue the Search cleanup: keep the search field/actions as the limited glass layer, remove unnecessary glass from transient state/suggestion surfaces, preserve search behavior, then run CI and inspect for related regressions before moving to the next screen.
