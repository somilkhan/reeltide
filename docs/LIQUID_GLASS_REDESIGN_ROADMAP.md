# ReelTide — Liquid Glass Redesign Roadmap

**Project:** ReelTide Android app  
**Repository:** `somilkhan/reeltide`  
**Primary branch:** `ui/2026-liquid-glass-redesign`  
**Baseline:** repository `master`  
**Status:** Master execution plan / source of truth  
**Last updated:** 2026-09-13

---

## 0. Mission

Rebuild ReelTide's presentation layer into a premium, modern, monochrome **Apple-inspired Liquid Glass** experience while preserving the app's existing functionality, data flow, business logic, navigation semantics, and user state.

This is a **replacement of the old UI system**, not a cosmetic patch of the broken `ui/2026-modern-redesign` branch.

### Non-negotiables

- Do **not** use `ui/2026-modern-redesign` as an implementation base.
- Do **not** remove or silently break existing functionality.
- Do **not** create a second competing UI system that leaves obsolete components wired into screens.
- Do **not** stop at XML/theme edits or compilation.
- Do **not** declare completion without functional, build, interaction, and visual verification.
- Do **not** fake screenshots, test results, CI results, or implementation progress.
- Prefer root-cause fixes over local patches.
- If one approach fails repeatedly, stop repeating it and replace the approach.
- Keep a running status ledger in this document.

---

# 1. Execution Contract

For every phase:

1. Inspect the current repository implementation.
2. Identify dependencies and existing behavior.
3. Research current platform/reference patterns when needed.
4. Define the target behavior before implementation.
5. Implement the smallest coherent subsystem, not isolated visual hacks.
6. Integrate it into the real app.
7. Build.
8. Run relevant static checks/tests/CI where available.
9. Inspect for regressions and incomplete integration.
10. Continue to the next related task without waiting for another instruction.

### Never stop merely because

- one component compiles;
- one screen looks correct;
- one test passes;
- a file was edited;
- the app launches;
- the requested component was created but not integrated.

### Stop only for genuine blockers

- unavailable credentials/permissions;
- device-only behavior that cannot be reproduced or verified with available tooling;
- an external dependency/resource that is actually inaccessible;
- a product decision that cannot be inferred safely and materially changes behavior.

When blocked, record the exact blocker and continue all independent work.

---

# 2. Source-of-Truth / Branch Rules

## Branches

### `master`
Known-good baseline. Preserve as the comparison point.

### `ui/2026-modern-redesign`
Legacy redesign attempt. **Do not use as the implementation base.** Treat it as historical context only when useful for diagnosing what went wrong.

### `ui/2026-liquid-glass-redesign`
Current implementation branch. All new redesign work belongs here unless the strategy changes deliberately.

## Design source

Figma file created for this project:

**ReelTide — Liquid Glass UI System**  
`https://www.figma.com/design/y7A6PwFXxt3DrO5YYNjgod`

Figma is the visual/system reference. The repository is the implementation source of truth.

---

# 3. Phase 0 — Baseline Audit

## Goal
Understand the existing ReelTide architecture before touching presentation code.

### Inspect

- Gradle/build configuration
- modules
- Compose/XML usage
- navigation graph/routes
- theme setup
- screens and screen state
- ViewModels/presentation state
- repositories/data sources
- networking/cache/database boundaries
- media/player functionality
- authentication/account flows if present
- search/discovery
- detail pages
- settings
- dialogs/sheets
- error/loading/empty states
- tests
- CI workflows

### Produce

- screen inventory;
- navigation/state map;
- component inventory;
- list of reusable existing business logic that must remain untouched;
- list of UI code safe to replace;
- baseline build/test status;
- known regressions in the old redesign, if useful.

### Exit criteria

- We can explain where every major screen gets its state.
- We know which layer owns navigation.
- We know which UI components are shared.
- Baseline build/test evidence is recorded.

---

# 4. Phase 1 — Reference & Product Research

Research before locking the visual system.

## Reference families

### Apple / Liquid Glass
Study current Apple platform material behavior rather than copying superficial gradients:

- layered translucency;
- depth and separation;
- content-aware material perception;
- dynamic highlights/borders;
- glass controls over changing content;
- hierarchy between primary and secondary surfaces;
- motion during state changes;
- accessibility/readability under translucency.

### Bingr.one
Use the current mobile website as a behavioral/visual reference where appropriate, especially for the ReelTide home/content browsing experience.

### Streaming/media products
Study information hierarchy for:

- posters/thumbnails;
- hero content;
- continue watching;
- categories;
- search;
- details;
- player entry points;
- progress states.

### Mobbin
Use current production UI references to validate patterns for navigation, sheets, search, media browsing, and modern mobile interaction—not to copy unrelated branding.

