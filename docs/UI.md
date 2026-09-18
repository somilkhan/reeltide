# ReelTide UI System

This document defines the visual UI rules for the Liquid Glass redesign. It is a product design authority, not a list of decorative effects.

## Visual Direction

Premium, restrained, monochrome, OLED-friendly, Apple-inspired Liquid Glass translated appropriately to native Android.

The visual language must feel intentional and editorial rather than like a generic Material theme or an AI-generated glassmorphism template.

## Core Liquid Glass Principle

Liquid Glass is a **functional hierarchy layer**, not a universal surface treatment. Important controls float above content and remain visually related to the content they control. The strongest material treatment belongs to high-value interaction surfaces; content itself should remain dominant.

Current Apple guidance emphasizes hierarchy, legibility, restrained use of Liquid Glass, and adaptive behavior across accessibility settings. ReelTide adopts those principles while implementing them with native Android capabilities rather than reproducing Apple's proprietary framework behavior.

## Material Hierarchy

Use layered surfaces with distinct roles:

- Base: deep neutral background/content.
- Glass: normal translucent floating surfaces.
- Elevated Glass: important cards and controls.
- Strong Glass: navigation and high-priority floating controls.
- Sheet Glass: modal surfaces with stronger separation.

Map material strength to function and context. Do not apply the same opacity, blur, border, or shadow to every surface.

### Media context

For visually rich poster/video backgrounds, a lighter/clearer material may be appropriate so the media remains visible. For text-heavy controls or variable/bright backgrounds, use a stronger material and/or scrim so readability wins.

Avoid putting heavy glass over faces, poster typography, subtitles, or other important media content unless the control genuinely needs that placement.

## Color

Default palette is black/white/neutral grey. Accent colors are not decorative. Semantic colors are reserved for meaning such as errors, warnings, or success.

Use contrast and luminance hierarchy instead of a large collection of near-duplicate grays.

## Typography

Typography carries hierarchy. Use a restrained scale with clear distinction between display/title, section title, body, metadata, compact labels, navigation labels, and numeric/media metadata.

Never sacrifice readability to achieve minimalism.

## Spacing and Shapes

Use centralized tokens. Repeated components must share consistent margins, gaps, radii, icon sizing, and touch-target dimensions.

Corner radius should communicate component hierarchy; avoid making every object a pill.

## Glass Treatment

Glass should communicate depth and layering. Prefer subtle translucency, controlled blur, fine highlights/borders, and appropriate separation from the content underneath.

A glass surface must remain readable over variable content. Avoid blur everywhere, excessive opacity, excessive highlights, or effects that obscure posters/media.

Use scroll-edge material/blur only where floating controls overlap scrolling content. Do not add edge effects as decoration where no UI/content boundary needs clarification.

## Components

Core components should be coherent across the product, including:

- surfaces
- cards
- buttons
- icon buttons
- pills
- navigation
- sheets
- dialogs
- media/poster surfaces
- loading/empty/error states

A component's visual state must be deliberate for default, pressed, selected, focused, disabled, loading, and error states where relevant.

## Navigation

Navigation is a primary visual anchor. The NavPill must feel integrated with the material system, not like a separate floating widget pasted onto the app.

Selection should communicate location through coordinated material, icon, label, and motion changes.

Navigation material should remain functional and unobtrusive; it must not visually compete with the primary media content.

## Imagery

Posters and artwork are content, not decoration. Preserve their prominence while ensuring controls remain legible. Avoid placing heavy glass over important faces/text in artwork when unnecessary.

## Icons

Use one coherent icon language. Optical sizing matters more than nominal box dimensions. Avoid mixing visually incompatible icon families.

## Edge-to-Edge

Design for edge-to-edge layouts. System bars, navigation, gesture areas, keyboard, sheets, and floating controls must respect insets without visible layout jumps.

Android 15+ enforces edge-to-edge for apps targeting the relevant SDK, so the redesign must treat insets as part of the layout model rather than as a late styling adjustment.

## Motion

Motion belongs to the visual system. Prefer native Compose/Android spring and transition primitives. Motion should be responsive, interruptible, and tied to user intent.

Liquid material transitions should communicate continuity rather than decorative spectacle.

Avoid gratuitous animation.

## Accessibility / Adaptation

Glass must have an accessibility-safe fallback. When transparency or motion needs to be reduced, prioritize opaque/semi-opaque contrast and simplified motion rather than preserving visual effects at the expense of legibility.

Test custom glass surfaces under reduced-motion, increased-contrast, dynamic-text, and other applicable accessibility configurations.

## Performance

Blur and translucent layers have real rendering cost. Use bounded surfaces and avoid expensive full-screen or per-item blur unless profiling/evidence justifies it.

Android Compose blur renders content into a separate graphics layer and has API-level constraints; the implementation must account for this rather than assuming arbitrary blur is free.

## Quality Gate

Reject a UI change if it is visually impressive in isolation but inconsistent with the system, harms readability/performance, duplicates an existing pattern, or makes existing functionality less discoverable.

A successful Liquid Glass surface should answer three questions:

1. Why does this surface need to float above the content?
2. What interaction or hierarchy does its material communicate?
3. Does the effect improve the product enough to justify its visual and rendering cost?
