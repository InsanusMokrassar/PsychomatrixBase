package com.github.insanusmokrassar.PsychomatrixBase.domain.entities

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import kotlin.math.sqrt

private val dateFormat: DateTimeFormatter = DateTimeFormat.forPattern("dMyyyy")
private fun Int.toDigits(): List<Byte> {
    return "$this".map { "$it".toByte() }
}

val UPPER_DIAG_MATRIX_INDEX = 3 to 0
val DOWN_DIAG_MATRIX_INDEX = 3 to 4

val FIRST_ROW_SUM_MATRIX_INDEX = 3 to 1
val SECOND_ROW_SUM_MATRIX_INDEX = 3 to 2
val THIRD_ROW_SUM_MATRIX_INDEX = 3 to 3

val FIRST_COLUMN_SUM_MATRIX_INDEX = 0 to 4
val SECOND_COLUMN_SUM_MATRIX_INDEX = 1 to 4
val THIRD_COLUMN_SUM_MATRIX_INDEX = 2 to 4

val EMPTY_CEILS_OF_MATRIX = (0 until 3).map { it to 0 }

open class Psychomatrix(val date: DateTime) {
    protected open val numbers: ByteArray = ByteArray(10)

    /**
     * Always array 4*5 of values. In rows was put columns
     *
     */
    val asMatrix: Array<Array<PsychomatrixCeilInfo>>
        get() = arrayOf(
            arrayOf(
                null,
                get(1),
                get(2),
                get(3),
                getColumnSum(0)
            ),
            arrayOf(
                null,
                get(4),
                get(5),
                get(6),
                getColumnSum(1)
            ),
            arrayOf(
                null,
                get(7),
                get(8),
                get(9),
                getColumnSum(2)
            ),
            arrayOf(
                getUpperDiagSum(),
                getRowSum(0),
                getRowSum(1),
                getRowSum(2),
                getDownDiagSum()
            )
        ).mapIndexed {
            x, yArray ->
            yArray.mapIndexed {
                y, value ->
                PsychomatrixCeilInfo(
                    x,
                    y,
                    value ?.toInt()
                )
            }.toTypedArray()
        }.toTypedArray()

    init {
        val dateDigits = dateFormat.print(date).map { "$it".toByte() }.toMutableList()

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
