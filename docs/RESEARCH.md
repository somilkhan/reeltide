# ReelTide — Design & Platform Research Notes

**Research date:** 2026-09-13
**Purpose:** Record external principles that materially affect the redesign so future sessions do not need to reconstruct the research from conversation history.

## Apple Liquid Glass

### Core principles

Apple's current Liquid Glass documentation describes the material as a dynamic layer combining optical glass properties with fluidity. The guidance emphasizes hierarchy, harmony, consistency, adaptable layouts, navigation/search conventions, and careful use of custom Liquid Glass.

Source: https://developer.apple.com/documentation/technologyoverviews/liquid-glass

### Use material selectively

Apple's current Materials guidance explicitly recommends using Liquid Glass effects sparingly and limiting them to important functional elements. It distinguishes a more legible `regular` material from a more translucent `clear` material intended for visually rich backgrounds. The regular treatment is preferable where text/controls need stronger separation; clear treatment is appropriate when the underlying media should remain prominent, provided contrast remains sufficient.

Source: https://developer.apple.com/design/human-interface-guidelines/materials

### Structure and continuity

Apple's current design-system guidance emphasizes a functional layer floating above content, spatial relationships between related surfaces, continuity between a source control and contextual surfaces, and scroll-edge effects as a way to clarify the boundary between content and pinned controls rather than as decoration.

Source: https://developer.apple.com/videos/play/wwdc2025/356/

### Accessibility

Apple's Liquid Glass guidance includes adaptations for reduced transparency, increased contrast, and reduced motion. ReelTide must therefore have material fallbacks and simplified motion rather than treating blur/translucency as mandatory visual effects.

Sources:
- https://developer.apple.com/videos/play/wwdc2025/219/
- https://developer.apple.com/documentation/technologyoverviews/adopting-liquid-glass

## Android / Jetpack Compose

### Edge-to-edge

Current Android guidance says edge-to-edge is enforced for apps targeting Android 15/API 35 and above. Interactive content must respect system-bar and gesture insets. ReelTide's redesign must treat edge-to-edge and insets as core layout behavior.

Sources:
- https://developer.android.com/develop/ui/compose/system/setup-e2e
- https://developer.android.com/design/ui/mobile/guides/layout-and-content/edge-to-edge

### Android 16 predictive back

Current Android 16 behavior changes make predictive back the default for apps targeting Android 16/API 36 and above; legacy back interception paths may no longer receive the same callbacks. The redesign must avoid introducing navigation behavior that conflicts with the supported back-navigation APIs.

Source: https://developer.android.com/about/versions/16/behavior-changes-16

### Compose blur

Current Compose documentation provides a `Modifier.blur` API with Android 12+ support for uniform blur and newer support for spatially varying blur. The API renders through a separate graphics layer, so blur has measurable rendering implications. ReelTide should use bounded, purposeful blur instead of applying it indiscriminately to lists or full-screen surfaces.

Source: https://developer.android.com/reference/kotlin/androidx/compose/ui/draw/blur.modifier

## Design decisions derived from research

1. Liquid Glass is a hierarchy layer, not the default background of every component.
2. Media remains the primary visual content; controls should float above it without obscuring important artwork.
3. Text-heavy surfaces need stronger material separation than media-floating controls.
4. Scroll-edge treatment should clarify actual control/content boundaries, not decorate every scroll container.
5. Navigation should be visually integrated into the material hierarchy and should not compete with Home content.
6. Reduced motion/transparency and increased contrast require intentional fallbacks.
7. Edge-to-edge/insets are architectural concerns and must be handled in the shell/component system.
8. Blur must be bounded and profiled; visual fidelity does not justify uncontrolled rendering cost.
9. Native Android/Compose primitives should reproduce the interaction principles rather than importing web-specific animation systems.
