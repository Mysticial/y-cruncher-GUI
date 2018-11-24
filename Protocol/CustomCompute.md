# Custom Compute

This scope represents y-cruncher's Custom Compute feature.

Parameters for Custom Compute are documented here: [ParameterSpecs/CustomCompute.md](/ParameterSpecs/CustomCompute.md)

-----
## Querying Commands:

The following commands can be invoked from the Main Menu Scope:
 - [`Defaults`](#Defaults)
 - [`Query`](#Query)
 - [`Run`](#Run)

### Defaults

    {
        "Action": "CustomCompute",
        "Option": "Defaults"
    }

Requests y-cruncher to return the default parameters as well as a renderer for those parameters.

Example: [CustomCompute-SampleDefaults.md](CustomCompute-SampleDefaults.md)


### Query

    {
        "Action": "CustomCompute",
        "Option": "Query",
        "CustomCompute": { **parameters** }
    }

Sends the specified set of custom compute parameters to y-cruncher.
y-cruncher will then respond with a sanitized version of the parameters along with a renderer for the sanitized parameters.

Example: [CustomCompute-SampleQuery.md](CustomCompute-SampleQuery.md)


### Run

    {
        "Action": "CustomCompute",
        "Option": "Run",
        "CustomCompute": { **parameters** }
    }

Runs a computation with the specified parameters.

This causes y-cruncher to enter the "Custom Compute" scope and start the stress test.


-----
## In Scope Commands:

The following commands can be invoked while a computation is running. (inside the Custom Compute scope)
 - [`QueryMicroStatus`](#QueryMicroStatus)

### QueryMicroStatus

    {
        "Action": "QueryMicroStatus"
    }

Requests a microstatus update for the current section of the computation.

(TODO: What is a micro vs. macro status updates?)

Due the asynchronous nature of such updates, y-cruncher will include a sequence # ID for all microstatus updates so they can be mapped to its parent macro-section.
If y-cruncher receives a request for a microstatus update while it is not inside a macro-section that has such updates, it will ignore the request.
