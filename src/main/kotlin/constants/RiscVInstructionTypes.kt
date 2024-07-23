package org.example.constants

object  RiscVInstructionTypes {
    enum class InstructionTypes {
        R, I, S, B, U, J
    }
    
    data class RTypeInstruction(
        val funct7: String,
        val rs2: String,
        val rs1: String,
        val funct3: String,
        val rd: String,
        val opcode: String
    ) {
        override fun toString(): String {
            return "${funct7}${rs2}${rs1}${funct3}${rd}${opcode}"
        }
    }

    data class ITypeInstruction(
        val imm11_0: String,
        val rs1: String,
        val funct3: String,
        val rd: String,
        val opcode: String
    ) {
        fun getImmediate(): String {
            return imm11_0
        }

        override fun toString(): String {
            return "${imm11_0}${rs1}${funct3}${rd}${opcode}"
        }
    }

    data class STypeInstruction(
        val imm11_5: String,
        val rs2: String,
        val rs1: String,
        val funct3: String,
        val imm4_0: String,
        val opcode: String
    ) {
        fun getImmediate(): String {
            return "${imm11_5}${imm4_0}"
        }

        override fun toString(): String {
            return "${imm11_5}${rs2}${rs1}${funct3}${imm4_0}${opcode}"
        }
    }

    data class BTypeInstruction(
        val imm12: String,
        val imm10_5: String,
        val rs2: String,
        val rs1: String,
        val funct3: String,
        val imm4_1: String,
        val imm11: String,
        val opcode: String
    ) {
        fun getImmediate(): String {
            return "${imm12}${imm11}${imm10_5}${imm4_1}"
        }

        override fun toString(): String {
            return "${imm12}${imm10_5}${rs2}${rs1}${funct3}${imm4_1}${imm11}${opcode}"
        }
    }

    data class UTypeInstruction(
        val imm31_12: String,
        val rd: String,
        val opcode: String
    ) {
        fun getImmediate(): String {
            return imm31_12
        }

        override fun toString(): String {
            return "${imm31_12}${rd}${opcode}"
        }
    }

    data class JTypeInstruction(
        val imm20: String,
        val imm10_1: String,
        val imm11: String,
        val imm19_12: String,
        val rd: String,
        val opcode: String
    ) {
        fun getImmediate(): String {
            return "${imm20}${imm19_12}${imm11}${imm10_1}"
        }

        override fun toString(): String {
            return "${imm20}${imm10_1}${imm11}${imm19_12}${rd}${opcode}"
        }
    }
}