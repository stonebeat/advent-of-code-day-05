fun main() {
    fun part1(rulesRaw: List<String>, numbersRaw: List<String>): Int {
        val testRules = convertToPairs(rulesRaw)
        val testNumbers = numbersRaw.map { it.split(',').map(String::toInt) }
        return testNumbers.filter { line ->
            val indexedNumbers = line.mapIndexed { index, value -> value to index }.toMap()
            testRules.filter { it -> indexedNumbers.containsKey(it.first) && indexedNumbers.containsKey(it.second) }
                .filterNot { indexedNumbers[it.first]!! < indexedNumbers[it.second]!! }.isEmpty()
        }.sumOf { it[it.size / 2] }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testRulesRaw = readInput("Day05_rules_test")
    val testNumbersRaw = readInput("Day05_numbers_test")
    check(part1(testRulesRaw, testNumbersRaw) == 143)

    // Read the input from the `src/Day01.txt` file.
    val rulesRaw = readInput("Day05_rules")
    val numbersRaw = readInput("Day05_numbers")
    part1(rulesRaw, numbersRaw).println()
//    part2(input).println()
}

private fun convertToPairs(testRulesRaw: List<String>) =
    testRulesRaw.map { it.split('|').let { it[0].toInt() to it[1].toInt() } }
