# RISC-V Translator
This repo is a test project for implementing a RISC-V ISA translator, which will be used in main project [Riscambler-Multiplatform](https://github.com/alchal17/Riscambler-Multiplatform) - an IDE for RISC-V programming.

This version is an attempt to improve a translator made in [Riscambler Desktop](https://github.com/alchal17/Riscambler-Desktop) and will be removed soon after it's finished.

# Working Principles
The translator accepts a line of user's code and encodes it in a way it's done in modern RISC-V based CPU-s.

The `Encoder` provides *opcodes*, *functs3/7*, *registers* and *immediate values*, depending on the **instruction type**: R, I, S, B, U, or J. These components are then combined together and stored in memory as a **uint32** number.

The `Decoder` works vice versa. It takes a **uint32** number from memory to get the instruction and its operands. The result depends on the opcode, which is extracted in the very beginning of the decoding process.

# Usage
The current version of the translator decides whether to use `Encoder` or `Decoder`
by itself. With that in mind, to use the translator we can simply write code like:
```
ADD x1, x2, x3 // Encoder will be used
```
or
```
00000000001100010000000010110011 // Decoder will be used
```

# TODO
- Finish GUI for the convenient usage
- Test all `Encoder` and `Decoder` on every command present in RISC-V ISA


# Finished tasks
- Integrate `Encoder` into the application [✔️]
- Finish `Decoder` [✔️]
- Fix `load/store instructions` encoding [✔️]