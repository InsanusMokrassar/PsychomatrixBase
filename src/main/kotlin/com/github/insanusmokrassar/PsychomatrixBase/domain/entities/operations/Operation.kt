package com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations

import java.io.IOException

private const val oneAsByte: Byte = 1
private const val twoAsByte: Byte = 2
private const val fourAsByte: Byte = 4
private const val fiveAsByte: Byte = 5
private const val sixAsByte: Byte = 6
private const val sevenAsByte: Byte = 7
private const val eightAsByte: Byte = 8
private const val nineAsByte: Byte = 9

class ConverterException: IOException("This converter can't convert input numbers")

val List<Operation>.canGrowSimpleWay: Boolean
    get() = contains(SixGrowSeven)

private val operations = listOf(
    TwoGrowFour,
    FourGrowTwo,
    OneGrowEight,
    EightGrowOne,
    SixGrowSeven,
    SevenGrowSix,
    FiveGrowNine,
    NineGrowFive
)

suspend fun availableConverts(numbers: MutableList<Byte>, operations: List<Operation>): List<Operation> {
    return operations.filter { it.canConvert(numbers, operations) }
}

suspend fun availableInverts(numbers: MutableList<Byte>, operations: List<Operation>): List<Operation> {
    return operations.filter { it.canInvert(numbers, operations) }
}

sealed class Operation {
    abstract suspend fun canConvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean
    abstract suspend fun canInvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean
    suspend fun convert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>? = null) {
        if (changesHistory ?.let { canConvert(numbers, changesHistory) } == true || changesHistory == null) {
            doConvert(numbers, changesHistory)
            changesHistory ?.add(this)
        }
    }

    suspend fun invert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>? = null) {
        if (changesHistory == null || (changesHistory.contains(this) && canInvert(numbers, changesHistory))) {
            doInvert(numbers, changesHistory)
            changesHistory ?.remove(this)
        }
    }

    protected abstract suspend fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?)
    protected abstract suspend fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?)
}

object TwoGrowFour : Operation() {
    override suspend fun canConvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.count { it == twoAsByte } / 2 > changesHistory.count { it == FourGrowTwo }
    }
    override suspend fun canInvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.contains(fourAsByte) && changesHistory.contains(this)
    }

    override suspend fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.apply {
            remove(twoAsByte)
            remove(twoAsByte)
            add(fourAsByte)
        }
    }

    override suspend fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.apply {
            add(twoAsByte)
            add(twoAsByte)
            remove(fourAsByte)
        }
    }
}

object FourGrowTwo : Operation() {
    override suspend fun canConvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.count { it == fourAsByte } > changesHistory.count { it == TwoGrowFour }
    }
    override suspend fun canInvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.count { it == twoAsByte } > 1 && changesHistory.contains(this)
    }

    override suspend fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        TwoGrowFour.invert(numbers)
    }

    override suspend fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        TwoGrowFour.convert(numbers)
    }
}

object EightGrowOne : Operation() {
    override suspend fun canConvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.contains(eightAsByte)
            && (TwoGrowFour.canConvert(numbers, changesHistory) || FourGrowTwo.canConvert(numbers, changesHistory))
    }
    override suspend fun canInvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.count { it == oneAsByte } > 1 && changesHistory.contains(this)
    }

    override suspend fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
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

    override suspend fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.apply {
            FourGrowTwo.invert(numbers, changesHistory)
            add(eightAsByte)
            remove(oneAsByte)
            remove(oneAsByte)
        }
    }
}

object OneGrowEight : Operation() {
    override suspend fun canConvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.count { it == oneAsByte} > 1
    }
    override suspend fun canInvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.contains(eightAsByte) && changesHistory.contains(this)
    }

    override suspend fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        EightGrowOne.invert(numbers)
    }

    override suspend fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        EightGrowOne.convert(numbers)
    }
}

object SixGrowSeven : Operation() {
    override suspend fun canConvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.contains(sixAsByte) && OneGrowEight.canConvert(numbers, changesHistory)
    }
    override suspend fun canInvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.contains(sevenAsByte) && EightGrowOne.canConvert(numbers, changesHistory) && changesHistory.contains(this)
            && !changesHistory.contains(FiveGrowNine) && !changesHistory.contains(NineGrowFive)
    }

    override suspend fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.apply {
            OneGrowEight.convert(numbers, changesHistory)
            remove(sixAsByte)
            add(sevenAsByte)
        }
    }

    override suspend fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.apply {
            OneGrowEight.invert(numbers, changesHistory)
            add(sixAsByte)
            remove(sevenAsByte)
        }
    }
}

object SevenGrowSix : Operation() {
    override suspend fun canConvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.contains(sevenAsByte) && EightGrowOne.canConvert(numbers, changesHistory)
            && !changesHistory.contains(FiveGrowNine) && !changesHistory.contains(NineGrowFive)
    }
    override suspend fun canInvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean {
        return numbers.contains(sixAsByte) && OneGrowEight.canConvert(numbers, changesHistory) && changesHistory.contains(this)
    }

    override suspend fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        EightGrowOne.convert(numbers, changesHistory)
        SixGrowSeven.invert(numbers)
    }

    override suspend fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        EightGrowOne.invert(numbers, changesHistory)
        SixGrowSeven.convert(numbers)
    }
}

object FiveGrowNine : Operation() {
    override suspend fun canConvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean {
        return changesHistory.canGrowSimpleWay
            && (
                numbers.count {
                    it == fiveAsByte
                } / 2 - (changesHistory.count { it == NineGrowFive })
            ) > changesHistory.count { it == this }
    }

    override suspend fun canInvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean {
        return changesHistory.contains(this)
    }

    override suspend fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.add(nineAsByte)
    }

    override suspend fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.remove(nineAsByte)
    }
}

object NineGrowFive : Operation() {
    override suspend fun canConvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean {
        return changesHistory.canGrowSimpleWay
            && (numbers.count { it == nineAsByte } - changesHistory.count { it == FiveGrowNine }) > changesHistory.count { it == this }
    }

    override suspend fun canInvert(numbers: MutableList<Byte>, changesHistory: List<Operation>): Boolean {
        return changesHistory.contains(this)
    }

    override suspend fun doConvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.add(fiveAsByte)
        numbers.add(fiveAsByte)
    }

    override suspend fun doInvert(numbers: MutableList<Byte>, changesHistory: MutableList<Operation>?) {
        numbers.remove(fiveAsByte)
        numbers.remove(fiveAsByte)
    }
}

class GrowCustom()