package org.example.translator

import data_converter.convertToBinary
import org.example.constants.*

import org.example.constants.RiscVInstructionTypes.InstructionTypes
import org.example.constants.RiscVInstructionTypes.RTypeInstruction
import org.example.constants.RiscVInstructionTypes.ITypeInstruction
import org.example.constants.RiscVInstructionTypes.STypeInstruction
import org.example.constants.RiscVInstructionTypes.BTypeInstruction
import org.example.constants.RiscVInstructionTypes.UTypeInstruction
import org.example.constants.RiscVInstructionTypes.JTypeInstruction
import java.lang.Exception


class Encoder {
    /*
    * Remove all unnecessary spaces from the code line
    */
    fun trimLine(codeLine: String): String {
        return codeLine.replace(Regex("\\s+"), " ").trim()
    }

    /*
    * Split the code line and retrieve the instruction name and its operands
    */
    fun splitIntoComponents(codeLine: String): Pair<String, List<String>> {
        val elements = codeLine.split(" ")

        val instructionName = elements[0]
        val operands = elements.slice(1..<elements.size)

        val tidyOperands = mutableListOf<String>()

        for (operand in operands) {
            tidyOperands.add(operand.replace(",", ""))
        }

        return Pair(instructionName, tidyOperands)
    }

    /*
    * Get instruction opcode
    * according to the instruction type
    * based on the instruction name
    */
    fun getOpcodeBasedOnName(instructionName: String): String {
        return when {
            RiscVInstructions.typeR.contains(instructionName) -> "0110011"

            RiscVInstructions.arithmeticTypeI.contains(instructionName) -> "0010011"
            RiscVInstructions.loadTypeI.contains(instructionName) -> "0000011"
            RiscVInstructions.jalrTypeI.contains(instructionName) -> "1100111"

            RiscVInstructions.typeS.contains(instructionName) -> "0100011"
            RiscVInstructions.typeB.contains(instructionName) -> "1100011"
            RiscVInstructions.typeU.contains(instructionName) -> when (instructionName) {
                "LUI" -> "0110111"
                "AUIP" -> "0010111"
                else -> "UCmd"
            }
            RiscVInstructions.typeJ.contains(instructionName) -> "1101111"
            else -> "UCmd"
        }
    }

    /*
    * Get funct7 for instructions of types R
    */
    fun getFunct7(instructionName: String): String {
        assert(RiscVInstructions.typeR.contains(instructionName))
        return funct7[instructionName]!!
    }

    /*
    * Get funct3 for instructions
    * of types R, I, S, B
    */
    fun getFunct3(instructionName: String): String {
        assert(!RiscVInstructions.typeU.contains(instructionName))
        assert(!RiscVInstructions.typeJ.contains(instructionName))
        return funct3[instructionName]!!
    }

    /*
    * Encode register into binary format of length 5
    */
    fun encodeReg(reg: String): String {
        val regDigit = reg.slice(1..<reg.length)
        val binaryDigit = Integer.toBinaryString(regDigit.toInt()).padStart(5, '0')
        return binaryDigit
    }

    /*
    * Encode immediate for instructions of types
    * I, S, B, U, J
    */
    fun encodeImm(commandType: InstructionTypes, immValue: String): List<String> {
        var binaryNumber = convertToBinary(immValue).padStart(12, '0')
        when (commandType) {
            InstructionTypes.I -> {
                return listOf(
                    binaryNumber
                )
            }
            InstructionTypes.S -> {
                return listOf(
                    binaryNumber.substring(0, 7),
                    binaryNumber.substring(7, 12)
                )
            }
            InstructionTypes.B -> {
                return listOf(
                    binaryNumber[0].toString(),
                    binaryNumber.substring(2, 7),
                    binaryNumber.substring(7, 12),
                    binaryNumber[1].toString()
                )
            }
            InstructionTypes.U -> {
                binaryNumber = binaryNumber.padStart(20, '0')
                return listOf(
                    binaryNumber
                )
            }
            InstructionTypes.J -> {
                binaryNumber = binaryNumber.padStart(20, '0')
                return listOf(
                    binaryNumber[0].toString(),
                    binaryNumber.substring(10, 20),
                    binaryNumber[9].toString(),
                    binaryNumber.substring(1, 9)
                )
            }
            else -> return listOf("")
        }
    }
}

class InstructionBuilder {
    private val encoder = Encoder()

