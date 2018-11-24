### Sample Request:

```
{
    "Action": "CustomCompute",
    "Option": "Defaults"
}
```

### Sample Response:

```
{
    "Response": "CustomComputeDefaults",
    "Parameters": {
        "Constant": {
            "Constant": "pi",
            "Algorithm": "chudnovsky"
        },
        "ComputeSize": {
            "DecimalDigits": 50000000,
            "EnableHexDigits": "true"
        },
        "Output": {
            "Path": "",
            "OutputEnable": "true",
            "DigitsPerFile": 0
        },
        "OutputVerify": "true",
        "Mode": "ram",
        "Parallelism": {
            "TaskDecomposition": 8,
            "Framework": "pushpool",
            "WorkerThreads": 0,
            "Randomization": "true"
        },
        "Allocator": {
            "Allocator": "mmap",
            "LargePages": "attempt",
            "LockedPages": "attempt"
        }
    },
    "Renderer": {
        "Constant": {
            "Constant": {
                "Value": "pi",
                "DisplayText": "Pi",
                "DisplayColor": "G"
            },
            "Algorithm": {
                "Value": "chudnovsky",
                "DisplayText": "Chudnovsky (1989)"
            }
        },
        "ComputeSize": {
            "DecimalDigits": {
                "Value": 50000000,
                "DisplayText": "50,000,000"
            },
            "HexadecimalDigits": {
                "Value": 41524102,
                "DisplayText": "41,524,102"
            }
        },
        "Output": {
            "Format": {
                "Path": {
                    "Value": "",
                    "DisplayText": "Default Path"
                },
                "OutputEnable": {
                    "Value": "true",
                    "DisplayText": "Enabled"
                },
                "DigitsPerFile": {
                    "Value": 0,
                    "DisplayText": "None - Write digits to a text file."
                }
            },
            "Size": {
                "Value": 91524102,
                "DisplayText": "87.3 MiB"
            }
        },
        "OutputVerify": "true",
        "Mode": {
            "value": "ram",
            "DisplayText": "Ram Only"
        },
        "Parallelism": {
            "TaskDecomposition": {
                "Value": 8,
                "DisplayText": "8"
            },
            "Framework": {
                "Framework": "pushpool",
                "WorkerThreads": 0,
                "Randomization": "true",
                "DisplayText": "Push Pool  ->  8 / ?  (randomization on)"
            }
        },
        "Allocator": {
            "Allocator": {
                "Value": "mmap",
                "DisplayText": "WinAPI VirtualAlloc()"
            },
            "LargePages": {
                "Value": "attempt",
                "DisplayText": "Attempt"
            },
            "LockedPages": {
                "Value": "attempt",
                "DisplayText": "Attempt"
            }
        },
        "Memory": {
            "RequiredRam": {
                "Value": 406683800,
                "DisplayText": " 388 MiB"
            }
        },
        "Warnings": [],
        "Errors": []
    }
}
```
