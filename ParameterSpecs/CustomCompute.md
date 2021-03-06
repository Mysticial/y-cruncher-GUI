Custom Compute
=====

The parameters object model for Custom Compute is identical to the configuration files that y-cruncher generates when you save the settings.
But for the GUI, it will be in standard JSON instead of the custom thing that y-cruncher does.


**Example: Ram Only mode**

```
{
    "Action": "CustomCompute",
    "CustomCompute": {
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
            "OutputEnable" : "true"
            "DigitsPerFile": 0
        },
        "OutputVerify" : "true"
        "Mode": "ram",
        "Parallelism": {
            "TaskDecomposition": 16,
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

**Example: Swap Mode**
```
{
    "Action": "CustomCompute",
    "CustomCompute": {
        "Constant": {
            "Constant": "log",
            "Argument": 2,
            "Algorithm": "machin-primary"
        },
        "ComputeSize": {
            "DecimalDigits": 50000000,
            "EnableHexDigits": "true"
        },
        "Output": {
            "Path": "",
            "OutputEnable" : "true"
            "DigitsPerFile": 1000000
        },
        "OutputVerify" : "true"
        "Mode": "swap",
        "Parallelism": {
            "TaskDecomposition": 16,
            "Framework": "pushpool",
            "WorkerThreads": 0,
            "Randomization": "true"
        },
        "Allocator": {
            "Allocator": "interleave",
            "Hashed": "true",
            "LockedPages": "attempt",
            "Nodes": [0, 1, 2, 3]
        },
        "BytesPerSeek": 2097152,
        "Memory": 314572800,
        "RaidFile": {
            "BufferPerFile": 67108864,
            "Lanes": [
                {
                    "Type": "raid0",
                    "Paths": ["d:"]
                },
                {
                    "Type": "raid0",
                    "Paths": ["f:"]
                }
            ]
        }
    }
}
```

Currently, the exact structure of the root element as well as the existance of the `Action` element isn't completely decided.
This example was translated directly from the runnable .ini config files which is structured this way.
It may change later as is fit for the GUI.

When sending this object from GUI to y-cruncher, every element and sub-element is needed as y-cruncher expects them.

-----

### `Constant`: Constant and Algorithm

This node determines what constant to compute as well as what algorithm to use.
Most constants will only have two sub-elements, `Constant` and `Algorithm`. Some will also have, an `Argument`.

```
        "Constant": {
            "Constant": "log",
            "Argument": 2,
            "Algorithm": "machin-primary"
        },
```

|Constant       |Argument|Algorithm|
|---------------|--------|---------|
|`sqrt`         |32-bit: Non-square integer ∈ `[2, 2^32)`.<br>64-bit: Non-square integer ∈ `[2, 2^64)`. | `newton` |
|`goldenratio`  | - | `newton` |
|`e`            | - | `exp(1)` or `exp(-1)` |
|`pi`           | - | `chudnovsky` or `ramanujan` |
|`arccoth`*     |32-bit: Integer ∈ `[2, 2^16)`<br>64-bit: Integer ∈ `[2, 2^32)` | `series` |
|`log`          |32-bit: Integer ∈ `[2, 2^16)`<br>64-bit: Integer ∈ `[2, 2^32)` | `machin-primary` or `machin-secondary` |
|`zeta3`        | - | `az-long` or `az-short` |
|`lemniscate`   | - | `gauss`, `sebah`, or `agm-pi`** |
|`catalan`      | - | `lupas`, `huvent`, `guillera`, `pilehrood-short`, or `pilehrood-long`** |
|`gamma`        | - | `brent-refined` or `brent-original` |
|`custom`*      | A formula specification.*** | - |

Currently, this node is identical for all binaries with the exception of the 32-bit vs. 64-bit differences.
So every single binary, whether it be `00-x86` or `17-SKX` all support the same constants and algorithms.

*`arccoth` goes away in v0.7.7. `custom` is new to v0.7.7.<br>
**`agm-pi`, `guillera`, `pilehrood-short`, and `pilehrood-long` are new to v0.7.7.<br>
***For custom formulas, the entire formula is inlined into the config. You cannot pass a filename.

-----

### `ComputeSize`: Digits to Compute

This node determines how many decimal digits to compute and whether hexadecimal digits should be outputted.
If enabled, the # of hexadecimal digits is derived from the # of decimal digits.

|Sub-Node          |Valid Values |
|------------------|-------------|
|`DecimalDigits`   |Integer ∈ `[100, 1000000000000050]` | 
|`EnableHexDigits` |`true` or `false` |

-----

### `Output`: Output Format

This controls how to output the digits. Where to output? And what format?

|Sub-Node        |Valid Values |
|----------------|-------------|
|`Path`          |Empty string or a writable filesystem path. | 
|`OutputEnable`  |`true` or `false` |
|`DigitsPerFile` |`0`, `-1`, or integer ∈ `[1000000, 2^63)` |

`OutputEnable` determines whether to output digits at all. If `false`, the remaining parameters are ignored.

For `DigitsPerFile`:
- `0` means output to an ASCII .txt file.
- `-1` means to compress entire output into a single .ycd file.
- Any other number N means to compress into multiple .ycd files with N digits per file.

-----

### `OutputVerify`: Verify Output Digits

`true` or `false`. If `true`, it enables verification of the radix conversion and the output digits.
This option is required for world record attempts.

-----

### `Mode`: Computation Mode

This is a pivot node. The structure of the rest of the Custom Compute object model will depend on this node.

|`ram`        |`swap`        |
|-------------|--------------|
|[`Constant`](#constant-constant-and-algorithm)|[`Constant`](#constant-constant-and-algorithm)|
|[`ComputeSize`](#computesize-digits-to-compute)|[`ComputeSize`](#computesize-digits-to-compute)|
|[`Output`](#output-output-format)|[`Output`](#output-output-format)|
|`Mode`       |`Mode`        |
|[`Parallelism`](https://github.com/Mysticial/y-cruncher-GUI/blob/master/ParameterSpecs/Parallelism.md)|[`Parallelism`](https://github.com/Mysticial/y-cruncher-GUI/blob/master/ParameterSpecs/Parallelism.md)|
|[`Allocator`](https://github.com/Mysticial/y-cruncher-GUI/blob/master/ParameterSpecs/Allocator.md)|[`Allocator`](https://github.com/Mysticial/y-cruncher-GUI/blob/master/ParameterSpecs/Allocator.md)|
|             |[`BytesPerSeek`](https://github.com/Mysticial/y-cruncher-GUI/blob/master/ParameterSpecs/SwapMode.md#bytesperseek-bytes-per-seek-tuning-parameter)|
|             |[`Memory`](https://github.com/Mysticial/y-cruncher-GUI/blob/master/ParameterSpecs/SwapMode.md#memory-requested-memory-usage)|
|             |[`RaidFile`](https://github.com/Mysticial/y-cruncher-GUI/blob/master/ParameterSpecs/SwapMode.md#raidfile-swap-file-configuration)|

Notes:
- Future versions of y-cruncher may add or remove modes.
- Not all modes will always be available for every constant/algorithm/compute-size combination.
- The only fields which are guaranteed* to exist for all modes are: `Constant`, `ComputeSize`, and `Output`.

*For *some* definition of "guarantee".


