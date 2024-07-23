package data_converter

/*
* Check whether input is all numbers
*/
fun isNumerical(inputData: String): Boolean {
    return inputData.all { it.isDigit() }
}

/*
* Check whether input is all letters
*/
fun isAlphabetic(inputData: String): Boolean {
    return inputData.all { it.isLetter() }
}