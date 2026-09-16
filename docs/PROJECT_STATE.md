# ReelTide — Project State

**Last updated:** 2026-09-17
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current code checkpoint:** `90623bda0fb98383e9f795b389fa72fd59e0f711`

> Live execution checkpoint. Repository/code/build/test evidence outranks stale documentation or conversation memory.

## Current Phase

**Phase 5 — Home/Search presentation refinement**

## DONE

- Active branch remains based on known-good `master`; old `ui/2026-modern-redesign` is not reused.
- Shared Liquid Glass foundation and reusable presentation primitives exist.
- Direct Compose launch-shell NavPill integration is retired; stable native shell remains in place.
- Home hero target is **628dp** with full-bleed artwork and a cinematic bottom fade.
- Persistent top branding/header container was removed from the Home hero.
- Profile remains the single persistent floating Liquid Glass control over the hero.
- Home search remains hidden as a compatibility anchor.
- Hero controls are now intentionally limited to **Play + Details**. The hero save/bookmark action is removed from the visible action layer; save will be handled by the Details model.
- Play is an icon-only solid white circular primary action. Details is a compact dark solid pill with an info icon.
- Hero controls remain left-aligned and use opaque controls rather than extra glass surfaces.
- `HomeHeroActionBar` is wired into the actual hero layout and hides the legacy save control before the first item bind.
- Empty successful hero responses now collapse the entire hero shell instead of leaving a blank 628dp region with controls.
- Home section title and `View All` are separate visual affordances; `View All` is plain transparent text+arrow and remains wired to `moreInfoClickCallback`.
- Home Source FAB was **not removed**. Its existing source/filter icon and picker behavior remain intact; only its visual treatment was corrected to a compact, restrained floating glass control with proper spacing above the bottom navigation layer.
- Source FAB still hides when there are no selectable providers, preventing an orphaned control from covering content.
- Home poster/hero metadata cleanup remains: no empty standalone metadata bullet, compact year/duration hierarchy, and two-line synopsis.
- Hero paging no longer scales pages down, preventing black gutters during full-bleed swipes.
- Search filter chips use monochrome black/white/grey states instead of magenta.
- Setup navigation lifecycle changes preserve the original setup Extensions/provider/media behavior.

## IN PROGRESS

- Home runtime refinement against the supplied target screenshot.
- CI verification of the latest hero/source changes.
- Search visual/state cleanup.

## REMAINING

1. Verify latest Home hero/action/Source/empty-state behavior on-device.
2. Finish Home poster/card spacing, section rhythm, profile treatment and navigation motion.
3. Audit Home loading/error transitions and compact-width behavior.
4. Finish Search empty/loading/error states and reduce unnecessary visual surfaces.
5. Replace Details presentation coherently.
6. Replace Library and Downloads presentation.
7. Finish Settings sub-screens/account presentation.
8. Audit player presentation without changing playback behavior.
9. Replace remaining secondary legacy Material surfaces.
10. Add bounded blur only where justified; do not fake backdrop blur with generic blur.
11. Accessibility, reduced-motion, contrast, dynamic text and performance verification.
12. Functional + visual regression matrix and obsolete presentation cleanup.

## BLOCKED

- Local Gradle execution is unavailable because outbound repository/network resolution is unavailable.
- No device/runtime automation is available in the execution environment.
- GitHub Actions is the executable build authority.

## REGRESSIONS / KNOWN RISKS

- The 628dp hero must be checked across compact phones/tablets/landscape.
- Hero action observer lifecycle must be covered by CI and on-device verification.
- Source FAB provider visibility needs verification against plugin install/uninstall lifecycle.
- The hero action layer still uses the existing binding IDs for compatibility, but save is deliberately hidden.
- No latest device verification is claimed.

## FAILED APPROACHES

- Direct Compose NavPill injection into the launch shell before device verification: rejected after launch regression.
- Broad multi-surface UI patching without runtime checkpoints: rejected.
- Excessive Liquid Glass treatment: rejected; target is restrained, approximately 25% or less of visible UI surfaces.
- Treating the Source FAB as something to remove: corrected; it is retained and its presentation is being refined instead.
- Keeping save/bookmark in the Home hero: corrected; save belongs in Details.
- Leaving an empty successful hero shell visible: corrected by collapsing the hero container when the real preview list is empty.

## DESIGN DECISIONS

- Liquid Glass is a functional floating hierarchy, not universal decoration.
- Keep Liquid Glass restrained to high-value floating controls/navigation; content remains dominant.
- Monochrome black/white/grey remains the active redesign direction.
- Home profile and Source are floating controls; hero Play/Details are solid content actions.
- Hero is **628dp** with left-aligned title/metadata/synopsis hierarchy and a cinematic bottom fade.
- Hero structure is: full-bleed artwork → floating profile → title → year/metadata → short synopsis → Play + Details → next content section.
- Preserve existing navigation IDs, graph, menu, state, adapters, ViewModels, repositories, player behavior and business ownership.

## VERIFICATION

- User previously confirmed the stable diagnostic build launches successfully.
- CloudStreamApp preference fix `b00655cf7dc59168d2894cbd591878043a87863b` passed GitHub Actions run `35034416615`.
- Hero action visibility compile checkpoint `743c005d52487082e8e200255c2ad9ffce619b86` passed GitHub Actions artifact build run #276.
- Current latest head `90623bda0fb98383e9f795b389fa72fd59e0f711` has a GitHub Actions run in progress; no green result is claimed yet.
- No device verification is claimed for the latest changes.
- No ReelTide crash stacktrace has been captured from earlier logs.

## NEXT ACTION

Poll CI for the current head. If green, continue the Home subsystem audit for card sizing/spacing, section rhythm, profile treatment, navigation motion and loading/empty/error states before moving to Search. If it fails, inspect the actual failing job/log and fix the root cause before proceeding.
