**Server (y-cruncher) Start Command:**

`y-cruncher.exe slave:0 -port:30000 -ctype:json`

-----

**Client (GUI) Request:**

```
{
    "Action": "QueryCustomCompute",
    "CustomCompute": {
        "Constant": {
            "Constant": "e",
            "Algorithm": "exp(1)"
        },
        "ComputeSize": {
            "DecimalDigits": 1000000000,
            "EnableHexDigits": "true"
        },
        "Output": {
            "Path": "",
            "DigitsPerFile": 0
        },
        "OutputVerify": "true",
        "Mode": "ram",
        "Parallelism": {
            "TaskDecomposition": 56,
            "Framework": "pushpool",
            "WorkerThreads": 0,
            "Randomization": "true"
        },
        "Allocator": {
            "Allocator": "mmap",
            "LargePages": "attempt",
            "LockedPages": "attempt"
        }
    }
}
```

-----

**Server (y-cruncher) Response:**

```
{
    "QueryResponse": "CustomCompute",
    "Parameters": {
        "Constant": {
            "Constant": "e",
            "Algorithm": "exp(1)"
        },
        "ComputeSize": {
            "DecimalDigits": 1000000000,
            "EnableHexDigits": "true"
        },
        "Output": {
            "Path": "",
            "DigitsPerFile": 0
        },
        "OutputVerify": "true",
        "Mode": "ram",
        "Parallelism": {
            "TaskDecomposition": 56,
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
                "Value": "e",
                "DisplayText": "e",
                "DisplayColor": "G"
            },
            "Algorithm": {
                "Value": "exp(1)",
                "DisplayText": "Taylor Series of exp(1)"
            }
        },
        "ComputeSize": {
            "DecimalDigits": {
                "Value": 1000000000,
                "DisplayText": "1,000,000,000"
            },
            "HexadecimalDigits": {
                "Value": 830482024,
                "DisplayText": "830,482,024"
            }
        },
        "Output": {
            "Format": {
                "Path": {
                    "Value": "",
                    "DisplayText": "Default Path"
                },
                "DigitsPerFile": {
                    "Value": 0,
                    "DisplayText": "None - Write digits to a text file."
                }
            },
            "Size": {
                "Value": 1830482024,
                "DisplayText": "1.70 GiB"
            }
        },
        "OutputVerify": "true",
        "Mode": {
            "value": "ram",
            "DisplayText": "Ram Only"
        },
        "Parallelism": {
            "TaskDecomposition": {
                "Value": 56,
                "DisplayText": "56"
            },
            "Framework": {
                "Framework": "pushpool",
                "WorkerThreads": 0,
                "Randomization": "true",
                "DisplayText": "Push Pool  ->  56 / ?  (randomization on)"
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
                "Value": 5938771816,
                "DisplayText": "5.53 GiB"
            }
        }
    }
}
```
