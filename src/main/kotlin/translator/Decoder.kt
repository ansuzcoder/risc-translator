package org.example.translator

import org.example.constants.*

class Decoder {
    /*
    * Retrieve opcode for instructions
    */
    fun retrieveOpcode(instruction: String): String {
        return instruction.substring(25)
    }

    /*
    * Retrieve funct3/7 for instructions if needed
    */
    fun retrieveFuncts(instruction: String): Map<String, String> {
        val opcode = retrieveOpcode(instruction)
        val instructionType = RiscVOpCodes[opcode]

        val functs = mutableMapOf(
            "funct3" to "",
            "funct7" to ""
        )

        when (instructionType) {
            RiscVInstructionTypes.InstructionTypes.R -> {
                functs["funct7"] = instruction.substring(0, 7)
                functs["funct3"] = instruction.substring(17, 20)
            }
            RiscVInstructionTypes.InstructionTypes.I -> {
                functs["funct3"] = instruction.substring(17, 20)
            }
            RiscVInstructionTypes.InstructionTypes.S -> {
                functs["funct3"] = instruction.substring(17, 20)
            }
            RiscVInstructionTypes.InstructionTypes.B -> {
                functs["funct3"] = instruction.substring(16, 19)
            }
            RiscVInstructionTypes.InstructionTypes.U -> { }
            RiscVInstructionTypes.InstructionTypes.J -> { }
            null -> throw Exception("UCmd")
        }

        return functs
    }

    /*
    * Retrieve operands for instructions
    * such as registers and immediate values
    */
    fun retrieveOperands(instruction: String): Map<String, String> {
        val opcode = retrieveOpcode(instruction)
        val instructionType = RiscVOpCodes[opcode]

        val operands = mutableMapOf(
            "rd" to "",
            "rs1" to "",
            "rs2" to "",
            "imm" to ""
        )

        when (instructionType) {
            RiscVInstructionTypes.InstructionTypes.R -> {
                operands["rd"] = instruction.substring(20, 25)
                operands["rs1"] = instruction.substring(12, 17)
                operands["rs2"] = instruction.substring(7, 12)
            }
            RiscVInstructionTypes.InstructionTypes.I -> {
                operands["rd"] = instruction.substring(20, 25)
                operands["rs1"] = instruction.substring(12, 17)
                operands["imm"] = instruction.substring(0, 12)
            }
            RiscVInstructionTypes.InstructionTypes.S -> {
                val imm1 = instruction.substring(0, 7)
                val imm2 = instruction.substring(20, 25)

                operands["imm"] = imm1 + imm2
                operands["rs1"] = instruction.substring(12, 17)
                operands["rs2"] = instruction.substring(7, 12)
            }
            RiscVInstructionTypes.InstructionTypes.B -> {
                val imm1 = instruction[0].toString()
                val imm2 = instruction.substring(1, 6)
                val imm3 = instruction.substring(19, 24)
                val imm4 = instruction[24].toString()

                operands["imm"] = imm1 + imm4 + imm2 + imm3
                operands["rs1"] = instruction.substring(11, 16)
                operands["rs2"] = instruction.substring(6, 11)
            }
            RiscVInstructionTypes.InstructionTypes.U -> {
                operands["imm"] = instruction.substring(0, 20)
                operands["rd"] = instruction.substring(20, 25)
            }
            RiscVInstructionTypes.InstructionTypes.J -> {
                val imm20 = instruction[0].toString()
                val imm10_1 = instruction.substring(1, 11)
                val imm11 = instruction[11].toString()
                val imm19_12 = instruction.substring(12, 20)

                // shitty shift right (>> 1)
                operands["imm"] = (imm20 + imm19_12 + imm11 + imm10_1).substring(1) + "0"
                operands["rd"] = instruction.substring(20, 25)
            }
            null -> throw Exception("UCmd")
        }

        return operands
    }

    fun retrieveInstructionName(opcode: String, functs: Map<String, String>): String {
        val instructionType = RiscVOpCodes[opcode]

        var instructionName: String = ""

        val funct3 = functs["funct3"]
        val funct7 = functs["funct7"]

        val soughtCode = funct7 + funct3

        when (instructionType) {
            RiscVInstructionTypes.InstructionTypes.R -> {
                instructionName = InstructionsR[soughtCode] ?: throw Exception("UCmd $soughtCode")
            }
            RiscVInstructionTypes.InstructionTypes.I -> {
                instructionName = when (opcode) {
                    "0000011" -> {
                        InstructionsLoadI[soughtCode] ?: throw Exception("UCmd $soughtCode")
                    }

                    "0010011" -> {
                        InstructionsArithmeticI[soughtCode] ?: throw Exception("UCmd $soughtCode")
                    }

                    "1100111" -> {
                        InstructionsJalrI[soughtCode] ?: throw Exception("UCmd $soughtCode")
                    }

                    else -> {
                        throw Exception("UCmd with $opcode")
                    }

                }
            }
            RiscVInstructionTypes.InstructionTypes.S -> {
                instructionName = InstructionsS[soughtCode] ?: throw Exception("UCmd with $soughtCode")
            }
            RiscVInstructionTypes.InstructionTypes.B -> {
                instructionName = InstructionsB[soughtCode] ?: throw Exception("UCmd with $soughtCode")
            }
            RiscVInstructionTypes.InstructionTypes.U -> {
                when (opcode) {
                    "0010111" -> {
                        instructionName = "AUIPC"
                    }
                    "0110111" -> {
                        instructionName = "LUI"
                    }
                }
            }
            RiscVInstructionTypes.InstructionTypes.J -> {
                instructionName = "JAL"
            }
            null -> throw Exception("UCmd for $opcode")
        }
        return instructionName
    }
}

data class DecodedInstruction(
    val instructionName: String,
    val operands: Map<String, String>
) {
    override fun toString(): String {
        return "{Name: [$instructionName], Operands: [$operands]}"
    }
}