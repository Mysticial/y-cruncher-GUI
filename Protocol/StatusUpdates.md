# Status Updates

This section details the various status updates that y-cruncher (running in Slave Mode) may send back to the client.

Most of these serve no functional purpose other than to inform the client of the progress of an operation or a computation. GUIs acting as clients don't have to imitate y-cruncher's console output. They can do whatever want with these updates.

**Simple Updates:**
- [`Status_Line`](#Status_Line)
- [`Status_ColoredLine`](#Status_ColoredLine)
- [`Status_Time`](#Status_Time)
- [`Data_XXX`](#Data_XXX)
- [`PauseWarning`](#PauseWarning)
- [`Error`](#Error)

**Line Updates:**
- [`Status_LineSectionStart`](#Status_LineSectionStart)
- [`Status_LineSectionAdd`](#Status_LineSectionAdd)
- [`Status_LineSectionEnd`](#Status_LineSectionEnd)

**Micro-section Updates:**
- [`Status_MicroSectionStart`](#Status_MicroSectionStart)
- [`Status_MicroSectionEnd`](#Status_MicroSectionEnd)
- [`Status_MicroStatus`](#Status_MicroStatus)


## Simple Updates

These are basic updates that y-cruncher normally prints to the console.

### Status_Line

This is a single line status update. y-cruncher normally prints these out to the console.
In slave mode, y-cruncher sends the following node to the client.

```
{
    "Status_Line": "Begin Computation:"
}
```

Empty text bodies are common. They simply indicate a line-break.

### Status_ColoredLine

Same as `Status_Line`, but with colors.

```
{
    "Status_Line": {
        "Color" : "G"
        "Text" : "Begin computing Pi..."
    }
}
```

### Status_Time

Typically used to mark the end of a large operation and how long it took.

```
{
    "Status_Time": {
        "Name": "Pi",
        "TimeMilliseconds": 139371
    }
}
```

### Data_XXX

This is custom data that the operation may send back to the client.

The following example is what y-cruncher sends to the client at the end of a computation.

```
{
    "Data_ComputationOutput": {
        "Dates": {
            "Start": "Sat Nov 24 18:58:59 2018",
            "End": "Sat Nov 24 19:01:40 2018"
        },
        "Times": {
            "ComputeTimeMillis": 149940,
            "WallTimeMillis": 160536
        },
        "DigitStats": {
            "LastDecDigits": {
                "Offset": 999999900,
                "Digits": "6434543524276655356743570219396394581990548327874671398682093196353628204612755715171395115275045519"
            },
            "LastHexDigits": {
                "Offset": 830481950,
                "Digits": "ae333f43c7115484995f2006caac11c2976355b709ebae401d675375bb3afc1a62f13dffa2"
            },
            "HiddenDecDigits": {
                "Offset": 1000000000,
                "Digits": 100,
                "SHA256": "a1f734e8ac3306119e7742de73e15c69d0378559e8844fc3619116cab82db3db"
            },
            "DecStats": {
                "Hash": 807692683230452073,
                "Counts": [99993942, 99997334, 100002410, 99986911, 100011958, 99998885, 100010387, 99996061, 100001839, 100000273]
            },
            "HexStats": {
                "Hash": 2286991717319275474,
                "Counts": [51908485, 51902158, 51911711, 51913598, 51889633, 51906433, 51906705, 51908361, 51903207, 51899870, 51905851, 51897520, 51910048, 51908765, 51903537, 51906142]
            }
        }
    }
}
```

### PauseWarning

When y-cruncher hits an important warning or error that warrants pausing the operation or computation, it will send this to the client.

```
{
    "PauseWarning": {
        "Name" : "Unable to initialize XXX, expect performance degradation."
    }
}
```

To unpause the operation/computation, the client needs to send a `Continue` back to y-cruncher.

```
{
    "Action": "Continue"
}
```

### Error

The generic error message. Exceptions that propagate all the way up the execution stack will be caught and sent to the client verbatim. It is currently undefined what state y-cruncher will be after such an error.

y-cruncher's error handling is "not great" - to put it lightly. The undefined aspects state of y-cruncher after a generic error message is mostly a reflection of that.

```
{
    "Error": {
        "Type": "InvalidParametersException",
        "Message": "Unknown command: QueryMicroStatus"
    }
}
```

## Line Updates

This represents a single line which gets appended to as progress is made.

### Status_LineSectionStart

### Status_LineSectionAdd

### Status_LineSectionEnd


## Micro-section Updates

These are the updates where the status line is repeatedly overwritten as progress is made. Internally these are called "Microstatus Updates".

They are called "micro" because each individual update is small and relatively unimportant. So instead of printing out a new line for each update, it's better (aesthetically) to just have a mutating status line that updates in real time.

Unlike the other status updates on this page, microstatus updates do not happen automatically. They need to be requested by the client which y-cruncher will respond to with the current status. y-cruncher normally does these updates once per second. In Slave Mode, the client takes control of when to poll the updates.

To date, y-cruncher has only ever used single-line microstatus updates. But both y-cruncher and the protocol here supports a much more generic generalization that uses tree to represent a status. This allows the status to be polled within parallel sections of a computation. If and when this more generic structure is used, y-cruncher will print it out in the console as a multi-line microstatus where all the lines update in real time.



### Status_MicroSectionStart

### Status_MicroSectionEnd

### Status_MicroStatus


