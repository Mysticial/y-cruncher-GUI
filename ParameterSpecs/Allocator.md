Memory Allocator
=====

Example:
```
        "Allocator": {
            "Allocator": "mmap",
            "LargePages": "attempt",
            "LockedPages": "attempt"
        }
```

y-cruncher pre-allocates all of its working memory in large chunks. This allocator object determines how that is done.

The first field `Allocator` determines which allocator is used. The remaining fields are specific to that allocator.

Allocators:
- `malloc`
- `mmap`
- `interleave`
- `interleave-libnuma`

Like the parallel frameworks, not all allocators are available everywhere.
The GUI should query the options object model to get the supported list for the active binary.

-----

### `malloc`: C malloc()
```
        "Allocator": {
           "Allocator": "malloc"
        }
```

**Supported by (as of v0.7.5):** Always Supported

This framework has no additional sub-options.

-----

### `mmap`: Memory Mapping

`VirtualAlloc()` on Windows and `mmap()` on Linux.

```
        "Allocator": {
            "Allocator": "mmap",
            "LargePages": "attempt",
            "LockedPages": "attempt"
        }
```

**Supported by (as of v0.7.5):** Always Supported

|Sub-Node      |Valid Values |
|--------------|-------------|
|`LargePages`  |`disabled`, `attempt`, `forced` |
|`LockedPages` |`disabled`, `attempt`, `forced` |

- `disabled` will disable that option.
- `attempt` will try to enable it. But it it fails, it falls back to disabling it.
- `forced` will try to enable it. If it fails, it gives up and terminates the computation.

-----

### `interleave`: Node Interleaving

Interleave the memory across multiple NUMA nodes.

**Windows:**

```
        "Allocator": {
            "Allocator": "interleave",
            "Hashed": "true",
            "LockedPages": "attempt",
            "Nodes": [0 1 2 3]
        }
```

**Linux:**

```
        "Allocator": {
            "Allocator": "interleave",
            "Hashed": "true",
            "mbind": "false",
            "LockedPages": "attempt",
            "Nodes": [0 1 2 3]
        }
```

This allocator differs slightly between Windows and Linux. In Linux, there is an extra field, `mbind`.

**Supported by (as of v0.7.5):** Always Supported

|Sub-Node      |Valid Values |
|--------------|-------------|
|`Hashed`      |`true` or `false`               |
|`mbind`       |`true` or `false`               |
|`LockedPages` |`disabled`, `attempt`, `forced` |
|`Nodes`       |Integer list of NUMA node #s.   |

Duplicates are allowed in the node list.
The `mbind` field is ignored on the statically-linked Linux binaries since they are unable to bind anyway.

-----

### `interleave-libnuma`: Node Interleaving (via libnuma)

Interleave the memory across multiple NUMA nodes using the libnuma library.

```
        "Allocator": {
            "Allocator": "interleave-libnuma",
            "LockedPages": "attempt",
            "Nodes": [0 1 2 3]
        }
```

**Supported by (as of v0.7.5):** Dynamically-linked Linux binaries only. Not available in Windows.

|Sub-Node      |Valid Values |
|--------------|-------------|
|`LockedPages` |`disabled`, `attempt`, `forced` |
|`Nodes`       |Integer list of NUMA node #s.   |

Duplicates are ***not*** allowed in the node list.





