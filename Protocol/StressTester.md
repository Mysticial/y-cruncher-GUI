# Stress Tester Scope

This scope represents y-cruncher's Stress-Tester feature.

## Querying Commands:

The following commands can be invoked from the Main Menu Scope:
 - [`Defaults`](#Defaults)
 - [`Query`](#Query)
 - [`Run`](#Run)

### Defaults

    {
        "Action": "StressTest",
        "Option": "Defaults"
    }

Requests y-cruncher to return the default parameters as well as a renderer for those parameters.

Example: [StressTest-SampleDefaults.md](StressTest-SampleDefaults.md)


### Query

    {
        "Action": "StressTest",
        "Option": "Query",
        "StressTest": { **parameters** }
    }

Sends the specified set of stress-tester parameters to y-cruncher.
y-cruncher will then respond with a sanitized version of the parameters along with a renderer for the sanitized parameters.

Example: [StressTest-SampleQuery.md](StressTest-SampleQuery.md)


### Run

    {
        "Action": "StressTest",
        "Option": "Run",
        "StressTest": { **parameters** }
    }

Runs the stress-tester with the specified parameters.

This causes y-cruncher to enter the "Stress Test" scope.


## In Scope Commands:

The following commands can be invoked while the Stress Tester is running. (inside the Stress Test scope)
 - [`StopCleanly`](#StopCleanly)

### StopCleanly

    {
        "Action": "StopCleanly"
    }

Signals the stress test to stop after the finishing the current test.
Once the stress test is stopped, y-cruncher returns to the Main Menu scope.
