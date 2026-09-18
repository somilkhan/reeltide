# REELTIDE PROJECT STATE

## Branch
`ui/2026-liquid-glass-redesign`

## Current objective
Finish the Home liquid-glass visual redesign without changing provider/data-loading/navigation behavior.

## Runtime trace verified
- `activity_main.xml` -> `nav_host_fragment` + runtime bottom-nav container
- `fragment_home.xml` -> `home_master_recycler`
- `HomeFragment` -> `HomeParentItemAdapterPreview`
- `HomeParentItemAdapterPreview` -> `fragment_home_head.xml` + `HomeScrollAdapter`
- `HomeScrollAdapter` -> `home_scroll_view.xml`
- Bottom navigation -> `liquid_glass_nav_surface.xml` through `liquid_glass_nav_container`

## Five-issue status

1. **Home hero CTA geometry — FIXED IN CODE**
   - XML is now the authoritative geometry source.
   - Play uses a weighted `0dp` width inside the full-width CTA row.
   - Details uses fixed width and gap from XML.
   - Runtime adapter no longer replaces either CTA's `LayoutParams`.

2. **Hero rounded container — VERIFIED / HARDENED**
   - `home_preview_viewpager_text` is the runtime hero container.
   - It uses `home_hero_surface.xml` with rounded corners.
   - `clipChildren`, `clipToPadding`, `clipToOutline`, and `outlineProvider=background` are applied at the runtime hierarchy.

3. **Pagination — FIXED IN CODE**
   - Runtime code now updates the five existing `home_preview_dot_0..4` views instead of deleting/recreating them.
   - Pager selection and adapter data changes both feed `updatePagination()`.

4. **Latest-release metadata — FIXED IN CODE**
   - `HomeChildItemAdapter` already binds `card_title` and `card_subtitle`.
   - The bottom-layout `home_result_grid_expanded.xml` now exposes those IDs, closing the runtime binding/layout mismatch.

5. **Bottom navigation — FIXED/HARDENED IN CODE**
   - `activity_main.xml` references `@drawable/liquid_glass_nav_surface` through `liquid_glass_nav_container`.
   - The container now has explicit outline clipping.
   - `MainActivity.updateNavBar()` now controls the liquid-glass container visibility together with the actual `navView`.

## Constraints preserved
- No branch switch.
- No master changes.
- Provider/data-loading/navigation logic untouched.
- Existing layouts/adapters modified in place.
- No duplicate architecture.

## Verification state
- Static runtime-trace verification: **DONE**
- Code/layout consistency checks: **DONE**
- Clean Gradle build: **BLOCKED — repository has no existing CI workflow exposed for this branch, and this execution environment has no Android build/ADB workspace**
- Temporary CI workflow was created to attempt validation, but GitHub returned no workflow run for the commit; it was removed and is not part of the project.
- APK install/runtime screenshot verification: **BLOCKED for the same environment limitation**
- Final project completion: **NOT YET VERIFIED**

## 2026 liquid-glass navA

### Bottom-nav implementation
- Kept `bottom_nav_menu.xml` IDs/order/destinations unchanged.
- Replaced only the phone bottom-nav renderer with `LiquidGlassBottomNavigationView`.
- Inactive items are fixed 40dp icon-only items.
- Selected item uses the existing Material selection state, horizontal icon+label layout, content-width sizing, and the existing Material transition pipeline.
- Active background is the new gradient/border island drawable.
- Active/inactive icon and label colors use `#7BA6FF` / `#585F6E`.
- Outer surface remains `liquid_glass_nav_surface.xml`; side margin is now 22dp and inner padding 8dp per the requested spec.
- 4dp item gap is applied by the custom visual menu renderer.
- The active width is capped by available width after reserving four 40dp inactive items, so the active island cannot push an item outside the 360dp bar. At 360dp the requested label set fits without abbreviation under the current 40dp + 4dp gap geometry.
- `home_nav_active_size` was removed because the old fixed active-circle dimension is no longer used.
- No Home hero/card/provider/navigation destination code was changed for this nav task.

### Verification
- Menu IDs/order statically verified: Home, Search, Library, Downloads, Settings.
- Runtime routing implementation remains Material `BottomNavigationView`-based; the custom class only overrides menu rendering/measurement.
- Material 1.14.0 already performs delayed layout transitions around selection changes; the custom renderer changes only child widths/positions, so the existing transition animates the island expansion/shrink together with label state.
- Full `./gradlew clean assembleDebug`: **NOT EXECUTED** in this environment because the repository cannot be cloned into the build workspace (network/DNS access to GitHub is unavailable).
- APK installation and 360dp/430dp physical-device verification: **NOT EXECUTED**.
- Therefore this branch is **NOT declared PROJECT COMPLETE** and no APK download link is claimed.