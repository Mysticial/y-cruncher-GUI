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
y-cruncher will then respond the same set of parameters along with the renderer for the parameters.

If the parameters that are sent to y-cruncher are invalid, y-cruncher will either:
1. Sanitize the parameters so that they are valid. Thus the `Parameters` portion of the response will be different from what was initially sent to y-cruncher.
2. y-cruncher will respond with an [`Error`](StatusUpdates.md#Error) saying what's wrong.

The expected usecase here is that when the user updates a parameter in the UI, the GUI application will update its view of the object model and send it to y-cruncher with the `Query` request. y-cruncher then responds with an updated set of parameters as well has a new renderer so that the GUI can redraw the UI to fit the update.

Example: [CustomCompute-SampleQuery.md](CustomCompute-SampleQuery.md)


### Run

    {
        "Action": "CustomCompute",
        "Option": "Run",
        "CustomCompute": { **parameters** }
    }

Runs a computation with the specified parameters.

This causes y-cruncher to enter the "Custom Compute" scope and start the computation.


-----
## In Scope Commands:

The following commands can be invoked while a computation is running. (inside the Custom Compute scope)
 - [`QueryMicroStatus`](#QueryMicroStatus)
 - [`Continue`](#Continue)

### QueryMicroStatus

    {
        "Action": "QueryMicroStatus"
    }

Requests a microstatus update for the current section of the computation.

(TODO: What is a micro vs. macro status updates?)

Due the asynchronous nature of such updates, y-cruncher will include a sequence # ID for all microstatus updates so they can be mapped to its parent macro-section.
If y-cruncher receives a request for a microstatus update while it is not inside a macro-section that has such updates, it will ignore the request.

### Continue

    {
        "Action": "Continue"
    }

Wake up the computation from a pause event. Pause events can happen with certain warnings or errors.
