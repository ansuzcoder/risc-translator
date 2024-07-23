package org.example.constants

val funct7 = mapOf(
    "ADD" to "0000000",
    "SUB" to "0100000",
    "SLT" to "0000000",
    "SLTU" to "0000000",
    "AND" to "0000000",
    "OR" to "0000000",
    "XOR" to "0000000",
    "SLL" to "0000000",
    "SRL" to "0000000",
    "SRA" to "0100000",
)

val funct3 = mapOf(
    // I type
    "LB" to "000",
    "LH" to "001",
    "LW" to "010",
    "LD" to "011",
    "LBU" to "100",
    "LHU" to "101",
    "LWU" to "110",

    "ADDI" to "000",
    "SLLI" to "001",
    "SLTI" to "010",
    "SLTIU" to "011",
    "XORI" to "100",
    "SRLI" to "101",
    "SRAI" to "101",

    "ORI" to "110",
    "ANDI" to "111",

    "JALR" to "000",

    // S type
    "SB" to "000",
    "SH" to "001",
    "SW" to "010",
    "SD" to "011",

    // R type
    "ADD" to "000",
    "SUB" to "000",
    "SLL" to "001",
    "SLT" to "010",
    "SLTU" to "011",
    "XOR" to "100",
    "SRL" to "101",
    "SRA" to "101",
    "OR" to "110",
    "AND" to "111",

    // B type
    "BEQ" to "000",
    "BNE" to "001",
    "BLT" to "100",
    "BGE" to "101",
    "BLTU" to "110",
    "BGEU" to "111"
)