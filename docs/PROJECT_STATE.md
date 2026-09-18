# ReelTide — Project State

**Last updated:** 2026-09-18
**Active branch:** `ui/2026-liquid-glass-redesign`
**Baseline:** `master`
**Current code checkpoint:** `5d77015cf858c448ec84ba9faffce5eed1a7a4f1`

## Current Phase

**Phase 5 — Home/Search presentation refinement**

## DONE

- Known-good master baseline preserved; old `ui/2026-modern-redesign` is not reused.
- Native launch shell/NavPill remains stable; risky Compose shell injection remains rejected.
- Home hero is a responsive rounded presentation surface with 24dp corners and width-qualified heights: 517dp at 360dp, 575dp at 400dp, 560dp at the 390dp reference, and 617dp at 430dp.
- Hero artwork remains full-bleed inside the rounded container with a strong bottom-anchored cinematic scrim.
- Profile remains the persistent floating control over the hero.
- Source selector remains functional as a persistent bottom-right floating control, positioned above the bottom navigation island, and uses the restrained 55%-surface token with a strong border.
- Hero hierarchy is badge → title → metadata → synopsis → Play + Details → pagination.
- Hero title uses 34sp/800 styling, tightened tracking and two-line clamping.
- Hero metadata supports year, duration and rating with dot separators while preserving the existing LoadResponse data source.
- Hero synopsis remains a two-line presentation clamp and uses secondary text styling.
- Play and Details preserve their existing IDs/callbacks while the action-button wrapper keeps the redesigned 48dp CTA geometry after legacy adapter binding.
- Latest release rails use 118x168 artwork, 14dp corners, restrained borders, 12dp inter-item spacing and title text below the artwork; the item root no longer adds a second 12dp margin, preventing doubled visual gaps.
- Latest release section headers now use 19sp primary hierarchy, 13sp secondary `View all`, 22dp screen padding and 26dp section rhythm.
- Bottom navigation remains the existing 5-item menu/navigation graph, presented as a 64dp floating pill with 22dp side margins, 26dp bottom margin, 72%-surface fallback, 8dp inner padding and compact active indicator styling.
- Bottom navigation icons use outline-oriented assets with accent active-state color.
- Existing navigation IDs, provider logic, adapters, ViewModels and business actions remain authoritative.

## IN PROGRESS

- Verify the latest Home hero CTA and bottom-navigation rendering via CI/device
- Finish Home hero clipping/profile/source/pagination/loading/error/empty audit
- Finish Search visual/state cleanup and interaction verification

## REMAINING

1. Verify latest Home build and artifact.
2. Device/runtime verification at 360dp and 430dp; no device automation is available here.
3. Finish Home profile/source/pagination/NavPill interaction audit.
4. Finish Search visual/state and provider/filter interaction audit.
5. Details presentation.
6. Library/Downloads presentation.
7. Settings/account presentation.
8. Player presentation audit.
9. Remaining secondary legacy Material surfaces.
10. Bounded blur only where justified; current surfaces use deterministic flat fallbacks.
11. Accessibility, reduced motion, contrast, dynamic text and performance.
12. Functional + visual regression matrix and obsolete presentation cleanup.

## BLOCKED

- Local Gradle execution unavailable because outbound repository/network resolution is unavailable.
- No device/runtime automation is available.
- Inter is not bundled in the repository and binary font upload is unavailable through the current repository connector; the existing bundled Google Sans resource is used as the presentation fallback rather than introducing a fake Inter alias.

## REGRESSIONS / KNOWN RISKS

- Latest Home redesign is not device-verified.
- Pagination dots are layout-present but still need runtime synchronization verification against variable ViewPager item counts.
- Source FAB and bottom navigation need compact/landscape/system-inset verification.
- Hidden zero-size `home_preview_bookmark` remains for adapter compatibility.
- Search behavior still needs runtime verification.

## FAILED APPROACHES

- Direct Compose NavPill injection caused a launch regression and remains rejected.
- Broad multi-surface patching without runtime checkpoints is rejected.
- Excessive Liquid Glass is rejected; target remains approximately 25% or less of visible UI surfaces.
- Removing Source FAB is rejected.
- Keeping bookmark in Home hero is rejected.

## DESIGN DECISIONS

- Liquid Glass is functional hierarchy, not universal decoration.
- Dark-only 2026 Home tokens are black/blue-grey with restrained accent blue.
- Profile and Source are floating controls; Play/Details are solid content actions.
- Home hero uses responsive width-qualified sizing rather than one fixed height across phones.
- Poster cards use 118x168 artwork, 14dp radius and 12dp rail spacing.
- Bottom navigation is a floating pill; the active indicator is restricted to the icon slot rather than the entire bar width.
- Existing navigation, state, adapters, ViewModels, repositories, player behavior and business ownership remain authoritative.

## VERIFICATION

- Active branch verified as `ui/2026-liquid-glass-redesign`.
- Latest implementation checkpoint before this state-ledger update: `5d77015cf858c448ec84ba9faffce5eed1a7a4f1`.
- Artifact Build run #402 (`35371267726`) is in progress for that checkpoint; no success is claimed yet.
- The preceding Artifact Build run #401 (`35359494840`) passed all steps and produced artifact `pull-request-build` for checkpoint `9a99b625455c770e5042ebbf9e16f645394b4082`.
- No device verification is claimed.

## NEXT ACTION

- Wait for CI #402; if green, continue with device/runtime verification and the remaining Home/Search audits. If red, inspect the failing Gradle step and fix the root cause.