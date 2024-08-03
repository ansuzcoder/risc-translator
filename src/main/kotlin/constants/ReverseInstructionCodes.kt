package org.example.constants

// funct7 + funct3 for type R instructions
val InstructionsR = mapOf(
    "0000000000" to "ADD",
    "0100000000" to "SUB",
    "0000000001" to "SLL",
    "0000000010" to "SLT",
    "0000000011" to "SLTU",
    "0000000100" to "XOR",
    "0000000101" to "SRL",
    "0100000101" to "SRA",
    "0000000110" to "OR",
    "0000000111" to "AND"
)

// funct3 for load type I instructions
val InstructionsLoadI = mapOf(
    "000" to "LB",
    "001" to "LH",
    "010" to "LW",
    "011" to "LD",
    "100" to "LBU",
    "101" to "LHU",
    "110" to "LWU",
)

// funct3 for arithmetic type I instructions
val InstructionsArithmeticI = mapOf(
    "000" to "ADDI",
    "001" to "SLLI",
    "010" to "SLTI",
    "011" to "SLTIU",
    "100" to "XORI",
    "0000000101" to "SRLI",
    "0100000101" to "SRAI",
)

// funct3 for JALR instruction
val InstructionsJalrI = mapOf("000" to "JALR")

// funct3 for type S instructions
val InstructionsS = mapOf(
    "000" to "SB",
    "001" to "SH",
    "010" to "SW",
    "011" to "SD"
)

// funct3 for type B instructions
val InstructionsB = mapOf(
    "000" to "BEQ",
    "001" to "BNE",
    "100" to "BLT",
    "101" to "BGE",
    "110" to "BLTU",
    "111" to "BGEU"
)