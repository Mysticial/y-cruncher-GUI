### Sample Request:

```
{
    "Action": "CustomCompute",
    "Option": "Query",
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
            "OutputEnable": "true",
            "DigitsPerFile": 10000000
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
    }
}
```

### Sample Response:

```
{
    "Response": "CustomComputeQuery",
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
            "OutputEnable": "true",
            "DigitsPerFile": 10000000
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
                "Value": "e",
                "DisplayText": "e",
                "DisplayColor": "P"
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
                "OutputEnable": {
                    "Value": "true",
                    "DisplayText": "Enabled"
                },
                "DigitsPerFile": {
                    "Value": 10000000,
                    "DisplayText": "Compress with 10,000,000 digits per file."
                }
            },
            "Size": {
                "Value": 836293636,
                "DisplayText": " 798 MiB"
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
                "Value": 5413782072,
                "DisplayText": "5.04 GiB"
            }
        },
        "Warnings": [],
        "Errors": []
    }
}
```
