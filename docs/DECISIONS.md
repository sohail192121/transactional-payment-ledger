# Design decisions

## 001: Domain first
Implement and test the core invariants before adding infrastructure. This keeps failure behavior visible and avoids presenting scaffolding as a finished distributed system.

## 002: Next integration
Spring HTTP adapter. Verify it using integration tests, then update the implementation status in the README.
