# ReelTide — Project State

**Last updated:** 2026-09-17
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current code checkpoint:** `ab926b3d2f444a4892d3ff8d27bab013eece54a3`

> Live execution checkpoint. Repository/code/build/test evidence outranks stale documentation or conversation memory.

## Current Phase

**Phase 5 — Home/Search presentation refinement**

## DONE

- Redesign branch remains based on known-good `master`; historical `ui/2026-modern-redesign` is not reused.
- Shared Liquid Glass foundation and reusable presentation primitives remain intact.
- Stable native launch shell/NavPill remains in place; rejected risky Compose shell injection is not used.
- Home hero remains **628dp**, full-bleed, cinematic, with no persistent top branding header.
- Profile remains the single persistent floating Liquid Glass control over the hero.
- Hero hierarchy remains title → metadata → genres → short synopsis → Play + Details.
- Hero Play is solid white; Details is a dark solid pill; Save/bookmark remains a Details concern.
- Empty successful hero responses collapse the hero shell.
- Home paging remains full-bleed without scale-down gutters.
- Home Source FAB remains present, stable and extended during scroll/shrink transitions, with a deliberately opaque dark surface and monochrome tune icon.
- Source selector is positioned in the hero below the profile instead of competing with section headers.
- Source provider-count suppression remains removed; source selection behavior is preserved.
- Standard Home section headers use compact title hierarchy with plain `View All` + arrow.
- Continue Watching now follows the same compact section-header rhythm instead of the older full-width header treatment.
- Home section spacing was tightened to reduce large vertical gaps between headers and poster rails while preserving RecyclerView behavior.
- Hero Details action keeps the leading info icon while the label is visually centered within the pill via controlled content padding.
- Search state presentation was simplified by removing the nested glass-looking state icon surface; the state container remains the single secondary surface.
- Home metadata cleanup, monochrome search filter-chip treatment, and setup lifecycle preservation remain intact.

## IN PROGRESS

- Verify latest Home/Search presentation changes through CI and device/runtime testing where available.
- Finish Home poster/card geometry, profile overlay, NavPill interaction/motion, and loading/error/empty transitions.
- Finish Search visual/state cleanup and behavior audit.

## REMAINING

1. Verify Home hero/action/Source FAB and loading/empty/error states on-device.
2. Audit Home poster/card geometry across compact phones and larger layouts.
3. Audit Home profile overlay and NavPill interaction/scroll behavior.
4. Finish Search loading/empty/error states, suggestions/history hierarchy, and provider/filter interactions.
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
- No new GitHub Actions run is currently exposed for the latest commits; prior head `00cd5e...` is the last confirmed green build.

## REGRESSIONS / KNOWN RISKS

- Latest Home/Search layout changes are not device-verified.
- Current Home Source FAB positioning must be checked on compact/landscape layouts and against system insets.
- Hidden zero-size `home_preview_bookmark` remains intentionally non-visible for adapter compatibility.
- Search still needs behavioral/state verification, not only visual review.
- The latest Details alignment adjustment is implemented through existing ViewBinding and does not change click/business behavior.

## FAILED APPROACHES

- Direct Compose NavPill injection into the launch shell caused a launch regression and remains rejected.
- Broad multi-surface UI patching without runtime checkpoints is rejected.
- Excessive Liquid Glass treatment is rejected; target remains restrained, approximately 25% or less of visible UI surfaces.
- Removing the Home Source FAB is rejected; it remains a functional control.
- Keeping Save/bookmark in the Home hero is rejected; save belongs in Details.
- Leaving an empty successful hero shell visible is rejected; it collapses when preview data is empty.

## DESIGN DECISIONS

- Liquid Glass is functional hierarchy, not universal decoration.
- Monochrome black/white/grey is the active visual direction.
- Home profile and Source are floating controls; hero Play/Details are solid content actions.
- Home structure is full-bleed artwork → profile → source → title → metadata → synopsis → Play + Details → content rails.
- Section headers use compact hierarchy; `View All` remains plain rather than another glass container.
- Search state uses one restrained secondary surface instead of nested surfaces.
- Existing navigation IDs, graph, menus, state, adapters, ViewModels, repositories, player behavior and business ownership remain authoritative.

## VERIFICATION

- Branch head verified at `ab926b3d2f444a4892d3ff8d27bab013eece54a3`.
- Prior head `00cd5eaba308ae5bb256e53c77bc48fd2cab49a1` had GitHub Actions Artifact Build run #322 (`35243496790`) completed successfully.
- No CI result is claimed for the latest layout commits because no new run is currently exposed for them.
- User previously confirmed a stable diagnostic build launched successfully; no new device verification is claimed here.
- No ReelTide crash stacktrace has been captured from the earlier runtime incident.

## NEXT ACTION

Continue the Home poster/card geometry audit, then complete the Search state/interaction audit. Use CI/device evidence as available and keep functionality untouched.
