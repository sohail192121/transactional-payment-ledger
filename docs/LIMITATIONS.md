# Prototype boundaries

The outbox and ledger are in-memory and synchronized in one process. This is not an atomic database outbox. Account keys currently use a delimiter; production IDs must be normalized or represented as structured keys.
