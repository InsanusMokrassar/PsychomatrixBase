package com.github.insanusmokrassar.PsychomatrixBase.domain.entities

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations.Operation
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations.availableConverts
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations.availableInverts
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import org.joda.time.DateTime

class MutablePsychomatrix(date: DateTime) : Psychomatrix(date) {
    private var mutableNumbers = super.numbers.toMutableList()

    override val numbers: ByteArray
        get() = mutableNumbers.toByteArray()

    private val mutableOperationsHistory: MutableList<Operation> = ArrayList()

    val availableOperations: Deferred<List<Operation>>
        get() = async {
            availableConverts.await().plus(availableInverts.await())
        }

    private val availableConverts: Deferred<List<Operation>>
        get() = async {
            availableConverts(mutableNumbers, mutableOperationsHistory)
        }
    private val availableInverts: Deferred<List<Operation>>
        get() = async {
            availableInverts(mutableNumbers, mutableOperationsHistory)
        }

    suspend fun applyConvert(operation: Operation): Boolean {
        if (operation in availableConverts.await()) {
            operation.convert(mutableNumbers, mutableOperationsHistory)
            return true
        }
        return false
    }

    suspend fun applyInvert(operation: Operation): Boolean {
        if (operation in availableInverts.await()) {
            operation.invert(mutableNumbers, mutableOperationsHistory)
            return true
        }
        return false
    }
}