### Android / Jetpack Compose
Validate implementation against current Android/Compose capabilities and constraints. Prefer platform-native equivalents of modern motion/material behavior over importing web-only paradigms.

---

# 5. Phase 2 — Visual Design System

Create one coherent design language before rebuilding screens.

## 5.1 Color

Primary direction:

- monochrome black / white / grey;
- OLED-friendly dark surfaces;
- no decorative rainbow gradients;
- no arbitrary accent colors;
- semantic contrast levels instead of many unrelated grays.

Define tokens for:

- background;
- elevated background;
- glass fill;
- glass highlight;
- glass border;
- primary text;
- secondary text;
- tertiary text;
- disabled text;
- scrim;
- destructive state;
- success/warning only where functionally required.

## 5.2 Typography

Define:

- display/title scale;
- section title;
- body;
- metadata;
- compact labels;
- button labels;
- navigation labels;
- numeric/media metadata.

Typography must remain readable over translucent/blurred content.

## 5.3 Spacing / shape

Create centralized tokens for:

- screen margins;
- content gaps;
- card padding;
- control heights;
- corner radii;
- icon sizes;
- touch targets;
- sheet/dialog radii.

## 5.4 Glass material hierarchy

At minimum define distinct material levels:

1. **Base** — app background/content layer.
2. **Glass** — normal floating translucent surface.
3. **Elevated Glass** — important controls/cards.
4. **Strong Glass** — navigation/critical floating controls.
5. **Sheet Glass** — modal surfaces with stronger separation.

Each level defines:

- translucency;
- blur strategy;
- border/highlight;
- shadow/depth treatment;
- content contrast requirements.

Avoid applying identical blur/alpha everywhere. Uniform glass becomes visually flat and hurts performance/readability.

---

# 6. Phase 3 — Core UI Architecture

Build reusable primitives first.

## Required foundations

- `ReelTideTheme`
- design tokens
- typography system
- shape system
- glass material primitives
- interaction state primitives
- icon/button primitives
- card primitives
- sheet/dialog primitives
- navigation surface

Suggested component family:

- `GlassSurface`
- `GlassCard`
- `GlassButton`
- `GlassIconButton`
- `GlassPill`
- `GlassNavPill`
- `GlassSheet`
- `GlassDialog`
- media/poster/card variants
- loading/empty/error components

Names may differ if repository conventions require it; the architectural rule does not.

## Component rules

- Components own visual behavior, not business logic.
- State comes from the existing app architecture.
- No duplicated navigation/business state.
- Components must expose accessibility semantics.
- Touch targets remain usable despite visual minimalism.
- Components must support loading/disabled/pressed/selected/error states where applicable.

---

# 7. Phase 4 — Navigation + NavPill

**Highest-risk UI subsystem. Rebuild it coherently rather than patching the old NavPill.**

## State machine

Explicit states:

- `Idle`
- `Pressed`
- `Selected`
- `Transitioning`
- `Disabled`
- `Scrolling`

The exact implementation can use sealed state, derived state, or another appropriate mechanism, but behavior must be deterministic.

## Motion

Use native Compose/Android motion primitives to achieve a Framer-Motion-like feel:

- spring-based selection;
- icon scale/weight transition;
- label opacity/width interpolation;
- active glass indicator movement;
- controlled surface alpha/blur interpolation;
- gesture-aware transitions;
- interruption-safe animation;
- no abrupt recomposition flashes.

## Navigation behavior tests

Verify:

- selecting every destination;
- back navigation;
- rapid repeated taps;
- tapping during animation;
- restoring selected destination;
- process/configuration restoration where supported;
- long labels;
- narrow screens;
- accessibility services;
- disabled destinations if present;
- scrolling/content overlap;
- keyboard/IME interactions where relevant.

## Exit criteria

Navigation is visually premium **and** functionally equivalent to the existing navigation contract.

---

# 8. Phase 5 — Screen-by-Screen Replacement

Rebuild screens in dependency order, not random order.

## 8.1 App shell

- edge-to-edge;
- system-bar treatment;
- root background;
- global content insets;
- navigation layer;
- global motion/material rules.

## 8.2 Home

Priority screen.

Target:

- strong content hierarchy;
- premium hero/featured area where existing functionality supports it;
- continue-watching/progress surfaces;
- category/content rails;
- modern poster treatment;
- glass controls only where they improve hierarchy;
- fluid scroll behavior;
- skeleton/loading states;
- empty/error states.

Home should use Bingr-like information density where appropriate without breaking ReelTide's actual data model.

## 8.3 Search / discovery

