### Sample Request:

```
{
    "Action": "StressTest",
    "Option": "Defaults"
}
```

### Sample Response:

```
{
    "Response": "StressTestDefaults",
    "Parameters": {
        "AllocateLocally": "true",
        "LogicalCores": [0, 1, 2, 3, 4, 5, 6, 7],
        "TotalMemory": 19385882880,
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
            "Value": [0, 1, 2, 3, 4, 5, 6, 7],
            "DisplayText": "0-7"
        },
        "TotalMemory": {
            "Value": 19385882880,
            "DisplayText": "18.1 GiB"
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
                    "Value": 28416,
                    "DisplayText": "27.8 KiB"
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
                "DisplayComponent": "AVX2 Float",
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
                    "Value": 260608,
                    "DisplayText": " 255 KiB"
                },
                "DisplayComponent": "AVX2 Float",
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
                "DisplayComponent": "AVX2 Float",
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
                "DisplayComponent": "AVX2 Integer",
                "Scale": {
                    "Value": 6,
                    "DisplayText": "-----|----"
                }
            },
            {
                "Value": "N64",
                "DisplayTag": "N64",
                "DisplayName": "Classic NTT (64-bit)",
                "Enabled": "true",
                "MemoryPerThread": {
                    "Value": 2423235344,
                    "DisplayText": "2.26 GiB"
                },
                "DisplayComponent": "AVX2 Integer",
                "Scale": {
                    "Value": 4,
                    "DisplayText": "---|------"
                }
            },
            {
                "Value": "HNT",
                "DisplayTag": "HNT",
                "DisplayName": "Hybrid NTT",
                "Enabled": "true",
                "MemoryPerThread": {
                    "Value": 2401648256,
                    "DisplayText": "2.24 GiB"
                },
                "DisplayComponent": "Mixed Workload",
                "Scale": {
                    "Value": 5,
                    "DisplayText": "----|-----"
                }
            },
            {
                "Value": "VST",
                "DisplayTag": "VST",
                "DisplayName": "Vector Transform",
                "Enabled": "true",
                "MemoryPerThread": {
                    "Value": 2420380416,
                    "DisplayText": "2.25 GiB"
                },
                "DisplayComponent": "AVX2 Float",
                "Scale": {
                    "Value": 6,
                    "DisplayText": "-----|----"
                }
            },
            {
                "Value": "C17",
                "DisplayTag": "C17",
                "DisplayName": "Code 17 Experiment",
                "Enabled": "false",
                "DisplayComponent": "AVX2 Mixed",
                "Scale": {
                    "Value": 4,
                    "DisplayText": "---|------"
                }
            }
        ],
        "Warnings": [],
        "Errors": []
    }
}
```
