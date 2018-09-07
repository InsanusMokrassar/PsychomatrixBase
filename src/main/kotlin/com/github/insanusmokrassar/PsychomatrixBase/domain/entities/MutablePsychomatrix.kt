package com.github.insanusmokrassar.PsychomatrixBase.domain.entities

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations.Operation
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations.availableConverts
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations.availableInverts
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import org.joda.time.DateTime

class MutablePsychomatrix(date: DateTime) : Psychomatrix(date) {
    private var mutableNumbers = calculateNumbers(date).toMutableList()

    override val numbers: ByteArray
        get() = mutableNumbers.toByteArray()

    private val mutableOperationsHistory: MutableList<Operation> = ArrayList()

    constructor(psychomatrix: Psychomatrix): this(psychomatrix.date)

    val operationsHistory: List<Operation>
        get() = mutableOperationsHistory

    val availableConverts: List<Operation>
        get() = availableConverts(mutableNumbers, mutableOperationsHistory)
    val availableInverts: List<Operation>
        get() = availableInverts(mutableNumbers, mutableOperationsHistory)


    fun applyConvert(operation: Operation): Boolean {
        if (operation in availableConverts) {
            operation.convert(mutableNumbers, mutableOperationsHistory)
            return true
        }
        return false
    }

    fun applyInvert(operation: Operation): Boolean {
        if (operation in availableInverts) {
            operation.invert(mutableNumbers, mutableOperationsHistory)
            return true
        }
        return false
    }
}
