# ReelTide Agent Entry Point

This repository is an ongoing engineering project. Do not rely on conversation history as the project's source of truth.

## Before Any Project Work

1. Inspect the current branch and git state.
2. Read `docs/PROJECT_STATE.md`.
3. Read `docs/SMART_MECHANICS_WORKFLOW.md`.
4. Read `docs/LIQUID_GLASS_REDESIGN_ROADMAP.md`.
5. For UI/UX/design work, read the applicable `docs/UI.md`, `docs/UX.md`, `docs/DESIGN.md`, and `docs/SKILL.md`.
6. Inspect the actual implementation relevant to the current task.
7. Reconcile documented state with repository evidence before acting.

## Execution Rule

This is continuous work. Do not stop after a small edit, isolated component, compilation, or single passing test. Complete the current logical subsystem, integrate it, build/test, verify behavior, check regressions, update project state, and continue to the next highest-priority incomplete task.

Do not ask what to do next when the roadmap and repository provide enough information. Stop only for a genuine external blocker or an explicit user stop/change of direction.

## Truth Hierarchy

1. Actual repository/code/git/build/test evidence
2. `docs/PROJECT_STATE.md`
3. `docs/LIQUID_GLASS_REDESIGN_ROADMAP.md`
4. Other project documentation
5. Conversation memory

If documentation and reality disagree, verify reality and update the state document.

## Design Authority

The Liquid Glass redesign must follow the project's UI, UX, design-direction, and skill documents. Maintain product-level visual taste: coherent hierarchy, restraint, material depth, typography, motion, accessibility, and performance. Do not settle for generic Material defaults, template layouts, arbitrary gradients, or inconsistent AI-looking styling.

## Recovery

`RELOAD` means: ignore conversational assumptions, reconstruct the project from repository evidence and control documents, correct stale state, then resume execution.

`CONTINUE` means: verify current state and keep executing.

`STATUS` means: inspect and report actual state without pretending work was performed.

`PAUSE` means: persist the exact current state and stop.

`REVIEW` means: audit implementation against the roadmap and identify omissions, regressions, and failed approaches.

Always leave the repository in a reconstructable state after meaningful work.
