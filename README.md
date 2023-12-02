# Transactional Payment Ledger

**Sohail Sharif · Personal engineering project · Core prototype**

## Problem
This project explores balanced entries, idempotency, outbox pattern, reconciliation. It is designed as a focused engineering lab for Java roles.

## Current implementation
Balanced postings, idempotency and an in-memory outbox. See source and executable tests for the exact behavior.

This repository is an initial implementation, not a deployed production system. The full target architecture below is a roadmap. No throughput, uptime, savings or recovery claims have been measured for a production environment.

## Target stack
Java 21, Spring Boot, PostgreSQL

## Architecture
```mermaid
flowchart LR
  n0["Payment request"] --> n1
  n1["idempotency lookup"] --> n2
  n2["balanced ledger transaction"] --> n3
  n3["outbox"] --> n4
  n4["reconciliation"]
```

The diagram describes the target architecture; adapters listed below are not implemented yet.

## Run and verify
Run from this directory. Requires Java 21 on PATH.

```sh
javac Main.java && java Main
```

## Next milestones
- [ ] Spring HTTP adapter
- [ ] database atomicity
- [ ] outbox publisher
- [ ] reconciliation jobs

## Trade-offs
- The domain core is isolated from infrastructure so its invariants can be tested quickly.
- In-memory state makes the prototype reproducible but does not survive restarts or coordinate multiple machines.
- Production authentication, secret management, persistence, metrics and deployment verification remain required.

## Evidence
Tests are executable specifications. Cloud and load-test results are deliberately unclaimed. Record real commands, versions, hardware, dataset, date and results in `docs/VALIDATION.md` before quoting numbers on a resume.

## Interview discussion
Explain the failure boundary, tenant boundary, consistency model, retry behavior, operational signals and why the chosen persistence adapter fits the workload.
