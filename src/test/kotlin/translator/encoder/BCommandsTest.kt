package translator.encoder

import org.example.translator.Encoder
import org.example.translator.InstructionBuilder

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BCommandsTest {
    private val encoder = Encoder()
    private val instructionBuilder = InstructionBuilder()

    @Test
    fun testBEQ1() {
        val instruction = "BEQ x1, x2, 14"
        val expected = "00000000010000010000111001100011"

        val components = encoder.splitIntoComponents(instruction)
        val opcode = encoder.getOpcodeBasedOnName(components.first)

        val encoded = instructionBuilder.buildInstructionB(opcode, components)

        assertEquals(expected, encoded.toString())
    }
}