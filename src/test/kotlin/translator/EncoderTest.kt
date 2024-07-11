package translator

import org.example.constants.RiscVInstructionTypes.InstructionTypes
import org.example.translator.*

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EncoderTest {
    @Test
    fun testTrimLine() {
        val input = "  ADD   R1,  R2,  R3  "
        val expected = "ADD R1, R2, R3"
        assertEquals(expected, trimLine(input))
    }

    @Test
    fun testSplitIntoComponents() {
        val input = "ADD R1, R2, R3"
        val expected = Pair("ADD", listOf("R1", "R2", "R3"))
        assertEquals(expected, splitIntoComponents(input))
    }

    @Test
    fun testGetOpcodeBasedOnName() {
        assertEquals("0110011", getOpcodeBasedOnName("ADD"))
        assertEquals("0010011", getOpcodeBasedOnName("ADDI"))
        assertEquals("0000011", getOpcodeBasedOnName("LD"))
        assertEquals("1100111", getOpcodeBasedOnName("JALR"))
        assertEquals("0100011", getOpcodeBasedOnName("SD"))
        assertEquals("1100011", getOpcodeBasedOnName("BEQ"))
        assertEquals("0110111", getOpcodeBasedOnName("LUI"))
        assertEquals("0010111", getOpcodeBasedOnName("AUIP"))
        assertEquals("1101111", getOpcodeBasedOnName("JAL"))
        assertEquals("UCmd", getOpcodeBasedOnName("UNKNOWN"))
    }
    
    @Test
    fun testEncodeReg() {
        val testInput1 = "x1"
        val testExpected1 = "00001"
        assertEquals(testExpected1, encodeReg(testInput1))
        
        val testInput2 = "x5"
        val testExpected2 = "00101"
        assertEquals(testExpected2, encodeReg(testInput2))
        
        val testInput3 = "x10"
        val testExpected3 = "01010"
        assertEquals(testExpected3, encodeReg(testInput3))
        
        val testInput4 = "x27"
        val testExpected4 = "11011"
        assertEquals(testExpected4, encodeReg(testInput4))
        
        val testInput5 = "x31"
        val testExpected5 = "11111"
        assertEquals(testExpected5, encodeReg(testInput5))
    }

    @Test
    fun testGetFunct7() {
        val testInput1 = "ADD"
        val testExpected1 = "0000000"
        assertEquals(testExpected1, getFunct7(testInput1))

        val testInput2 = "SUB"
        val testExpected2 = "0100000"
        assertEquals(testExpected2, getFunct7(testInput2))

        val testInput3 = "SLT"
        val testExpected3 = "0000000"
        assertEquals(testExpected3, getFunct7(testInput3))
    }

    @Test
    fun testGetFunct3() {
        val testInput1 = "ADDI"
        val testExpected1 = "000"
        assertEquals(testExpected1, getFunct3(testInput1))

        val testInput2 = "XORI"
        val testExpected2 = "100"
        assertEquals(testExpected2, getFunct3(testInput2))

        val testInput3 = "LH"
        val testExpected3 = "001"
        assertEquals(testExpected3, getFunct3(testInput3))

        val testInput4 = "LWU"
        val testExpected4 = "110"
        assertEquals(testExpected4, getFunct3(testInput4))

        val testInput5 = "JALR"
        val testExpected5 = "000"
        assertEquals(testExpected5, getFunct3(testInput5))

        val testInput6 = "SLL"
        val testExpected6 = "001"
        assertEquals(testExpected6, getFunct3(testInput6))

        val testInput7 = "BGE"
        val testExpected7 = "101"
        assertEquals(testExpected7, getFunct3(testInput7))
    }

    @Test
    fun testEncodeImm() {
        val inputValue = "16"
        val encodedDictI = encodeImm(InstructionTypes.I, inputValue)
        var encodedImm = ""
        for (el in encodedDictI) {
            encodedImm += el
        }
        val expectedValueI = "000000010000"
        assertEquals(expectedValueI, encodedImm)

        val encodedDictJ = encodeImm(InstructionTypes.J, inputValue)
        encodedImm = ""
        for (el in encodedDictJ) {
            encodedImm += el
        }
        val expectedValueJ = "00000010000000000000"
        assertEquals(expectedValueJ, encodedImm)
    }
}