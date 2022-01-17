import java.util.LinkedList

class Lottery {

    private val lotteryNumbers = getRandomLotteryNumbers()
    private val bonusNumber = getBonusNumber(lotteryNumbers)

    fun drawLottery(count: Int) {
        val summary = arrayOf(0, 0, 0, 0, 0)

        val numbersList = LinkedList<List<Int>>()
        for (i in count downTo 1) {
            numbersList.add(getRandomLotteryNumbers())
        }

        for (numbers in numbersList) {
            val result = calculateResult(numbers)
            if (result > 0) {
                summary[result - 1] += 1
            }
        }

        println(
            """
            |발급 번호
            |${numbersList.map { getPrettyString(it) }.joinToString("\n")}
            |당첨 번호 : ${getPrettyString(lotteryNumbers)} + $bonusNumber
            |결과 : 1등 ${summary[0]}개, 2등 ${summary[1]}개, 3등 ${summary[2]}개, 4등 ${summary[3]}개, 5등 ${summary[4]}개 당첨!    
            """.trimMargin()
        )
    }

    private fun getRandomNumber(): Int {
        return (1..45).random()
    }

    private fun getRandomLotteryNumbers(): List<Int> {
        val numbers = HashSet<Int>()
        while (numbers.size < 6) {
            numbers.add(getRandomNumber())
        }

        return numbers.toList().sorted()
    }

    private fun getBonusNumber(lotteryNumbers: List<Int>): Int {
        var bonusNumber = getRandomNumber()
        while (lotteryNumbers.contains(bonusNumber)) {
            bonusNumber = getRandomNumber()
        }

        return bonusNumber
    }

    private fun calculateResult(numbers: List<Int>): Int {
        var matchCount = 0
        for (lotteryNumber in lotteryNumbers) {
            if (numbers.contains(lotteryNumber)) {
                matchCount += 1
            }
        }

        return when (matchCount) {
            6 -> 1
            5 -> return if (numbers.contains(bonusNumber)) 2 else 3
            4 -> 4
            3 -> 5
            else -> -1
        }
    }

    private fun getPrettyString(numbers: List<Int>): String {
        return numbers.joinToString(", ")
    }

}