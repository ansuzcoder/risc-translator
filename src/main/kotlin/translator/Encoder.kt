package org.example.translator

import data_converter.convertToBinary
import org.example.constants.*
import org.example.constants.RiscVInstructionTypes.InstructionTypes

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
                    binaryNumber.substring(2, 9),
                    binaryNumber.substring(9, 12),
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

    fun encodeLine(codeLine: String): String {
        val splitComponents = splitIntoComponents(trimLine(codeLine))

        val opcode = getOpcodeBasedOnName(splitComponents.first)
        var funct3 = ""
        var funct7 = ""
        when (RiscVOpCodes[opcode]) {
            InstructionTypes.R -> {
                funct3 = getFunct3(splitComponents.first)
                funct7 = getFunct7(splitComponents.first)
            }
            InstructionTypes.I -> {
                funct3 = getFunct3(splitComponents.first)
            }
            InstructionTypes.S -> {
                funct3 = getFunct3(splitComponents.first)
            }
            InstructionTypes.B -> {
                funct3 = getFunct3(splitComponents.first)
            }
            else -> { }
        }
        return ""
    }
}