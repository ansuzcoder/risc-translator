package org.example.memory

class Memory(size: Int) {
    private var memoryArray = Array(size) { 0u }

    init {
        memoryArray.fill(0u)
    }

    fun fetch(address: Int): UInt {
        if (address < 0 || address >= memoryArray.size) {
            throw IndexOutOfBoundsException("Invalid address: $address")
        }
        return memoryArray[address]
    }

    fun write(address: Int, value: UInt) {
        if (address < 0 || address >= memoryArray.size) {
            throw IndexOutOfBoundsException("Invalid address: $address")
        }
        memoryArray[address] = value
    }
}