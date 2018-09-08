package com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations

private const val zeroAsByte: Byte = 0

val List<Operation>.canGrowSimpleWay: Boolean
    get() = contains(SixGrowSeven)

val List<Operation>.containsSimpleGrows: Boolean
    get() = firstOrNull { it == FiveGrowNine || it == NineGrowFive || it is GrowCustom } != null

private val operations: List<Operation> = listOf(
    TwoGrowFour,
    FourGrowTwo,
    OneGrowEight,
    EightGrowOne,
    SixGrowSeven,
    SevenGrowSix,
    FiveGrowNine,
    NineGrowFive,
    *(1 .. 9).map {
        GrowCustom(it)
    }.toTypedArray()
)

fun availableConverts(numbers: MutableList<Byte>, history: List<Operation>): List<Operation> {
    return operations.filter { it.canConvert(numbers, history) }
}

fun availableInverts(numbers: MutableList<Byte>, history: List<Operation>): List<Operation> {
    return operations.filter { it.canInvert(numbers, history) }
}

sealed class Operation {
    abstract fun canConvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean
    abstract fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean
    fun convert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>? = null) {
        if (changesHistory ?.let { canConvert(numbers, changesHistory) } == true || changesHistory == null) {
            doConvert(numbers, changesHistory)
            changesHistory ?.add(this)
        }
    }

    fun invert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>? = null) {
        if (changesHistory == null || (changesHistory.contains(this) && canInvert(numbers, changesHistory))) {
            doInvert(numbers, changesHistory)
            changesHistory ?.remove(this)
        }
    }

    protected abstract fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?)
    protected abstract fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?)
}

object TwoGrowFour : Operation() {
    override fun canConvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers[2] / 2 > changesHistory.count { it == FourGrowTwo }
    }
    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers[4] != zeroAsByte && changesHistory.contains(this)
    }

    override fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.apply {
            numbers[2]--
            numbers[2]--
            numbers[4]++
        }
    }

    override fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.apply {
            numbers[2]++
            numbers[2]++
            numbers[4]--
        }
    }
}

object FourGrowTwo : Operation() {
    override fun canConvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers[4] > changesHistory.count { it == TwoGrowFour }
    }
    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers[2] > 1 && changesHistory.contains(this)
    }

    override fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        TwoGrowFour.invert(numbers)
    }

    override fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        TwoGrowFour.convert(numbers)
    }
}

object EightGrowOne : Operation() {
    override fun canConvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers[8] > changesHistory.count { it == OneGrowEight }
            && (TwoGrowFour.canConvert(numbers, changesHistory) || FourGrowTwo.canConvert(numbers, changesHistory))
    }
    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers[1] > 1 && changesHistory.contains(this)
    }

    override fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.apply {
            if (numbers[2] < 2) {
                FourGrowTwo.convert(numbers, changesHistory)
            }
            numbers[2]--
            numbers[2]--
            numbers[8]--
            numbers[1]++
            numbers[1]++
        }
    }

    override fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.apply {
            numbers[1]--
            numbers[1]--
            numbers[8]++
            numbers[2]++
            numbers[2]++
        }
    }
}

object OneGrowEight : Operation() {
    override fun canConvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers[1] / 2 > changesHistory.count { it == EightGrowOne }
    }
    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers[8] > 0 && changesHistory.contains(this)
    }

    override fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        EightGrowOne.invert(numbers)
    }

    override fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        EightGrowOne.convert(numbers)
    }
}

object SixGrowSeven : Operation() {
    override fun canConvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers[6] > changesHistory.count { it == SevenGrowSix }
    }
    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers[7] > if (changesHistory.canGrowSimpleWay) {
            1
        } else {
            0
        } && changesHistory.contains(this)
    }

    override fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.apply {
            numbers[6]--
            numbers[7]++
        }
    }

    override fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.apply {
            numbers[7]--
            numbers[6]++
        }
    }
}

object SevenGrowSix : Operation() {
    override fun canConvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers[7] - changesHistory.count { it == SixGrowSeven } > if (changesHistory.canGrowSimpleWay) {
            1
        } else {
            0
        }
    }
    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers[6] > 0 && changesHistory.contains(this)
    }

    override fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        SixGrowSeven.invert(numbers)
    }

    override fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        SixGrowSeven.convert(numbers)
    }
}

object FiveGrowNine : Operation() {
    override fun canConvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return changesHistory.canGrowSimpleWay
            && (
                numbers[5] / 2 - (changesHistory.count { it == NineGrowFive })
            ) > changesHistory.count { it == this }
    }

    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return changesHistory.contains(this)
    }

    override fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers[9]++
    }

    override fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers[9]--
    }
}

object NineGrowFive : Operation() {
    override fun canConvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return changesHistory.canGrowSimpleWay
            && (numbers[9] - changesHistory.count { it == FiveGrowNine }) > changesHistory.count { it == this }
    }

    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return changesHistory.contains(this)
    }

    override fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers[5]++
        numbers[5]++
    }

    override fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers[5]--
        numbers[5]--
    }
}

class GrowCustom internal constructor(val number: Int) : Operation() {
    override fun canConvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return changesHistory.canGrowSimpleWay
                && changesHistory.firstOrNull { it is GrowCustom && it.number == number } == null
    }

    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return changesHistory.firstOrNull { it is GrowCustom && it.number == number } != null
            && numbers[number] > 0
    }

    override fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers[number]++
    }

    override fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers[number]--
    }
}
