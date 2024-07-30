package org.example.constants

val RiscVOpCodes = mapOf(
    "0110011" to RiscVInstructionTypes.InstructionTypes.R,

    "0000011" to RiscVInstructionTypes.InstructionTypes.I,
    "0001111" to RiscVInstructionTypes.InstructionTypes.I,
    "0010011" to RiscVInstructionTypes.InstructionTypes.I,
    "1110011" to RiscVInstructionTypes.InstructionTypes.I,
    "0011011" to RiscVInstructionTypes.InstructionTypes.I,

    "0100011" to RiscVInstructionTypes.InstructionTypes.S,

    "1100011" to RiscVInstructionTypes.InstructionTypes.B,

    "0110111" to RiscVInstructionTypes.InstructionTypes.U,

    "1101111" to RiscVInstructionTypes.InstructionTypes.J,
)