# ReelTide — Project State

**Last updated:** 2026-09-16
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current code checkpoint:** `f5c65f03398de94428d54e1513653f43e9464a90`

> Live execution checkpoint. Repository/code/build/test evidence outranks stale documentation or conversation memory.

## Current Phase

**Phase 5 — Home/Search presentation refinement**

## DONE

- Active branch remains based on known-good `master`; old `ui/2026-modern-redesign` is not reused.
- Shared Liquid Glass foundation and reusable presentation primitives exist.
- Direct Compose launch-shell NavPill integration is retired; stable native shell remains in place.
- Home hero presentation is incrementally reintroduced while preserving Home data/state/business ownership.
- Home hero target is **628dp** rather than the previous 728dp treatment.
- Persistent top branding/header container was removed from the Home hero.
- Profile remains the single persistent floating Liquid Glass control over the hero.
- Home search remains hidden as a compatibility anchor.
- Hero actions are left-aligned; Play is solid and bookmark/info use opaque solid dark circles.
- Home section title and `View all` are separate visual affordances; `View all` uses a restrained opaque dark surface.
- Home watch/bookmark containers no longer use one combined full-width glass background.
- Fresh-install theme defaults to `Amoled` + `White` without overwriting existing selections.
- Search filter chips use monochrome black/white/grey states instead of magenta.
- Checked search/filter chip text explicitly switches to black for contrast on the white selected state.
- Setup navigation shell is hidden during setup screens and restored when leaving setup; original setup Extensions flow was preserved.
- GitHub Actions build/check for `b00655cf7dc59168d2894cbd591878043a87863b` completed successfully (run `35034416615`).
- Home hero bounds were hardened with an explicit 628dp ViewPager height/minimum so nested RecyclerView/ViewPager measurement cannot collapse the hero frame and detach the action row.
- Home bottom artwork fade was reduced from 390dp to 300dp to improve title/metadata/synopsis readability.
- Home bookmark action no longer exposes the nullable `WatchType.NONE` label as visible `None`; the action remains icon-only while preserving the existing watch-state picker behavior.
- Home source selector now uses a dedicated `HomeSourceFab` presentation wrapper so the internal `noneApi` sentinel is displayed as the deliberate `Source` label rather than raw `None`; provider selection/callback ownership remains in `HomeFragment`.
- Corrected an intermediate duplicate XML attribute introduced while wiring `HomeSourceFab`; current `fragment_home.xml` is normalized.

## IN PROGRESS

- Home runtime refinement based on the latest user screenshots.
- Search visual/state cleanup.

## REMAINING

1. Verify the latest Home hero/source/bookmark changes on-device.
2. Finish Home poster/card spacing, section rhythm, profile treatment and navigation motion.
3. Audit Search empty/loading/error states and reduce unnecessary visual surfaces.
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

- Latest user screenshots show at least two Home runtime states: a loaded hero and a collapsed/blank hero state. The collapsed state remains the highest-priority runtime risk until verified on-device.
- The 628dp hero must be checked across compact phones/tablets/landscape.
- Setup lifecycle visibility must be checked for transient reappearance between setup destinations.
- Search checked-chip text contrast required an explicit fix because white selected backgrounds otherwise inherit light text.
- `HomeSourceFab` relies on TextView's `setText(CharSequence, BufferType)` override; CI must validate the custom view integration before it is treated as verified.

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
- Hero is 628dp with left-aligned title/metadata/synopsis hierarchy and a cinematic bottom fade.
- Section title and `View all` are separate visual affordances.
- Preserve existing navigation IDs, graph, menu, state, adapters, ViewModels, repositories, player behavior and business ownership.

## VERIFICATION

- User confirmed the stable diagnostic build launches successfully.
- Home refinement checkpoint `029bad1738c97158d23752ad5b5d0dc15509ee3b` passed GitHub Actions run #232.
- CloudStreamApp preference fix `b00655cf7dc59168d2894cbd591878043a87863b` passed GitHub Actions run `35034416615`.
- Home bounds/bookmark checkpoint commits `a550a758d20502742697cef36768a33f29c5e89e` and `097e4a6bca3fc856020928b924d0825ca37db3f5` are in the current branch history.
- Latest source selector commits are `e9d29add249615198bbe4c559e8b90976397b496` and `f5c65f03398de94428d54e1513653f43e9464a90`.
- GitHub Actions run `35082626449` (#267) is the current head verification run and was in progress at the last check.
- No ReelTide crash stacktrace has been captured from earlier logs.
- No device verification is claimed for the latest source changes until the user installs the corresponding build.

## NEXT ACTION

Poll the current head CI run. If green, continue the Home runtime polish and Search state cleanup; if it fails, diagnose the actual failure before proceeding.
