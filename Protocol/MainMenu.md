# Main Menu Scope

This is the scope that y-cruncher enters when it is first started up.

Commands valid from the Main Menu scope:
 - [`Exit`](#Exit)
 - [`ExitNow`](#ExitNow)
 - [`BinaryInfo`](#BinaryInfo)
 - [`StressTest`](#StressTest)
 - [`CustomCompute`](#CustomCompute)

### Exit:

    {
        "Action" : "Exit"
    }

This causes y-cruncher to exit from this scope and gracefully terminate the process.

### ExitNow:

    {
        "Action" : "ExitNow"
    }

A stronger version of Exit. This causes y-cruncher to stop now. This will work in any scope, not just the Main Menu scope.
This stronger exit will not perform any cleanup and is similar to closing the console window.

### BinaryInfo

    {
        "Action": "BinaryInfo"
    }

This causes y-cruncher to send a response containing metadata and version information about itself.

y-cruncher will respond with something like this:

    {
        "Response": "BinaryInfo",
        "BinaryInfo": {
            "VersionNumber": "0.7.7",
            "BuildNumber": "9483-130",
            "BuildSuffix": " (AV)",
            "VersionString": "0.7.7 Build 9483-130 (AV)",
            "BuildName": "Windows/13-HSW ~ Airi",
            "InstructionSet": "Haswell (x64 AVX2)"
        }
    }


### StressTest

    {
        "Action": "StressTest",
        "Option": ...
    }

Sends a command related to the Stress Tester.

The `Option` field can be one of:
 - `Defaults`
 - `Query`
 - `Run`


### CustomCompute

    {
        "Action": "CustomCompute",
        "Option": ...
    }

Sends a command related to the Custom Compute feature.

The `Option` field can be one of:
 - `Defaults`
 - `Query`
 - `Run`
