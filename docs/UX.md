# ReelTide UX System

This document defines interaction and information-architecture rules for the Liquid Glass redesign.

## Core Principle

The redesign changes presentation, not the user's mental model or existing business behavior unless the roadmap explicitly calls for a UX change.

## Information Hierarchy

Every screen must make the primary task obvious. Secondary actions should remain discoverable without competing with the primary action.

Use hierarchy through placement, scale, typography, material strength, and motion—not through arbitrary color.

## Navigation

Navigation must remain predictable. The selected destination must always be obvious. Back behavior must preserve the existing navigation contract.

Rapid taps, interrupted animations, scrolling, restoration, keyboard visibility, and narrow screens must not produce inconsistent navigation state.

## Interaction States

Interactive controls should communicate:

- available
- pressed
- selected
- focused
- disabled
- loading
- error

State transitions should be understandable even with motion disabled.

## Gesture and Motion

Motion follows user intent. Direct manipulation should feel responsive and spring-like; navigation transitions should communicate spatial continuity.

Animations must be interruptible and must not delay core actions unnecessarily.

## Content Browsing

Media browsing should prioritize artwork, title, relevance, progress, and actionable information. Avoid decorative UI that competes with content.

Lists and rails must remain efficient and predictable while scrolling.

## Search

Search should make the input state, query, results, loading state, empty state, and error state unambiguous. Keyboard behavior must not obscure essential content or actions.

## Details and Playback

Primary actions such as play/resume must be easy to locate. Metadata should be scannable. Existing playback and resume semantics must remain intact.

## Sheets and Dialogs

Use sheets for contextual tasks that preserve the user's place. Use dialogs for focused decisions requiring attention. Dismissal and cancellation must be predictable.

## Feedback

Every asynchronous operation needs an understandable visual state where applicable. Avoid silent loading, ambiguous disabled controls, and transitions that look like failures.

## Accessibility

Touch targets, focus order, semantics, dynamic text, contrast, and reduced-motion behavior are functional requirements, not polish items.

## UX Quality Gate

Reject a design if the user must guess what is interactive, selected, loading, or actionable. A visually minimal interface is successful only when its interaction model remains obvious.