    fun buildInstructionR(opcode: String, components: Pair<String, List<String>>): RTypeInstruction {
        val functs = mapOf(
            "funct3" to encoder.getFunct3(components.first),
            "funct7" to encoder.getFunct7(components.first)
        )
        val regs = mapOf(
            "rd" to encoder.encodeReg(components.second[0]),
            "rs1" to encoder.encodeReg(components.second[1]),
            "rs2" to encoder.encodeReg(components.second[2]),
        )
        return RTypeInstruction(
            functs["funct7"]!!,
            regs["rs2"]!!,
            regs["rs1"]!!,
            functs["funct3"]!!,
            regs["rd"]!!,
            opcode
        )
    }

    fun buildInstructionI(opcode: String, components: Pair<String, List<String>>): ITypeInstruction {
        val imm = encoder.encodeImm(InstructionTypes.I, components.second[2])
        val funct3 = encoder.getFunct3(components.first)
        val regs = mapOf(
            "rd" to encoder.encodeReg(components.second[0]),
            "rs1" to encoder.encodeReg(components.second[1])
        )
        return ITypeInstruction(
            imm[0],
            regs["rs1"]!!,
            funct3,
            regs["rd"]!!,
            opcode
        )
    }

    fun buildInstructionS(opcode: String, components: Pair<String, List<String>>): STypeInstruction {
        val leftBracket = components.second[1].indexOf('(')
        val rightBracket = components.second[1].indexOf(')')

        val funct3 = encoder.getFunct3(components.first)
        val rs2 = encoder.encodeReg(components.second[0])
        val imm = components.second[1].substring(0, leftBracket)
        val rs1 = encoder.encodeReg(components.second[1].substring(leftBracket + 1, rightBracket))

        val encodedImm = encoder.encodeImm(InstructionTypes.S, imm)

        return STypeInstruction(
            encodedImm[0],
            rs2,
            rs1,
            funct3,
            encodedImm[1],
            opcode
        )
    }

    fun buildInstructionB(opcode: String, components: Pair<String, List<String>>): BTypeInstruction {
        val rs1 = encoder.encodeReg(components.second[0])
        val rs2 = encoder.encodeReg(components.second[1])
        val funct3 = encoder.getFunct3(components.first)
        val imm = encoder.encodeImm(InstructionTypes.B, components.second[2])

        return BTypeInstruction(
            imm[0],
            imm[1],
            rs2,
            rs1,
            funct3,
            imm[2],
            imm[3],
            opcode
        )
    }

    fun buildInstructionU(opcode: String, components: Pair<String, List<String>>): UTypeInstruction {
        val rd = encoder.encodeReg(components.second[0])
        val imm = encoder.encodeImm(InstructionTypes.U, components.second[1])

        return UTypeInstruction(
            imm[0],
            rd,
            opcode
        )
    }

    fun buildInstructionJ(opcode: String, components: Pair<String, List<String>>): JTypeInstruction {
        val rd = encoder.encodeReg(components.second[0])
        val imm = encoder.encodeImm(InstructionTypes.J, components.second[1])

        return JTypeInstruction(
            imm[0],
            imm[1],
            imm[2],
            imm[3],
            rd,
            opcode
        )
    }
}

fun encodeCodeLine(instructionComponents: Pair<String, List<String>>): String {
    val encoder = Encoder()
    val instructionBuilder = InstructionBuilder()

    val opcode = encoder.getOpcodeBasedOnName(instructionComponents.first)
    val commandType = RiscVOpCodes[opcode]

    val encodedInstruction: String

    when (commandType) {
        InstructionTypes.R -> {
            encodedInstruction = instructionBuilder.buildInstructionR(opcode, instructionComponents).toString()
        }
        InstructionTypes.I -> {
            encodedInstruction = instructionBuilder.buildInstructionI(opcode, instructionComponents).toString()
        }
        InstructionTypes.S -> {
            encodedInstruction = instructionBuilder.buildInstructionS(opcode, instructionComponents).toString()
        }
        InstructionTypes.B -> {
            encodedInstruction = instructionBuilder.buildInstructionB(opcode, instructionComponents).toString()
        }
        InstructionTypes.U -> {
            encodedInstruction = instructionBuilder.buildInstructionU(opcode, instructionComponents).toString()
        }
        InstructionTypes.J -> {
            encodedInstruction = instructionBuilder.buildInstructionJ(opcode, instructionComponents).toString()
        }
        else -> {
            throw Exception("UCmd")
        }
    }

    return encodedInstruction
}