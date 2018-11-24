### Sample Request:

```
{
    "Action": "StressTest",
    "Option": "Query",
    "StressTest": {
        "AllocateLocally": "false",
        "LogicalCores": [0, 1, 2, 3, 4, 5, 6, 7],
        "TotalMemory": 10737418240,
        "Seconds": 10,
        "StopOnError": "true",
        "Tests": [
            "SFT",
            "FFT",
            "N64",
            "VST"
        ]
    }
}
```

### Sample Response:

```
{
    "Response": "StressTestQuery",
    "Parameters": {
        "AllocateLocally": "false",
        "LogicalCores": [0, 1, 2, 3, 4, 5, 6, 7],
        "TotalMemory": 10737418240,
        "Seconds": 10,
        "StopOnError": "true",
        "Tests": [
            "SFT",
            "FFT",
            "N64",
            "VST"
        ]
    },
    "Renderer": {
        "AllocateLocally": {
            "Value": "false",
            "DisplayText": "Interleaved - Memory interleaved across all nodes."
        },
        "LogicalCores": {
            "Value": [0, 1, 2, 3, 4, 5, 6, 7],
            "DisplayText": "0-7"
        },
        "TotalMemory": {
            "Value": 10737418240,
            "DisplayText": "10.0 GiB"
        },
        "Seconds": {
            "Value": 10,
            "DisplayText": "10 seconds"
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
                "Enabled": "false",
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
                "Enabled": "false",
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
                "Enabled": "true",
                "MemoryPerThread": {
                    "Value": 333459048,
                    "DisplayText": " 318 MiB"
                },
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
                    "Value": 1217375360,
                    "DisplayText": "1.13 GiB"
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
                "Enabled": "false",
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
                    "Value": 1218136704,
                    "DisplayText": "1.13 GiB"
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
