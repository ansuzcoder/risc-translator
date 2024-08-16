package translator.encoder

import org.example.translator.Encoder
import org.example.translator.InstructionBuilder

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SCommandsTest {
    private val encoder = Encoder()
    private val instructionBuilder = InstructionBuilder()

    @Test
    fun testSD1() {
        val instruction = "SD x1, 10(x2)"
        val expected = "00000000000100010011010100100011"

        val components = encoder.splitIntoComponents(instruction)
        val opcode = encoder.getOpcodeBasedOnName(components.first)

        val encoded = instructionBuilder.buildInstructionS(opcode, components)
        assertEquals(expected, encoded.toString())
    }
}