package org.example.translator

import org.example.constants.RiscVInstructionTypes

class Decoder {
    fun getOpcode(instruction: String): String {
        return instruction.substring(instruction.length - 7, instruction.length)
    }

//    fun getFuncts(instruction: String, instructionType: RiscVInstructionTypes): Map<String, String> {
//
//    }
}