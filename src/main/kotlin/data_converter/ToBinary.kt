package data_converter

/*
* Convert numbers and chars to binary
*/
fun convertToBinary(data: String): String {
    var binaryData = ""
    if (isNumerical(data)) {
        var intData = data.toInt()
        do {
            binaryData += (intData % 2).toString()
            intData /= 2
        } while ((intData != 0))
    } else if (isAlphabetic(data)) {
        for (char in data) {
            var charNumber = char.code
            var binaryChar = ""
            do {
                binaryChar += (charNumber % 2).toString()
                charNumber /= 2
            } while (charNumber != 0)
            binaryData += binaryChar.padStart(8, '0')
        }
    } else {
        throw IllegalArgumentException("Invalid input")
    }

    return binaryData.reversed()
}