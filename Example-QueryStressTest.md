**Server (y-cruncher) Start Command:**

`y-cruncher.exe slave:0 -port:30000 -ctype:json`

-----

**Client (GUI) Request:**

```
{
    "Action": "QueryStressTest",
    "StressTest": {
        "AllocateLocally": "true",
        "LogicalCores": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27],
        "TotalMemory": 85713327360,
        "Seconds": 120,
        "StopOnError": "true",
        "Tests": [
            "BKT",
            "BBP",
            "SFT",
            "N64",
            "HNT",
            "VST"
        ]
    }
}
```

-----

**Server (y-cruncher) Response:**

```
{
    "QueryResponse": "StressTest",
    "Parameters": {
        "AllocateLocally": "true",
        "LogicalCores": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27],
        "TotalMemory": 85901909760,
        "Seconds": 120,
        "StopOnError": "true",
        "Tests": [
            "BKT",
            "BBP",
            "SFT",
            "N64",
            "HNT",
            "VST"
        ]
    },
    "Renderer": {
        "AllocateLocally": {
            "Value": "true",
            "DisplayText": "Local - Memory allocated from local thread."
        },
        "LogicalCores": {
            "Value": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27],
            "DisplayText": "0-27"
        },
        "TotalMemory": {
            "Value": 85901909760,
            "DisplayText": "80.0 GiB"
        },
        "Seconds": {
            "Value": 120,
            "DisplayText": "120 seconds"
        },
        "StopOnError": {
            "Value": "true",
            "DisplayText": "Enabled"
        },
        "Tests": [
            {
                "Value": "BKT",
                "DisplayTag": "BKT",
                "DisplayName": "Basecase + Karatsuba",
                "Enabled": "true",
                "MemoryPerThread": {
                    "Value": 28160,
                    "DisplayText": "27.5 KiB"
                },
                "DisplayComponent": "Scalar Integer",
                "Scale": {
                    "Value": 2,
                    "DisplayText": "-|--------"
                }
            },
            {
                "Value": "BBP",
                "DisplayTag": "BBP",
                "DisplayName": "BBP Digit Extraction",
                "Enabled": "true",
                "MemoryPerThread": {
                    "Value": 0,
                    "DisplayText": "small"
                },
                "DisplayComponent": "AVX512 Float",
                "Scale": {
                    "Value": 1,
                    "DisplayText": "|---------"
                }
            },
            {
                "Value": "SFT",
                "DisplayTag": "SFT",
                "DisplayName": "Small In-Cache FFT",
                "Enabled": "true",
                "MemoryPerThread": {
                    "Value": 259328,
                    "DisplayText": " 253 KiB"
                },
                "DisplayComponent": "AVX512 Float",
                "Scale": {
                    "Value": 2,
                    "DisplayText": "-|--------"
                }
            },
            {
                "Value": "FFT",
                "DisplayTag": "FFT",
                "DisplayName": "Fast Fourier Transform",
                "Enabled": "false",
                "DisplayComponent": "AVX512 Float",
                "Scale": {
                    "Value": 10,
                    "DisplayText": "---------|"
                }
            },
            {
                "Value": "N32",
                "DisplayTag": "N32",
                "DisplayName": "Classic NTT (32-bit)",
                "Enabled": "false",
                "DisplayComponent": "AVX512 Integer",
                "Scale": {
                    "Value": 8,
                    "DisplayText": "-------|--"
                }
            },
            {
                "Value": "N64",
                "DisplayTag": "N64",
                "DisplayName": "Classic NTT (64-bit)",
                "Enabled": "true",
                "MemoryPerThread": {
                    "Value": 2856825792,
                    "DisplayText": "2.66 GiB"
                },
                "DisplayComponent": "AVX512 Integer",
                "Scale": {
                    "Value": 7,
                    "DisplayText": "------|---"
                }
            },
            {
                "Value": "HNT",
                "DisplayTag": "HNT",
                "DisplayName": "Hybrid NTT",
                "Enabled": "true",
                "MemoryPerThread": {
                    "Value": 2955228544,
                    "DisplayText": "2.75 GiB"
                },
                "DisplayComponent": "Mixed Workload",
                "Scale": {
                    "Value": 6,
                    "DisplayText": "-----|----"
                }
            },
            {
                "Value": "VST",
                "DisplayTag": "VST",
                "DisplayName": "Vector Transform",
                "Enabled": "true",
                "MemoryPerThread": {
                    "Value": 3023300800,
                    "DisplayText": "2.82 GiB"
                },
                "DisplayComponent": "AVX512 Float",
                "Scale": {
                    "Value": 8,
                    "DisplayText": "-------|--"
                }
            },
            {
                "Value": "C17",
                "DisplayTag": "C17",
                "DisplayName": "Code 17 Experiment",
                "Enabled": "false",
                "DisplayComponent": "AVX512 Mixed",
                "Scale": {
                    "Value": 7,
                    "DisplayText": "------|---"
                }
            }
        ]
    }
}
```

