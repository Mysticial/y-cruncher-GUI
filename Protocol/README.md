# Slave Mode Protocol

This is the full documentation and specification for Slave Mode.

## Background

Slave Mode is a feature that allows y-cruncher to be controlled over TCP.
This makes it possible to build GUI wrappers around y-cruncher while keeping y-cruncher stand-alone.

The way Slave Mode works is that once enabled, it turns y-cruncher into a TCP server.
Clients (the wrapper application) can then connect to it. Then it can send commands (RPCs) and receive responses.

These commands give access to much of y-cruncher functionality, thus allowing a GUI to provide most of y-cruncher's functionality to a user without them needing to interact with a console window.


## Startup

In order to use Slave Mode, y-cruncher must be started with specific parameters.

    {program} slave:{version}
              [-port:{port}]
              [-ctype:{type}]
              [-allowremote:{value}]
              [-printtraffic:{value}]

    y-cruncher slave:0 -port:20000
    y-cruncher slave:0 -port:40000 -ctype:json -allowremote:1 -printtraffic:1

See the [command lines](SlaveMode%20Command%20Lines.txt) for more details.

Once started in Slave Mode, y-cruncher will accept incoming TCP connections at the specified port number.
While not officially supported, it will accept multiple connections simultaneously, but it won't distinguish them from another.
(In other words, there is no concept of multiple independent sessions.)
Messages sent by y-cruncher will be sent to all connected clients.

Once started in Slave Mode, y-cruncher will continue running until it is either terminated, or it receives an exit command.
It will not automatically terminate when all clients disconnect.


## Communication Protocol

All data sent to and from y-cruncher is plain text JSON* in UTF-8.

Messages start with 4 bytes followed by the JSON message body.
The 4 bytes is a little-endian 32-bit integer indicating the size of the message in bytes.
This size includes the 4 byte length header.

*By default y-cruncher uses JSON since it's a standard with many available parsers and generators.
But y-cruncher can also be configured to use its custom ACFG format that is used for its config files.


## Scopes

(todo)

- Main Menu
- Stress Tester
- Custom Compute


## Main Menu Scope

This is the scope that y-cruncher enters when it is first started up.

Commands valid from the Main Menu scope:
 - [`Exit`](#Exit)
 - `ExitNow`
 - `BinaryInfo`
 - `StressTest`
 - `CustomCompute`

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




