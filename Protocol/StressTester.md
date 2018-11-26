# Stress Tester

This scope represents y-cruncher's Stress-Tester feature.

Parameters for the stress tester are documented here: [ParameterSpecs/StressTester.md](/ParameterSpecs/StressTester.md)

-----
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
y-cruncher will then respond the same set of parameters along with the renderer for the parameters.

If the parameters that are sent to y-cruncher are invalid, y-cruncher will either:
1. Sanitize the parameters so that they are valid. Thus the `Parameters` portion of the response will be different from what was initially sent to y-cruncher.
2. y-cruncher will respond with an [`Error`](StatusUpdates.md#Error) saying what's wrong.

The expected usecase here is that when the user updates a parameter in the UI, the GUI application will update its view of the object model and send it to y-cruncher with the `Query` request. y-cruncher then responds with an updated set of parameters as well has a new renderer so that the GUI can redraw the UI to fit the update.

Example: [StressTest-SampleQuery.md](StressTest-SampleQuery.md)


### Run

    {
        "Action": "StressTest",
        "Option": "Run",
        "StressTest": { **parameters** }
    }

Runs the stress-tester with the specified parameters.

This causes y-cruncher to enter the "Stress Test" scope and start the stress test.


-----
## In Scope Commands:

The following commands can be invoked while the Stress Tester is running. (inside the Stress Test scope)
 - [`StopCleanly`](#StopCleanly)

### StopCleanly

    {
        "Action": "StopCleanly"
    }

Signals the stress test to stop after the finishing the current test.
Once the stress test is stopped, y-cruncher returns to the Main Menu scope.
