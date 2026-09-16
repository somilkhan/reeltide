# ReelTide — Project State

**Last updated:** 2026-09-17
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current code checkpoint:** `1d65bbc91529ea3ddb6a5062b869ba23818c4dc0`

> Live execution checkpoint. Repository/code/build/test evidence outranks stale documentation or conversation memory.

## Current Phase

**Phase 5 — Home/Search presentation refinement**

## DONE

- Active branch remains based on known-good `master`; old `ui/2026-modern-redesign` is not reused.
- Shared Liquid Glass foundation and reusable presentation primitives exist.
- Direct Compose launch-shell NavPill integration is retired; stable native shell remains in place.
- Home hero presentation is incrementally reintroduced while preserving Home data/state/business ownership.
- Home hero target is **628dp**.
- Persistent top branding/header container was removed from the Home hero.
- Profile remains the single persistent floating Liquid Glass control over the hero.
- Home search remains hidden as a compatibility anchor.
- Hero actions are left-aligned; Play is solid and bookmark/info use opaque solid dark circles.
- Hero action visibility is wired to the real hero ViewPager adapter item count through `HomeHeroActionBar`.
- The existing `HomeHeroActionBar` is actually wired into `fragment_home_head.xml`; no duplicate hero-action implementation was added.
- Home section title and `View all` are separate visual affordances; `View all` uses an opaque dark surface rather than Liquid Glass.
- Home watch/bookmark containers no longer use one combined full-width glass background.
- Fresh-install theme defaults to `Amoled` + `White` without overwriting existing selections.
- Search filter chips use monochrome black/white/grey states instead of magenta.
- Checked search/filter chip text explicitly switches to black for contrast on the white selected state.
- Setup navigation shell is hidden during setup screens and restored when leaving setup; original setup Extensions flow was preserved.
- Hero bottom artwork fade is **300dp**.
- Hero paging no longer scales pages down, preventing black gutters around the full-bleed artwork during swipes.
- Phone hero metadata was corrected to avoid the empty standalone `•` seen when a title has no year. The redesigned phone hero now presents year/duration compactly and does not surface the old rating-first treatment.
- Hero synopsis is limited to two lines and slightly increased in contrast for readability.
- Home Source FAB now self-hides when there are no selectable providers, preventing an orphaned floating source/filter control from covering the content rail. Its existing provider-selection behavior remains intact when providers exist.

## IN PROGRESS

- Home runtime refinement against the supplied target screenshot.
- Search visual/state cleanup.

## REMAINING

1. Verify latest Home hero/action/paging/Source behavior on-device.
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

- The previously observed collapsed/blank Home hero state remains a device-runtime case that must be checked on-device.
- `HomeHeroActionBar` adapter-observer lifecycle must be covered by CI and on-device verification.
- The 628dp hero must be checked across compact phones/tablets/landscape.
- Setup lifecycle visibility must be checked for transient reappearance between setup destinations.
- Search checked-chip text contrast required an explicit fix because white selected backgrounds otherwise inherit light text.
- The Source FAB visibility guard calls provider filtering from the custom view; this needs CI/device verification against plugin install/uninstall lifecycle.

## FAILED APPROACHES

- Direct Compose NavPill injection into the launch shell before device verification: rejected after launch regression.
- Broad multi-surface UI patching without runtime checkpoints: rejected.
- Source-only confidence without runtime evidence: rejected.
- Excessive Liquid Glass treatment: rejected; target is restrained, approximately 25% or less of visible UI surfaces.
- Rewriting setup extension flow while adding visibility hooks: caught by source comparison and restored to master behavior.
- Leaving `HomeHeroActionBar` as dead code while using a native `LinearLayout`: corrected by wiring the existing presentation component into the actual hero layout.
- Padding-based hero page transformation: replaced because it exposed black gutters and weakened the full-bleed composition during swipes.

## DESIGN DECISIONS

- Liquid Glass is a functional floating hierarchy, not universal decoration.
- Keep Liquid Glass restrained to high-value floating controls/navigation; content remains dominant.
- Monochrome black/white/grey remains the active redesign direction.
- Home profile is the primary floating glass control; hero action buttons are solid.
- Hero is **628dp** with left-aligned title/metadata/synopsis hierarchy and a cinematic **300dp** bottom fade.
- Hero structure is: full-bleed artwork → floating profile → title → year/metadata → short synopsis → Play/Save/Info actions → next content section.
- Hero paging must remain full-bleed; transitions use restrained alpha depth rather than scaling the artwork.
- Section title and `View all` are separate visual affordances.
- Preserve existing navigation IDs, graph, menu, state, adapters, ViewModels, repositories, player behavior and business ownership.

## VERIFICATION

- User previously confirmed the stable diagnostic build launches successfully.
- Home refinement checkpoint `029bad1738c97158d23752ad5b5d0dc15509ee3b` passed GitHub Actions run #232.
- CloudStreamApp preference fix `b00655cf7dc59168d2894cbd591878043a87863b` passed GitHub Actions run `35034416615`.
- Hero action visibility compile checkpoint `743c005d52487082e8e200255c2ad9ffce619b86` passed GitHub Actions artifact build run #276.
- The latest screenshot was inspected for Home hero/content overlap. It showed the intended loaded hero composition, plus two concrete defects addressed here: the orphaned Source FAB overlapping the card rail and the standalone metadata bullet.
- Commits `a971df7`, `65c53bc`, `fb64753`, and `1d65bbc` contain the current Home fixes.
- No device verification is claimed for the latest changes.
- No ReelTide crash stacktrace has been captured from earlier logs.

## NEXT ACTION

Poll CI for the current head `1d65bbc91529ea3ddb6a5062b869ba23818c4dc0`. If green, continue the Home screen as a complete subsystem: card sizing/spacing, section rhythm, profile treatment, nav interaction and loading/empty/error states before moving to Search.
