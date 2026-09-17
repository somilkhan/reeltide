# ReelTide — Project State

**Last updated:** 2026-09-17
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current code checkpoint:** `128d32574fff7e9add5c731a50ca354164f021fc`

## Current Phase

**Phase 5 — Home/Search presentation refinement**

## DONE

- Known-good master baseline preserved; old `ui/2026-modern-redesign` is not reused.
- Native launch shell/NavPill remains stable; risky Compose shell injection remains rejected.
- Home hero is 628dp, full-bleed and cinematic with no persistent top branding header.
- Profile is the single persistent floating Liquid Glass control over the hero.
- Hero hierarchy: title → metadata → genres → synopsis → Play + Details.
- Play is solid white; Details is a dark solid pill; bookmark remains a Details concern.
- Empty successful hero responses collapse the hero shell.
- Home paging remains full-bleed.
- Source FAB remains functional, extended/stable through scroll transitions, opaque dark, and monochrome tune-icon based.
- Source selector is placed below the profile inside the hero; provider selection behavior is preserved.
- Home section headers use compact hierarchy with plain `View All` + arrow.
- Continue Watching follows the compact section rhythm.
- Home section spacing was tightened without changing RecyclerView behavior.
- Details keeps the existing TextView/ViewBinding contract while centering its icon+label group through controlled padding.
- Search state uses one restrained secondary surface rather than a nested glass icon surface.
- Home master RecyclerView has 120dp bottom clearance for the persistent bottom navigation.
- Metadata cleanup, monochrome search filters, and setup lifecycle preservation remain intact.

## IN PROGRESS

- Home poster/card geometry and profile/NavPill interaction audit.
- Search visual/state and provider/filter interaction audit.
- Device/runtime verification is unavailable here; CI is the build gate.

## REMAINING

1. Home hero/action/Source/loading/empty/error verification.
2. Home poster/card geometry across phone sizes.
3. Home profile overlay and NavPill motion/scroll behavior.
4. Search loading/empty/error, suggestions/history, provider/filter interactions.
5. Details presentation.
6. Library/Downloads presentation.
7. Settings/account presentation.
8. Player presentation audit.
9. Remaining secondary legacy Material surfaces.
10. Bounded blur only where justified.
11. Accessibility, reduced motion, contrast, dynamic text and performance.
12. Functional + visual regression matrix and obsolete presentation cleanup.

## BLOCKED

- Local Gradle execution unavailable because outbound repository/network resolution is unavailable.
- No device/runtime automation is available.

## REGRESSIONS / KNOWN RISKS

- Latest Home/Search changes are not device-verified.
- Source FAB needs compact/landscape/system-inset verification.
- Hidden zero-size `home_preview_bookmark` remains for adapter compatibility.
- Search behavior still needs runtime verification.
- `fragment_home.xml` retains the 120dp navigation clearance and existing IDs; no business logic was changed.

## FAILED APPROACHES

- Direct Compose NavPill injection caused a launch regression and remains rejected.
- Broad multi-surface patching without runtime checkpoints is rejected.
- Excessive Liquid Glass is rejected; target remains approximately 25% or less of visible UI surfaces.
- Removing Source FAB is rejected.
- Keeping bookmark in Home hero is rejected.

## DESIGN DECISIONS

- Liquid Glass is functional hierarchy, not universal decoration.
- Monochrome black/white/grey is the active visual direction.
- Profile and Source are floating controls; Play/Details are solid actions.
- Home structure is artwork → profile → source → title → metadata → synopsis → actions → rails.
- View All remains plain.
- Existing navigation, state, adapters, ViewModels, repositories, player behavior and business ownership remain authoritative.

## VERIFICATION

- Latest implementation checkpoint: `128d32574fff7e9add5c731a50ca354164f021fc`.
- CI Artifact Build run #334 (`35254760140`) for `5ddaa0db2551ed16572fc1f90db18e6f6a435055` completed successfully; build job `105315533628` passed all steps.
- Run #334 artifact `pull-request-build`: 83,017,240 bytes; SHA-256 `20805f9e91c987f195555abaf2b5a4e9298ee97868a8e99f4f4db0aede1ee16b`.
- The latest state-doc commit has not yet received a CI result.
- No device verification is claimed.

## NEXT ACTION

Continue Home card/rail geometry, then Search state/interaction audit. Keep functionality untouched.
