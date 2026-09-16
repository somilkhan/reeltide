# ReelTide — Project State

**Last updated:** 2026-09-17
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current code checkpoint:** `7f23769ad545ec2608fd895c0363714068715e7b`

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
- Hero action visibility is wired to the real hero ViewPager adapter item count through `HomeHeroActionBar`, preventing Play/Save/Info from remaining visible when the hero adapter is empty.
- The existing `HomeHeroActionBar` is now actually wired into `fragment_home_head.xml`; no duplicate hero-action implementation was added.
- Home section title and `View all` are separate visual affordances; `View all` uses a restrained opaque dark surface rather than Liquid Glass.
- Home watch/bookmark containers no longer use one combined full-width glass background.
- Fresh-install theme defaults to `Amoled` + `White` without overwriting existing selections.
- Search filter chips use monochrome black/white/grey states instead of magenta.
- Checked search/filter chip text explicitly switches to black for contrast on the white selected state.
- Setup navigation shell is hidden during setup screens and restored when leaving setup; original setup Extensions flow was preserved.
- Hero bottom artwork fade is **300dp**.
- Home hero paging now uses a restrained scale/alpha transition without padding-based black gutters during swipes.
- Home source-selector changes remain separate from hero action controls; the source FAB was not repurposed for hero behavior.

## IN PROGRESS

- Home runtime refinement against the latest target hero structure.
- Search visual/state cleanup.

## REMAINING

1. Verify latest Home hero/action/paging changes on-device.
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
- Hero paging must remain full-bleed; transitions use restrained depth rather than exposing background gutters.
- Section title and `View all` are separate visual affordances.
- Preserve existing navigation IDs, graph, menu, state, adapters, ViewModels, repositories, player behavior and business ownership.

## VERIFICATION

- User previously confirmed the stable diagnostic build launches successfully.
- Home refinement checkpoint `029bad1738c97158d23752ad5b5d0dc15509ee3b` passed GitHub Actions run #232.
- CloudStreamApp preference fix `b00655cf7dc59168d2894cbd591878043a87863b` passed GitHub Actions run `35034416615`.
- Hero action visibility compile checkpoint `743c005d52487082e8e200255c2ad9ffce619b86` passed GitHub Actions artifact build run #276.
- Head `63b6c6a7888f5a4d30a714a6b666caf40e7f7a15` started GitHub Actions Artifact Build run #279; it was still in progress when last polled.
- Hero header wiring, 300dp fade, and hero transformer changes are now on head `7f23769ad545ec2608fd895c0363714068715e7b`; no CI result for this latest head is claimed yet.
- No device verification is claimed for the latest Home changes.
- No ReelTide crash stacktrace has been captured from earlier logs.

## NEXT ACTION

Poll the active PR CI for the current branch head. If green, continue the Home visual/state audit and then Search state cleanup; if it fails, diagnose the actual failure before proceeding.
