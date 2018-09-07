package com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations

private const val oneAsByte: Byte = 1
private const val twoAsByte: Byte = 2
private const val fourAsByte: Byte = 4
private const val fiveAsByte: Byte = 5
private const val sixAsByte: Byte = 6
private const val sevenAsByte: Byte = 7
private const val eightAsByte: Byte = 8
private const val nineAsByte: Byte = 9

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
        GrowCustom(it.toByte())
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
        return numbers.count { it == twoAsByte } / 2 > changesHistory.count { it == FourGrowTwo }
    }
    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.contains(fourAsByte) && changesHistory.contains(this)
    }

    override fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.apply {
            remove(twoAsByte)
            remove(twoAsByte)
            add(fourAsByte)
        }
    }

    override fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.apply {
            add(twoAsByte)
            add(twoAsByte)
            remove(fourAsByte)
        }
    }
}

object FourGrowTwo : Operation() {
    override fun canConvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.count { it == fourAsByte } > changesHistory.count { it == TwoGrowFour }
    }
    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.count { it == twoAsByte } > 1 && changesHistory.contains(this)
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
        return numbers.contains(eightAsByte)
            && (TwoGrowFour.canConvert(numbers, changesHistory) || FourGrowTwo.canConvert(numbers, changesHistory))
    }
    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.count { it == oneAsByte } > 1 && changesHistory.contains(this)
    }

    override fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.apply {
            if (count { it == twoAsByte} <= 1) {
                FourGrowTwo.convert(numbers, changesHistory)
            }
            remove(twoAsByte)
            remove(twoAsByte)
            remove(eightAsByte)
            add(oneAsByte)
            add(oneAsByte)
        }
    }

    override fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.apply {
            FourGrowTwo.invert(numbers, changesHistory)
            add(eightAsByte)
            remove(oneAsByte)
            remove(oneAsByte)
        }
    }
}

object OneGrowEight : Operation() {
    override fun canConvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.count { it == oneAsByte} > 1
    }
    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.contains(eightAsByte) && changesHistory.contains(this)
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
        return numbers.contains(sixAsByte)
    }
    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.contains(sevenAsByte) && changesHistory.contains(this)
            && !changesHistory.containsSimpleGrows
    }

    override fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.apply {
            remove(sixAsByte)
            add(sevenAsByte)
        }
    }

    override fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.apply {
            add(sixAsByte)
            remove(sevenAsByte)
        }
    }
}

object SevenGrowSix : Operation() {
    override fun canConvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.contains(sevenAsByte)
            && !changesHistory.containsSimpleGrows
    }
    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.contains(sixAsByte) && changesHistory.contains(this)
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
                numbers.count {
                    it == fiveAsByte
                } / 2 - (changesHistory.count { it == NineGrowFive })
            ) > changesHistory.count { it == this }
    }

    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return changesHistory.contains(this)
    }

    override fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.add(nineAsByte)
    }

    override fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.remove(nineAsByte)
    }
}

object NineGrowFive : Operation() {
    override fun canConvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return changesHistory.canGrowSimpleWay
            && (numbers.count { it == nineAsByte } - changesHistory.count { it == FiveGrowNine }) > changesHistory.count { it == this }
    }

    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return changesHistory.contains(this)
    }

    override fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.add(fiveAsByte)
        numbers.add(fiveAsByte)
    }

    override fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.remove(fiveAsByte)
        numbers.remove(fiveAsByte)
    }
}

class GrowCustom internal constructor(val number: Byte) : Operation() {
    override fun canConvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return changesHistory.canGrowSimpleWay
                && changesHistory.firstOrNull { it is GrowCustom && it.number == number } == null
    }

    override fun canInvert(numbers: List<Byte>, changesHistory: List<Operation>): Boolean {
        return changesHistory.firstOrNull { it is GrowCustom && it.number == number } != null
            && numbers.contains(number)
    }

    override fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.add(number)
    }

    override fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.remove(number)
    }
}
