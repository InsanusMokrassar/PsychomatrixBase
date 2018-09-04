package com.github.insanusmokrassar.PsychomatrixBase.domain.entities

import org.joda.time.DateTime
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.sqrt

private val dateFormat: DateFormat = SimpleDateFormat("dMyyyy", Locale.ROOT)
private fun Int.toDigits(): List<Byte> {
    return "$this".map { "$it".toByte() }
}

class Psychomatrix(val date: DateTime) {
    private val numbers: ByteArray = ByteArray(10)

    /**
     * Always array 4*4 of values. In rows was put columns (
     */
    val asMatrix: Array<Array<Byte>>
        get() = arrayOf(
            arrayOf(-1, -1, -1, getUpperDiagSum()),
            arrayOf(numbers[1], numbers[4], numbers[7], getRowSum(0)),
            arrayOf(numbers[2], numbers[5], numbers[8], getRowSum(1)),
            arrayOf(numbers[3], numbers[6], numbers[9], getRowSum(2)),
            arrayOf(getColumnSum(0), getColumnSum(1), getColumnSum(2), getDownDiagSum())
        )

    init {
        val dateDigits = dateFormat.format(date).map { "$it".toByte() }.toMutableList()

        val firstNumber = dateDigits.sum()
        val firstNumberDigits = firstNumber.toDigits()

        val secondNumber = firstNumberDigits.sum()
        val secondNumberDigits = secondNumber.toDigits()

        val thirdNumber = firstNumber - dateDigits[0] * 2
        val thirdNumberDigits = thirdNumber.toDigits()

        val fourthNumber = thirdNumberDigits.sum()
        val fourthNumberDigits = fourthNumber.toDigits()

        dateDigits.addAll(firstNumberDigits)
        dateDigits.addAll(secondNumberDigits)
        dateDigits.addAll(thirdNumberDigits)
        dateDigits.addAll(fourthNumberDigits)

        (0 until numbers.size).forEach {
            index ->
            numbers[index] = dateDigits.count {
                it == index.toByte()
            }.toByte()
        }
    }

    /**
     * @return count of numbers of `i`
     */
    operator fun get(i: Int): Byte {
        return numbers[i]
    }

    /**
     * 1 4 7 - zero row
     * 2 5 8 - first row
     * 3 6 9 - second row
     */
    fun getRowSum(i: Int): Byte {
        return (i + 1 until numbers.size step 3).map { numbers[it] }.sum().toByte()
    }

    /**
     * 1 4 7
     * 2 5 8
     * 3 6 9
     * | | |
     * z f s
     * e i e
     * r r c
     * o s o
     *   t n
     *     d
     */
    fun getColumnSum(i: Int): Byte {
        val side = sqrt(numbers.size.toDouble() - 1).toInt()
        val first = side * i + 1
        return (first until first + side).map { numbers[it] }.sum().toByte()
    }

    /**
     *        upper
     *       /
     * 1 4 7
     * 2 5 8
     * 3 6 9
     */
    fun getUpperDiagSum(): Byte {
        val side = sqrt(numbers.size.toDouble() - 1).toInt()
        return (side .. numbers.size - side step side - 1).map { numbers[it] }.sum().toByte()
    }

    /**
     * 1 4 7
     * 2 5 8
     * 3 6 9
     *      \
     *       down
     */
    fun getDownDiagSum(): Byte {
        val side = sqrt(numbers.size.toDouble() - 1).toInt()
        return (1 until numbers.size step side + 1).map { numbers[it] }.sum().toByte()
    }

    override fun toString(): String {
        return  ("             %2d\n" +
                "%2d %2d %2d %2d\n" +
                "%2d %2d %2d %2d\n" +
                "%2d %2d %2d %2d\n" +
                "%2d %2d %2d %2d").format(
                        getUpperDiagSum(),
                        numbers[1],
                        numbers[4],
                        numbers[7],
                        getRowSum(0),
                        numbers[2],
                        numbers[5],
                        numbers[8],
                        getRowSum(1),
                        numbers[3],
                        numbers[6],
                        numbers[9],
                        getRowSum(2),
                        getColumnSum(0),
                        getColumnSum(1),
                        getColumnSum(2),
                        getDownDiagSum()
                )
    }
}
