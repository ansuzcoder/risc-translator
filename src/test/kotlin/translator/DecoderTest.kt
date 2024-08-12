package translator

import org.example.translator.*

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DecoderTest {
    private val decoder = Decoder()

    @Test
    fun testRetrieveOpcode1() {
        val instruction = "00000000001100010000000010110011"
        val desiredResult = "0110011"
        assertEquals(desiredResult, decoder.retrieveOpcode(instruction))
    }

    @Test
    fun testRetrieveOpcode2() {
        val instruction = "00000000101000010000000010010011"
        val desiredResult = "0010011"
        assertEquals(desiredResult, decoder.retrieveOpcode(instruction))
    }

    @Test
    fun testRetrieveFuncts1() {
        val instruction = "00000000001100010000000010110011"
        val desiredResult = mapOf(
            "funct3" to "000",
            "funct7" to "0000000"
        )
        assertEquals(desiredResult, decoder.retrieveFuncts(instruction))
    }

    @Test
    fun testRetrieveFuncts2() {
        val instruction = "00000000101000010000000010010011"
        val desiredResult = mapOf(
            "funct3" to "000",
            "funct7" to ""
        )
        assertEquals(desiredResult, decoder.retrieveFuncts(instruction))
    }
}