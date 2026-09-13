# ReelTide Engineering + Design Skill

This is the project-specific skill contract. It defines the quality and reasoning expected while implementing the redesign.

## 1. Think in Systems

Do not solve repeated UI problems independently. Identify the shared primitive, token, state model, or architecture causing the pattern and fix it at the correct level.

## 2. Inspect Before Editing

Before changing code, locate the owning state, navigation contract, data source, reusable component, and existing tests. Understand dependencies before replacement.

## 3. Preserve Behavior

Presentation code may be replaced, but business logic, data flow, persistence, navigation semantics, media behavior, and existing functionality must remain intact unless explicitly changed by the roadmap.

## 4. Component Craft

Build reusable primitives with stable APIs and explicit states. Avoid screen-specific copies of components that should be shared.

Prefer composition and existing project conventions over unnecessary abstractions.

## 5. Compose / Android Motion

Use appropriate native primitives: spring animation, transitions, derived state, gesture APIs, nested scroll, shared-element mechanisms where available, and controlled graphics effects.

Avoid importing web-only motion concepts when native Android can provide the same interaction quality more reliably.

## 6. Liquid Glass Engineering

Glass is a hierarchy, not a filter. Control blur radius, alpha, borders, clipping, shadows, and recomposition. Do not put expensive effects into every list item or across the entire screen without evidence that the device can handle them.

## 7. Visual Taste

Evaluate composition before implementation details. Prefer fewer, stronger decisions. Refine optical alignment, hierarchy, spacing, typography, material strength, and motion as one system.

Reject generic Material defaults, arbitrary gradients, inconsistent glass effects, excessive rounded containers, and decorative elements without interaction value.

## 8. Real Content Testing

Do not judge UI only with ideal placeholder content. Check long titles, missing artwork, loading, errors, empty results, varying poster ratios, rapid interaction, and realistic content density.

## 9. Accessibility Is Design

Maintain semantics, usable touch targets, readable contrast, dynamic text support, focus behavior, keyboard behavior, and reduced-motion behavior where supported.

## 10. Performance Is Part of Quality

Watch lazy-list stability, image loading, recomposition, animation allocation, blur cost, translucent overdraw, and frame behavior. Prefer an elegant cheaper effect over an expensive effect with no meaningful UX benefit.

## 11. Debugging Discipline

When something breaks, reproduce or inspect the failure, trace ownership, identify root cause, and fix the subsystem. Do not stack patches on symptoms.

## 12. Continuous Work

A task is not finished when the requested code exists. Integrate it, build/test it, verify it, inspect adjacent behavior, update state, and continue through the logical phase.

## 13. Evidence

Every completion claim should correspond to observable repository/build/test/interaction evidence. If something could not be verified, say exactly what remains unverified.

## 14. Research Discipline

When external references are relevant, research current platform/product patterns before locking a design. Separate inspiration from implementation constraints. Do not claim pixel accuracy without a suitable reference and verification method.

## 15. Final Polish

Before declaring a subsystem complete, inspect it at system level: consistency with neighboring screens, interaction states, edge cases, accessibility, performance, and cleanup. “Looks good” is not a verification method by itself.
