package data_converter

/*
* Convert numbers and chars to hexadecimal
*/
fun convertToHex(data: String): String {
    var hexData = ""
    val hexChars = "0123456789ABCDEF"
    if (isNumerical(data)) {
        var intData = data.toInt()
        do {
            val remainder = intData % 16
            hexData += hexChars[remainder]
            intData /= 16
        } while (intData != 0)
    } else if (isAlphabetic(data)) {
        for (char in data) {
            var charNumber = char.code
            var hexChar = ""
            do {
                val remainder = charNumber % 16
                hexChar += hexChars[remainder]
                charNumber /= 16
            } while (charNumber != 0)
        }
    } else {
        throw IllegalArgumentException("Invalid input")
    }
    return hexData.reversed()
}