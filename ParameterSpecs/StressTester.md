Stress Tester
=====

The parameters object model for the stress tester is identical to the configuration files that y-cruncher generates when you save the settings.
But for the GUI, it will be in standard JSON instead of the custom thing that y-cruncher does.

**Example:**
```
{
    "Action": "StressTest",
    "StressTest": {
        "AllocateLocally": "true",
        "LogicalCores": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15],
        "TotalMemory": 30881744640,
        "Seconds": 120,
        "StopOnError": "true",
        "Tests": [
            "BKT",
            "BBP",
            "SFT",
            "FFT",
            "N32",
            "N64",
            "HNT",
            "VST",
            "C17"
        ]
    }
}
```

Currently, the exact structure of the root element as well as the existance of the `Action` element isn't completely decided.
This example was translated directly from the runnable .ini config files which is structured this way.
It may change later as is fit for the GUI.

-----

### `AllocateLocally`: Memory Mode

Valid values are `true` or `false`.

When `true`, each thread allocates its own memory that is bound to the same NUMA node.
This minimizes inter-node traffic for greater CPU stress. But it does not stress the cross-node interconnects much.

When `false`, the memory is allocated globally and interleaved across all the NUMA nodes.
This creates high inter-node traffic for higher interconnect stress. But it may result in less CPU stress due to the memory bottleneck.

-----

### `LogicalCores`: Logical Cores to Test

This is an integer list containing the logical cores ids that should be tested.

Rules:
- Each core id must exist.
- Duplicates are not allowed.

On Linux, the core id is the same as the system assigned core ID. It is the bit position in the `cpu_set_t` mask.
On Windows, the core id assignment depends on processor group configuration.

The menu renderer for the default stress-test configuration will have all cores enabled.
The GUI can use this list as the complete set of logical core ids that can be used.

-----

### `TotalMemory`: Total Memory to Test

This is the # of bytes that should be used by the stress tester. This memory will be divided evenly among all the thread.

Valid values are:
- 32-bit: Integer ∈ `[0, 7 * 2^28)`
- 64-bit: Integer ∈ `[0, 7 * 2^60)`

-----

### `Seconds`: Seconds for each Test

Run each test for at least this many seconds.

Valid values are:
- 32-bit: Integer ∈ `[1, 2^32)`
- 64-bit: Integer ∈ `[1, 2^64)`

-----

### `StopOnError`: Stop when an Error is Detected

Valid values are `true` or `false`.

When `true`, the stress-tester will stop when it detects that an error has occurred.

-----

### `Tests`: Stress Test Modules

A list of which stress tests to run. There must be at least one test.

Valid values are:
- `BKT` - Basecase + Karatsuba
- `BBP` - BBP Digit Extraction
- `SFT` - Small In-Cache FFT
- `FFT` - Fast Fourier Transform
- `N32` - Classic NTT (32-bit)
- `N64` - Classic NTT (64-bit)
- `HNT` - Hybrid NTT
- `VST` - Vector Transform
- `C17` - Code 17 Experiment

Not all tests are available for every binary. In particular, the `C17` test is not available for processors that lack AVX2.

The GUI should make no assumption about the existance of any test for a particular environment.
The stress-test menu renderer will provide a full list of supported tests.



