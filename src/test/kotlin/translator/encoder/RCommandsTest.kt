package translator.encoder

import org.example.translator.Encoder
import org.example.translator.InstructionBuilder

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RCommandsTest {
    private val encoder = Encoder()
    private val instructionBuilder = InstructionBuilder()

    @Test
    fun testADD1() {
        val command = "ADD x1, x2, x3"
        val expected = "00000000001100010000000010110011"

        val components = encoder.splitIntoComponents(command)
        val opcode = encoder.getOpcodeBasedOnName(components.first)

        assertEquals(expected, instructionBuilder.buildInstructionR(opcode, components).toString())
    }

    @Test
    fun testADD2() {
        val command = "ADD x5, x12, x17"
        val expected = "00000001000101100000001010110011"

        val components = encoder.splitIntoComponents(command)
        val opcode = encoder.getOpcodeBasedOnName(components.first)

        assertEquals(expected, instructionBuilder.buildInstructionR(opcode, components).toString())
    }

    @Test
    fun testADD3() {
        val command = "ADD x16, x0, x31"
        val expected = "00000001111100000000100000110011"

        val components = encoder.splitIntoComponents(command)
        val opcode = encoder.getOpcodeBasedOnName(components.first)

        assertEquals(expected, instructionBuilder.buildInstructionR(opcode, components).toString())
    }

    @Test
    fun testSUB1() {
        val command = "SUB x1, x2, x3"
        val expected = "01000000001100010000000010110011"

        val components = encoder.splitIntoComponents(command)
        val opcode = encoder.getOpcodeBasedOnName(components.first)

        assertEquals(expected, instructionBuilder.buildInstructionR(opcode, components).toString())
    }

    @Test
    fun testSUB2() {
        val command = "SUB x8, x21, x10"
        val expected = "01000000101010101000010000110011"

        val components = encoder.splitIntoComponents(command)
        val opcode = encoder.getOpcodeBasedOnName(components.first)

        assertEquals(expected, instructionBuilder.buildInstructionR(opcode, components).toString())
    }
}