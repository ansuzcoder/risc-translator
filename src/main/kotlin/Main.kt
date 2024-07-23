package org.example

import data_converter.convertToBinary
import data_converter.convertToHex

fun main() {
    val data = "16"
    println(convertToBinary(data))
    println(convertToHex(data))
}