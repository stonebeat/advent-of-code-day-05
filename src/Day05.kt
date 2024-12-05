import java.util.Collections

fun main() {
    fun part1(rulesRaw: List<String>, numbersRaw: List<String>): Int {
        val rules = convertToPairs(rulesRaw)
        val numbers = numbersRaw.map { it.split(',').map(String::toInt) }
        return numbers.filter { line ->
            val indexedNumbers = line.mapIndexed { index, value -> value to index }.toMap()
            rules.filter { rule -> indexedNumbers.containsKey(rule.first) && indexedNumbers.containsKey(rule.second) }
                .filterNot { indexedNumbers[it.first]!! < indexedNumbers[it.second]!! }.isEmpty()
        }.sumOf { it[it.size / 2] }
    }

    fun part2(rulesRaw: List<String>, numbersRaw: List<String>): Int {
        val rules = convertToPairs(rulesRaw)
        var numbers = numbersRaw.map { it.split(',').map(String::toInt) }

        // find invalid lines
        numbers = numbers.filter { line ->
            val indexedNumbers = line.mapIndexed { index, value -> value to index }.toMap()
            rules.filter { rule -> indexedNumbers.containsKey(rule.first) && indexedNumbers.containsKey(rule.second) }
                .any { indexedNumbers[it.first]!! > indexedNumbers[it.second]!! }
        }

        numbers.forEach { line ->
            do {
                val indexedNumbers = line.mapIndexed { index, value -> value to index }.toMap()
                val ruleCantUsed =
                    rules.filter { rule -> indexedNumbers.containsKey(rule.first) && indexedNumbers.containsKey(rule.second) }
                        .firstOrNull() { indexedNumbers[it.first]!! > indexedNumbers[it.second]!! }
                if (ruleCantUsed != null) {
                    Collections.swap(line, indexedNumbers[ruleCantUsed.first]!!, indexedNumbers[ruleCantUsed.second]!!)
                }
            } while (ruleCantUsed != null)
        }
        return numbers.sumOf { it[it.size / 2] }
    }

// Or read a large test input from the `src/Day01_test.txt` file:
    val testRulesRaw = readInput("Day05_rules_test")
    val testNumbersRawPart1 = readInput("Day05_numbers_test_part1")
    check(part1(testRulesRaw, testNumbersRawPart1) == 143)
    val testNumbersRawPart2 = readInput("Day05_numbers_test_part2")
    check(part2(testRulesRaw, testNumbersRawPart2) == 123)

// Read the input from the `src/Day01.txt` file.
    val rulesRaw = readInput("Day05_rules")
    val numbersRaw = readInput("Day05_numbers")
    part1(rulesRaw, numbersRaw).println()
    part2(rulesRaw, numbersRaw).println()
}

private fun convertToPairs(testRulesRaw: List<String>) =
    testRulesRaw.map { it.split('|').let { it[0].toInt() to it[1].toInt() } }
