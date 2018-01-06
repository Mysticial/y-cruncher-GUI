Parallel Computing Method
=====

Example:
```
        "Parallelism": {
            "TaskDecomposition": 16,
            "Framework": "pushpool",
            "WorkerThreads": 0,
            "Randomization": "true"
        },
```

There are two fundumental parts to this node: "Task Decomposition" and "Parallel Framework".
- "Task Decomposition" determines how much to subdivide the computation to enable parallelism.
- "Parallel Framework" determines how to actually parallelize the subdivided computation.

Currently, both concepts are mashed together into a single object.

|Sub-Node           |Valid Values |
|-------------------|-------------|
|`TaskDecomposition`|Integer ∈ `[1, 2^16)` | 
|`Framework`        |Varies |

All other sub-nodes are specific to the framework.

The full list of frameworks is:
- `none`
- `cppasync`
- `spawn`
- `pushpool`
- `winpool`
- `cilk`
- `tbb`

However, not all frameworks are always supported. The GUI should make no assumptions about the support of any particular framework for any environment.
Instead, the GUI should query the options object model to get the supported list for the active binary.

-----

### `none`: No Parallelism
```
        "Parallelism": {
            "TaskDecomposition": 1,
            "Framework": "none"
        }
```

**Supported by (as of v0.7.5):** Always Supported

This framework has no additional sub-options.
Since parallelism is disabled, there is also no point in setting the task decomposition larger than 1.

-----

### `spawn`: C++11 std::async()
```
        "Parallelism": {
            "TaskDecomposition": 16,
            "Framework": "cppasync",
            "AsyncOnly": "true"
        }
```

**Supported by (as of v0.7.5):** Always Supported

|Sub-Option  |Valid Values |
|------------|-------------|
|`AsyncOnly` |`true` or `false` |

-----

### `spawn`: Spawn a Thread for Every Task
```
        "Parallelism": {
            "TaskDecomposition": 16,
            "Framework": "spawn"
        }
```

**Supported by (as of v0.7.5):** Always Supported

This framework has no additional sub-options.

-----

### `pushpool`: Push-Based Thread Pool
```
        "Parallelism": {
            "TaskDecomposition": 16,
            "Framework": "pushpool",
            "WorkerThreads": 0,
            "Randomization": "true"
        }
```

**Supported by (as of v0.7.5):** Always Supported

|Sub-Option      |Valid Values |
|----------------|-------------|
|`WorkerThreads` |Integer ∈ `[0, 2^16)` |
|`Randomization` |`true` or `false` |

When `WorkerThreads` is 0, it will spawn as many threads as necessary.
If it's non-zero, it will never use more than that many threads.

-----

### `winpool`: Windows Thread Pool
```
        "Parallelism": {
            "TaskDecomposition": 16,
            "Framework": "winpool"
        }
```

**Supported by (as of v0.7.5):** Windows Only

This framework has no additional sub-options.

-----

### `cilk`: Cilk Plus Work-Stealing
```
        "Parallelism": {
            "TaskDecomposition" : 16,
            "Framework" : "cilk",
            "WorkerThreads" : 16
        }
```

**Supported by (as of v0.7.5):**
- Windows: All binaries except 00-x86, 04-P4P, 05-A64, and 11-BD1.
- Linux: All dynamically-linked binaries. None of the statically-linked binaries.

|Sub-Option      |Valid Values |
|----------------|-------------|
|`WorkerThreads` |Integer ∈ `[1, ?)` |

The upper limit of `WorkerThreads` is unknown since it is passed directly into the Cilk Plus framework which is a 3rd party library owned by Intel.
This also isn't something you'd want to test if you don't want to risk fork-bombing yourself.

-----

### `tbb`: Thread Building Blocks
```
        "Parallelism": {
            "TaskDecomposition": 16,
            "Framework": "tbb"
        }
```

**Supported by (as of v0.7.5):**
- Windows: And binaries except 00-x86, 04-P4P.
- Linux: Not supported anywhere.

This framework has no additional sub-options.




