# Refactoring Notes

| # | Problem found | Improvement | Benefit |
|---|---|---|---|
| 1 | Duplicate code: every employee class repeated its own "days > limit" check | One `validateRequest()` in `Employee`; subclasses only supply `getMaxDaysPerRequest()` | One place to change a rule |
| 2 | Magic numbers (24, 10, 5, 2 ...) typed inside methods | Named constants `ANNUAL_LEAVE` and `MAX_DAYS_PER_REQUEST` | Rules are easy to find |
| 3 | Errors reported with `System.out.println` and `null` / `false` returns | Custom checked `InvalidLeaveException` | Callers must handle errors; easier to test |
| 4 | Request status was a `String` (typos possible) | `LeaveStatus` enum with controlled transitions in `LeaveRequest` | Invalid statuses are impossible |
| 5 | Approval logic lived in the ordinary `Employee` class, so anyone could approve | `approveLeave()` / `rejectLeave()` moved to `Manager`; `Employee` made abstract | Each class has one clear responsibility |

Other changes: renamed unclear members, made `deductLeave()` package-private so only the approval workflow changes the balance, and re-checked the balance at approval time.
