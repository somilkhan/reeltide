# ReelTide — Project State

**Last updated:** 2026-09-17
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current code checkpoint:** `848f3c20d054431e08124c5fb7683116b553ef74`

> Live execution checkpoint. Repository/code/build/test evidence outranks stale documentation or conversation memory.

## Current Phase

**Phase 5 — Home/Search presentation refinement**

## DONE

- Redesign branch remains based on the known-good `master`; historical `ui/2026-modern-redesign` is not reused.
- Shared Liquid Glass foundation and reusable presentation primitives remain intact.
- Stable native launch shell/NavPill remains in place; the rejected risky Compose shell injection is not used.
- Home hero remains **628dp**, full-bleed, cinematic, with no persistent top branding header.
- Profile remains the single persistent floating Liquid Glass control over the hero.
- Hero hierarchy remains title → metadata → genres → short synopsis → Play + Details.
- Hero Play is solid white; Details is a dark solid pill; Save/bookmark remains a Details concern.
- Empty successful hero responses collapse the hero shell.
- Home paging remains full-bleed without scale-down gutters.
- Home Source FAB remains present, stable and extended during scroll/shrink transitions, with a restrained dark surface and monochrome filter icon.
- Source provider-count suppression remains removed; source selection behavior is preserved.
- Standard Home section headers use compact title hierarchy with plain `View All` + arrow.
- Continue Watching now follows the same compact section-header rhythm instead of the older full-width header treatment.
- Home section spacing was tightened to reduce the large vertical gaps between headers and poster rails while preserving RecyclerView behavior.
- Home metadata cleanup and monochrome search filter-chip treatment remain intact.
- Setup navigation lifecycle changes preserve provider/media/extension setup behavior.

## IN PROGRESS

- Verify the latest Home layout changes through CI and device/runtime testing where available.
- Finish Home poster/card geometry, profile overlay, NavPill interaction/motion, and loading/error/empty transitions.
- Finish Search visual/state cleanup.

## REMAINING

1. Verify Home hero/action/Source FAB and loading/empty/error states on-device.
2. Audit Home poster/card geometry across compact phones and larger layouts.
3. Audit Home profile overlay and NavPill interaction/scroll behavior.
4. Finish Search loading/empty/error states and surface hierarchy.
5. Replace Details presentation coherently while preserving data/actions.
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
- The available GitHub Actions workflow is the executable build authority, but no new run has appeared yet for commits `7f18b7a` / `848f3c2`.

## REGRESSIONS / KNOWN RISKS

- Latest Home spacing/header changes are not device-verified.
- Current Home Source FAB layout relies on the existing parent positioning contract and must be checked on compact/landscape layouts.
- The hidden zero-size `home_preview_bookmark` compatibility anchor remains intentionally non-visible.
- Search has multiple presentation surfaces and still needs a complete state audit.

## FAILED APPROACHES

- Direct Compose NavPill injection into the launch shell before device verification caused a launch regression and remains rejected.
- Broad multi-surface UI patching without runtime checkpoints is rejected.
- Excessive Liquid Glass treatment is rejected; target remains restrained, approximately 25% or less of visible UI surfaces.
- Removing the Home Source FAB is rejected; it remains a functional control.
- Keeping Save/bookmark in the Home hero is rejected; save belongs in Details.
- Leaving an empty successful hero shell visible is rejected; it collapses when preview data is empty.

## DESIGN DECISIONS

- Liquid Glass is functional hierarchy, not universal decoration.
- Monochrome black/white/grey is the active visual direction.
- Home profile and Source are floating controls; hero Play/Details are solid content actions.
- Home structure is full-bleed artwork → profile → title → metadata → synopsis → Play + Details → content rails.
- Section headers use compact hierarchy; `View All` remains plain rather than another glass container.
- Existing navigation IDs, graph, menus, state, adapters, ViewModels, repositories, player behavior and business ownership remain authoritative.

## VERIFICATION

- Branch head verified at `848f3c20d054431e08124c5fb7683116b553ef74`.
- Prior head `00cd5eaba308ae5bb256e53c77bc48fd2cab49a1` had GitHub Actions Artifact Build run #322 (`35243496790`) completed successfully.
- No new GitHub Actions run is currently exposed for the two latest layout commits, so their build status is not claimed.
- User previously confirmed a stable diagnostic build launched successfully; no new device verification is claimed here.
- No ReelTide crash stacktrace has been captured from the earlier runtime incident.

## NEXT ACTION

Continue the Home subsystem audit at the poster/card layer, then perform a full Search state/surface pass. Use CI/device evidence as available; do not treat layout edits alone as completion.
