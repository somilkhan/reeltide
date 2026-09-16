# ReelTide — Project State

**Last updated:** 2026-09-17
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current code checkpoint:** `08434fa7d0bd4231854bf6da1dc5972a390f91aa`

> Live execution checkpoint. Repository/code/build/test evidence outranks stale documentation or conversation memory.

## Current Phase

**Phase 5 — Home/Search presentation refinement**

## DONE

- Active redesign branch remains based on the known-good `master`; old `ui/2026-modern-redesign` is not reused.
- Shared Liquid Glass foundation and reusable presentation primitives remain intact.
- Stable native launch shell/NavPill remains in place; the previous risky Compose shell injection is not used.
- Home hero is **628dp**, full-bleed, with cinematic top/bottom fades and no persistent top branding header.
- Profile remains the single persistent floating Liquid Glass control over the hero.
- Hero content hierarchy is title → metadata → genres → short synopsis → actions.
- Hero actions are **Play + Details only**. Save/bookmark is intentionally absent from the visible hero action layer and remains a Details-model concern.
- Play is an icon-only solid white circular primary action; Details is a compact dark solid pill with info icon.
- Hero actions are left-aligned and do not add another glass surface over the artwork.
- Empty successful hero responses collapse the entire hero shell instead of leaving a blank 628dp region with floating controls.
- Hero paging uses full-bleed pages without scale-down gutters.
- Home Source FAB/filter control is **retained**. Its source-picker behavior remains intact; only its visual treatment and positioning were corrected to a compact restrained glass control anchored above the bottom navigation layer.
- Source FAB no longer has provider-count visibility suppression; it is not removed merely because no plugin is currently installed.
- Home section headers now use a compact glass title treatment while `View All` remains plain text + arrow, preserving its existing callback behavior.
- Home metadata cleanup avoids empty bullets, hides invalid duration/year values, and keeps synopsis compact.
- Search filter chips remain monochrome black/white/grey with no magenta treatment.
- Setup navigation lifecycle changes preserve existing provider/media/extension setup behavior.

## IN PROGRESS

- Verify latest Home hero/action/source changes through GitHub Actions and on-device testing.
- Finish Home card sizing/spacing, section rhythm, profile treatment, navigation motion, and loading/error states.
- Finish Search visual/state cleanup.

## REMAINING

1. Verify Home hero, action placement, Source FAB positioning, and empty/loading/error states on-device.
2. Audit Home poster/card geometry and section rhythm across compact phones and larger layouts.
3. Audit Home profile overlay and NavPill interaction/scroll behavior.
4. Finish Search loading/empty/error states and surface hierarchy.
5. Replace Details presentation coherently while preserving its data/actions.
6. Replace Library and Downloads presentation.
7. Finish Settings sub-screen/account presentation.
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

- Latest Home changes are not yet device-verified.
- Latest CI run must match the current head before being treated as verification.
- The hidden `home_preview_bookmark` binding anchor exists only for compatibility with the current adapter code; it has zero size and is permanently gone from the visible hero.
- The 628dp hero and floating Source control need compact-width/landscape checks.
- Source FAB positioning must remain above the bottom navigation layer during scroll/shrink/extend transitions.

## FAILED APPROACHES

- Direct Compose NavPill injection into the launch shell before device verification: rejected after launch regression.
- Broad multi-surface UI patching without runtime checkpoints: rejected.
- Excessive Liquid Glass treatment: rejected; target remains restrained, approximately 25% or less of visible UI surfaces.
- Removing the Home Source FAB: rejected; control is retained and its presentation is fixed instead.
- Keeping Save/bookmark in the Home hero: rejected; save belongs in Details.
- Leaving an empty successful hero shell visible: rejected; hero collapses when the real preview list is empty.

## DESIGN DECISIONS

- Liquid Glass is a functional floating hierarchy, not universal decoration.
- Monochrome black/white/grey is the active visual direction.
- Home profile and Source are floating controls; hero Play/Details are solid content actions.
- Hero structure: full-bleed artwork → floating profile → title → metadata → synopsis → Play + Details → next content section.
- Preserve navigation IDs, graph, menus, state, adapters, ViewModels, repositories, player behavior and business ownership.

## VERIFICATION

- User previously confirmed the stable diagnostic build launches successfully.
- CloudStreamApp preference fix `b00655cf7dc59168d2894cbd591878043a87863b` passed GitHub Actions run `35034416615`.
- Earlier hero action visibility checkpoint `743c005d52487082e8e200255c2ad9ffce619b86` passed its GitHub Actions artifact build.
- Current head `08434fa7d0bd4231854bf6da1dc5972a390f91aa` has GitHub Actions run #305 currently queued; no green result is claimed yet.
- No latest-device verification is claimed.
- No ReelTide crash stacktrace has been captured from the earlier runtime incident.

## NEXT ACTION

Wait for/inspect the GitHub Actions result for the current head. If green, continue the Home subsystem audit rather than stopping at CI: card geometry, profile overlay, Source FAB interaction, NavPill motion, and loading/empty/error transitions. Then move into Search as the next coherent subsystem.
