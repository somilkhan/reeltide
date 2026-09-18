# Smart Mechanics Workflow

**Purpose:** persistent operating contract for continuous work on ReelTide.

This document defines **HOW the agent works**. The roadmap defines **WHAT is being built**. `PROJECT_STATE.md` defines **WHERE work currently is**.

## 1. Core Principle

The conversation is not the project's memory. The repository is.

At the start of every meaningful work cycle, reconstruct the actual state from:

`AGENTS.md` → `PROJECT_STATE.md` → roadmap → UI/UX/design/skill guidance → actual code → git/build/test evidence.

Never claim work happened because it was mentioned in chat.

## 2. Continuous Execution

The agent operates continuously unless explicitly stopped.

For each cycle:

`INSPECT → UNDERSTAND → PLAN → IMPLEMENT → INTEGRATE → BUILD → TEST → VERIFY → REGRESSION CHECK → UPDATE STATE → CONTINUE`

A completed micro-task is not a stopping point. After verification, immediately select the next logical incomplete task within the active phase.

Do not ask “what next?” when the roadmap, state, or repository makes the next action reasonably determinable.

## 3. Start / Resume Semantics

### START
Initialize or reconstruct the project state, then execute the highest-priority incomplete work.

### CONTINUE
Verify the current state and continue execution. Do not merely provide a plan.

### RELOAD
Treat conversation memory as untrusted. Re-read the control documents, inspect git/source, reconcile state, correct stale state, and resume execution.

### STATUS
Inspect actual repository state and report DONE / IN PROGRESS / BLOCKED / REMAINING / RISKS / NEXT ACTION. Do not modify implementation unless necessary to correct state documentation.

### PAUSE
Persist the exact state, verification evidence, unfinished work, blockers, and next resume action, then stop implementation.

### REVIEW
Audit the implementation against the roadmap and design authorities. Look for omissions, regressions, stale code, incomplete integration, failed approaches, and quality problems.

## 4. Work Selection

Choose work in this order unless evidence requires otherwise:

1. Critical build/runtime blocker.
2. Regression or broken core functionality.
3. Current phase's highest-risk dependency.
4. Work required to unblock multiple downstream tasks.
5. Highest-value incomplete roadmap item.
6. Cleanup and polish after functional stability.

Do not optimize for the number of files changed. Optimize for coherent, verified progress.

## 5. Scope Discipline

Preserve existing business logic, data flow, state ownership, navigation semantics, and working functionality unless the roadmap explicitly changes them.

Replace obsolete presentation architecture coherently instead of creating a second competing UI system.

Do not perform unrelated refactors merely because they are interesting.

## 6. Failure Protocol

When an approach fails:

1. Capture the exact failure.
2. Inspect surrounding implementation and dependencies.
3. Determine the root cause rather than patching symptoms.
4. Record the failed approach and reason.
5. Choose a materially different or corrected strategy.
6. Implement the replacement.
7. Build/test/verify again.
8. Continue.

Never repeat an approach that has demonstrated the same root failure without new evidence explaining why it should now work.

## 7. Verification Rules

“Edited” is not “implemented.”

“Compiles” is not “complete.”

“One screen works” is not “project complete.”

A task becomes DONE only when its implementation is integrated and the relevant behavior has been verified. Verification must state what was actually checked and by what mechanism.

Never fabricate screenshots, device results, test results, CI results, visual verification, or successful behavior.

## 8. UI / UX / Design Gate

For every visual task, consult the project design authorities before implementation:

- `docs/UI.md` — visual system and component rules.
- `docs/UX.md` — interaction and information architecture.
- `docs/DESIGN.md` — art direction, taste, hierarchy, and quality bar.
- `docs/SKILL.md` — implementation heuristics and expert workflow.

The design system must be coherent across the entire product, not individually attractive screens assembled without a system.

Design decisions must balance visual quality with readability, accessibility, motion safety, rendering cost, and existing functionality.

## 9. Design Quality Bar

The target is a premium, intentional, monochrome Apple-inspired Liquid Glass experience for Android—not a generic Material reskin and not an imitation assembled from random glass effects.

Use hierarchy, restraint, typography, spacing, depth, translucency, blur, borders, lighting, and motion deliberately.

If a visual effect does not improve hierarchy or interaction, remove it.

## 10. State Ledger

After every meaningful milestone, update `docs/PROJECT_STATE.md`.

At minimum record:

- current phase;
- objective;
- DONE;
- IN PROGRESS;
- REMAINING;
- BLOCKED;
- regressions;
- known risks;
- failed approaches;
- decisions;
- verification evidence;
- last verified commit;
- next resume action.

State must be factual and reconstructable.

## 11. Git Discipline

The active redesign branch is `ui/2026-liquid-glass-redesign`.

`master` is the baseline.

`ui/2026-modern-redesign` is historical/legacy and must not be used as the implementation base.

Keep commits coherent and attributable to meaningful milestones. Never overwrite the baseline merely to make the redesign easier.

## 12. Long-Conversation Recovery

When context becomes large or uncertain, do not attempt to reconstruct the entire history mentally.

Use:

`AGENTS.md → PROJECT_STATE.md → WORKFLOW → ROADMAP → relevant design docs → git → code → verification`

Then continue.

## 13. Genuine Blockers

Stop only when continuing requires something genuinely unavailable, such as credentials, permissions, an inaccessible external resource, device-only evidence unavailable through current tooling, or an ambiguous product decision whose outcome cannot be inferred safely.

Record the exact blocker and continue all independent work.

## 14. Completion Gate

The overall project is complete only when roadmap requirements are satisfied and applicable functional, technical, visual, accessibility, performance, build, test, interaction, and regression verification has evidence.

Do not use “complete” as a synonym for “current task finished.”
