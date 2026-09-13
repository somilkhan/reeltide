# ReelTide UI System

This document defines the visual UI rules for the Liquid Glass redesign. It is a product design authority, not a list of decorative effects.

## Visual Direction

Premium, restrained, monochrome, OLED-friendly, Apple-inspired Liquid Glass translated appropriately to native Android.

The visual language must feel intentional and editorial rather than like a generic Material theme or an AI-generated glassmorphism template.

## Material Hierarchy

Use layered surfaces with distinct roles:

- Base: deep neutral background/content.
- Glass: normal translucent floating surfaces.
- Elevated Glass: important cards and controls.
- Strong Glass: navigation and high-priority floating controls.
- Sheet Glass: modal surfaces with stronger separation.

Do not apply the same opacity, blur, border, or shadow to every surface.

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

## Imagery

Posters and artwork are content, not decoration. Preserve their prominence while ensuring controls remain legible. Avoid placing heavy glass over important faces/text in artwork when unnecessary.

## Icons

Use one coherent icon language. Optical sizing matters more than nominal box dimensions. Avoid mixing visually incompatible icon families.

## Edge-to-Edge

Design for edge-to-edge layouts. System bars, navigation, gesture areas, keyboard, sheets, and floating controls must respect insets without visible layout jumps.

## Motion

Motion belongs to the visual system. Prefer native Compose/Android spring and transition primitives. Motion should be responsive, interruptible, and tied to user intent.

Avoid gratuitous animation.

## Accessibility

Visual minimalism cannot reduce usable touch targets, focusability, semantics, contrast, or dynamic text support.

## Quality Gate

Reject a UI change if it is visually impressive in isolation but inconsistent with the system, harms readability/performance, duplicates an existing pattern, or makes existing functionality less discoverable.
