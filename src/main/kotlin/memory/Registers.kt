package org.example.memory

class Registers {
    private var registers = Array(32) { 0 }

    init {
        registers.fill(0)
    }

    fun fetch(regNum: Int): Int {
        if (regNum < 0 || regNum >= registers.size) {
            throw IndexOutOfBoundsException("Invalid register: $regNum")
        }
        return registers[regNum]
    }

    fun write(regNum: Int, value: Int) {
        if (regNum < 0 || regNum >= registers.size) {
            throw IndexOutOfBoundsException("Invalid register: $regNum")
        }
        registers[regNum] = value
    }
}