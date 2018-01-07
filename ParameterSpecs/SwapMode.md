Swap Mode Parameters
=====

This section covers all the swap-mode specific parameters.

Swap Mode Parameters:

- [`Parallelism`](https://github.com/Mysticial/y-cruncher-GUI/blob/master/ParameterSpecs/Parallelism.md)
- [`Allocator`](https://github.com/Mysticial/y-cruncher-GUI/blob/master/ParameterSpecs/Allocator.md)
- [`BytesPerSeek`](#bytesperseek-bytes-per-seek-tuning-parameter)
- [`Memory`](#memory-requested-memory-usage)
- [`RaidFile`](#raidfile-swap-file-configuration)

-----

### `BytesPerSeek`: Bytes per Seek Tuning Parameter

Example:
```
        "BytesPerSeek": 2097152
```

This is a tuning parameter that represents the # of bytes that can be read sequentially from the raid-file in the same amount of time as a disk seek.
This parameter is critical for large computations where the computation must choose whether to trade away disk seeks for more bytes accessed.

There are no sub-fields for this node. Valid values are:
- 32-bit: Integer ∈ `[4096, 2^31)`
- 64-bit: Integer ∈ `[4096, 2^63)`

-----

### `Memory`: Requested Memory Usage

Example:
```
        "Memory": 314572800
```

This is the memory limit that is requested by the user (measured in bytes).
In most cases, y-cruncher will be able to respect this and not use more than this much memory.
When y-cruncher is unable to stay below this limit, it will be reflected in the "RequiredRam" menu renderer node.

There are no sub-fields for this node. Valid values are:
- 32-bit: Integer ∈ `[0, 7 * 2^28)`
- 64-bit: Integer ∈ `[0, 7 * 2^60)`

-----

### `RaidFile`: Swap File Configuration

Example:
```
        "RaidFile": {
            "BufferPerFile": 67108864,
            "Lanes": [
                {
                    "Type": "raid0",
                    "Paths": ["d:", "e:"]
                },
                {
                    "Type": "raid3",
                    "Paths": ["f:", "g", "h"]
                }
            ]
        }
```

This is the swap file configuration for Swap Mode computations. It's called "RaidFile" since it supports RAID0 and RAID3.

The structure of the RaidFile is a 2-level RAID. The top level is RAID0 and has a limit of 64 lower-levels.
Each of the lower-levels is either a RAID0 or a RAID3 of up to 8 paths.

More details of the 2-level RAID can be found here: http://www.numberworld.org/y-cruncher/guides/swapmode.html#raid_manual

`BufferPerFile` is measured in bytes. The valid range is:
- 32-bit: Integer ∈ `[2^20, 2^32)`
- 64-bit: Integer ∈ `[2^20, 2^64)`

`Lanes` is the layout of the 2-layer raid file. Rules are:
- There is a limit of 64 entries in the `Lanes` node.
- There is a limit of 8 paths in each of the bottom nodes.
- `Type` can be either `raid0` or `raid3`.
- If a bottom node is `raid3`, it must have at least 2 paths.
- All paths must be writable.
- Paths can be the empty string. This means to use the current working directory.
- Paths do not need to be unique. But there are no performance advantages to duplicating paths.