- prominent but restrained search affordance;
- modern input state;
- keyboard behavior;
- recent/history state if existing functionality supports it;
- results loading;
- empty results;
- errors;
- filtering/sorting if existing.

## 8.4 Details

- poster/hero;
- metadata hierarchy;
- primary actions;
- secondary actions;
- seasons/episodes or equivalent media structure;
- progress state;
- loading/error handling.

## 8.5 Player / playback entry

Preserve existing playback behavior exactly.

Redesign only presentation controls around it unless the existing architecture explicitly permits more.

Verify:

- play/pause;
- seeking;
- progress;
- orientation/fullscreen;
- back behavior;
- loading/buffering;
- errors;
- resume position;
- external player integration if present.

## 8.6 Library / watchlist / history

Preserve all existing data/state semantics while replacing visual presentation.

## 8.7 Settings / account

- grouped glass sections;
- clear hierarchy;
- destructive actions visually distinct but still monochrome/semantic;
- dialogs/sheets rebuilt consistently.

## 8.8 Secondary screens

Do not leave legacy Material-default screens behind. Audit all reachable screens and replace the old visual language consistently.

---

# 9. Phase 6 — Motion System

Motion must be systemic, not random animation added to individual components.

## Motion principles

- spring-first for direct manipulation;
- short transitions for state changes;
- longer transitions only for spatial/navigation changes;
- preserve velocity where possible;
- avoid excessive parallax;
- avoid bouncing decorative animations;
- animations must be interruptible;
- animations must respect reduced-motion/accessibility settings where supported.

## Required motion categories

- navigation selection;
- screen enter/exit;
- sheet presentation/dismissal;
- dialog presentation;
- card press;
- loading-to-content transition;
- selection changes;
- scrolling/toolbar behavior;
- player controls show/hide.

---

# 10. Phase 7 — Performance & Rendering

Liquid Glass is only successful if it remains responsive.

Audit:

- blur cost;
- recomposition frequency;
- unnecessary state reads;
- list item stability/keys;
- image loading/caching;
- animation allocation;
- nested scrolling;
- large translucent surfaces;
- GPU overdraw where measurable;
- frame drops/jank where tooling permits.

Rules:

- Avoid full-screen expensive blur when a cheaper material achieves the same hierarchy.
- Avoid rebuilding heavy effects for every list item.
- Keep lazy lists truly lazy.
- Keep media/image loading independent from presentation recomposition.
- Do not trade app functionality for visual effects.

---

# 11. Phase 8 — Accessibility / Robustness

Verify:

- minimum touch targets;
- content descriptions / semantics;
- focus order;
- keyboard/DPAD behavior where applicable;
- readable contrast over glass;
- dynamic font scaling;
- long localized strings;
- screen width extremes;
- orientation changes where supported;
- system dark/light behavior if supported by the product;
- reduced-motion behavior where supported.

Visual minimalism must never remove functional affordances.

---

# 12. Phase 9 — Regression Verification

Compare redesigned flows against the baseline behavior.

## Functional regression matrix

| Area | Verify |
|---|---|
| Launch | app starts without crash |
| Navigation | every reachable route works |
| Back | back stack remains correct |
| Home | content loads and actions work |
| Search | input/results/errors work |
| Details | actions and media metadata work |
| Playback | playback contract remains intact |
| Library/history | data and state remain intact |
| Settings | preferences/actions remain intact |
| Sheets/dialogs | open/close/confirm/cancel work |
| Loading | no stuck states introduced |
| Errors | recoverable errors remain actionable |
| State restoration | selected state survives where expected |

## Visual regression matrix

Check:

- no legacy Material surfaces remain unexpectedly;
- no inconsistent corner radii;
- no random colors/gradients;
- glass hierarchy is consistent;
- typography is consistent;
- spacing follows tokens;
- navigation looks correct on all screens;
- edge-to-edge/insets are correct;
- OLED dark mode has no unintended bright artifacts;
- content remains readable over images.

---

# 13. Phase 10 — Build / Test / CI Evidence

Before declaring completion:

1. Build the relevant app variants.
2. Run unit tests.
3. Run instrumentation/UI tests available in the repository.
4. Run lint/static analysis available in the repository.
5. Inspect build output for warnings/errors relevant to the redesign.
6. Use GitHub Actions/CI where available.
7. Perform visual/browser/device verification when an appropriate tool/device is available.
8. Re-test flows affected by every major subsystem change.

Record actual evidence in the status ledger below. Never mark a check as verified from assumption.

---

# 14. Phase 11 — Cleanup

After the replacement UI is stable:

- remove obsolete UI components;
- remove dead theme/token code;
- remove duplicate navigation implementations;
- remove temporary adapters;
- remove unused dependencies introduced by failed approaches;
- remove TODOs created by the redesign;
- verify imports/resources;
- verify no old redesign branch code was accidentally copied;
- keep commits logically reviewable.

