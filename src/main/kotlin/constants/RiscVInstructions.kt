package org.example.constants


object RiscVInstructions {
    val typeR = setOf(
        "ADD", "SUB", "SLT", "SLTU", "AND", "OR", "XOR", "SLL", "SRL", "SRA"
    )

    val arithmeticTypeI = setOf(
        "ADDI", "SLTI", "SLTIU", "ANDI", "ORI", "XORI", "SLLI", "SRLI", "SRAI"
    )

    val loadTypeI = setOf(
        "LD", "LW", "LH", "LB", "LWU", "LHU", "LBU"
    )

    val jalrTypeI = setOf(
        "JALR"
    )

    val typeS = setOf(
        "SD", "SW", "SH", "SB"
    )

    val typeB = setOf(
        "BEQ", "BNE", "BGE", "BGEU", "BLT", "BLTU"
    )

    val typeU = setOf(
        "LUI", "AUIP"
    )

    val typeJ = setOf(
        "JAL"
    )
}