Do not delete code merely because it looks old if it still owns business/data functionality.

---

# 15. Definition of Done

The project is **not complete** until all applicable criteria below are true.

### Functional

- Existing core functionality remains available.
- Navigation is intact.
- Data/state/business logic is preserved.
- Media/playback flows work.
- Search/discovery works.
- Settings/account/library flows work.
- Loading/error/empty states work.

### Visual

- The old UI language has been replaced.
- The app has one coherent monochrome Liquid Glass system.
- Materials have deliberate hierarchy.
- Typography/spacing/shapes are tokenized and consistent.
- Navigation is premium and motion-rich without being distracting.
- No generic Material-default look remains in redesigned surfaces.

### Technical

- Presentation architecture is coherent.
- No duplicate competing UI systems are left active.
- Performance is acceptable.
- Accessibility remains usable.
- No avoidable hacks or dead code remain.

### Verification

- Build passes.
- Relevant tests pass.
- Static checks pass or documented pre-existing failures are isolated.
- Critical flows have been manually/automatically verified where tooling permits.
- Visual verification has been performed where tooling permits.
- Regression audit completed.

Only after all applicable criteria are evidenced may the branch be described as **PROJECT COMPLETE**.

---

# 16. Persistent Status Ledger

This section is intentionally kept in the roadmap so progress survives across sessions.

## DONE

- [x] Created clean implementation branch: `ui/2026-liquid-glass-redesign` from repository baseline.
- [x] Created Figma file: `ReelTide — Liquid Glass UI System`.
- [x] Established this roadmap as the implementation source-of-truth document.

## IN PROGRESS

- [ ] Repository architecture/baseline audit.
- [ ] Current-reference research.
- [ ] Final visual token specification.
- [ ] Core Liquid Glass component architecture.
- [ ] Navigation/NavPill replacement.
- [ ] Screen-by-screen redesign.
- [ ] Motion system.
- [ ] Performance/accessibility audit.
- [ ] Full regression verification.

## BLOCKED

_None currently recorded._

## REMAINING

- [ ] Complete baseline inventory and record evidence.
- [ ] Research and lock visual behavior.
- [ ] Implement core design system.
- [ ] Replace navigation subsystem.
- [ ] Replace all reachable presentation surfaces.
- [ ] Verify interactions and state restoration.
- [ ] Run build/tests/static checks/CI.
- [ ] Perform visual and functional regression audit.
- [ ] Remove obsolete presentation code.
- [ ] Final completion review.

## REGRESSIONS

_None currently recorded._

## FAILED APPROACHES / LESSONS

- **Legacy redesign patching:** explicitly avoided. The old `ui/2026-modern-redesign` branch is not the implementation base because incremental patching previously caused UI conflicts and functionality regressions.
- **NavPill micro-patching:** explicitly avoided. NavPill must be rebuilt as a coherent component/state machine and then integrated with the existing navigation state.
- **Generic Material/default styling:** rejected as insufficient for the target product direction.
- **Literal web Framer Motion dependency:** not required. Use native Compose/Android motion primitives to reproduce the interaction language without forcing a web animation stack into the native app.

## KNOWN RISKS

- Heavy blur/translucency can cause GPU/frame-time regressions.
- Incorrect glass opacity can reduce text contrast.
- Replacing presentation code can accidentally duplicate or disconnect existing state/navigation logic.
- Player/media screens may have tighter functional coupling than ordinary content screens.
- Some visual verification may require a physical/emulated Android device or CI environment.
- Current third-party/reference designs may evolve; implementation should preserve the established design principles rather than chase arbitrary screenshots.

## ROOT-CAUSE RULE

Whenever a regression appears:

1. reproduce it;
2. identify the owning subsystem;
3. inspect adjacent dependencies;
4. fix the underlying cause;
5. rerun the affected verification;
6. inspect for related regressions;
7. record the lesson here if the failed approach could recur.

---

# 17. Session Handoff Protocol

At the beginning/end of meaningful work sessions, update this document with:

- current phase;
- completed items;
- exact remaining work;
- blockers;
- tests/build evidence;
- regressions discovered;
- failed approaches;
- decisions that must not be revisited unnecessarily.

A future session should be able to read this file and continue execution without relying on chat history.

**Continuation rule:** read this roadmap first, inspect the actual branch state second, reconcile the roadmap with reality, then continue from the highest-priority incomplete item.

---

# 18. Final Principle

**Preserve the product. Replace the presentation. Verify the result.**

The redesign is successful only when ReelTide feels like a deliberate, premium Liquid Glass product while behaving like the same reliable ReelTide underneath.
