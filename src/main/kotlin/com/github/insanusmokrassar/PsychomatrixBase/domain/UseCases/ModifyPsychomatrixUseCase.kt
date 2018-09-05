package com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.Psychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations.Operation
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.channels.ReceiveChannel

typealias PsychomatrixOperationIsConvert = Pair<Psychomatrix, Pair<Operation, Boolean>>

interface ModifyPsychomatrixUseCase {

    fun openPsychomatrixChangedSubscription(): ReceiveChannel<PsychomatrixOperationIsConvert>

    suspend fun makeConvert(psychomatrix: Psychomatrix, operation: Operation): Deferred<Boolean>
    suspend fun makeInvert(psychomatrix: Psychomatrix, operation: Operation): Deferred<Boolean>

    suspend fun getConverts(psychomatrix: Psychomatrix): Deferred<List<Operation>>
    suspend fun getInverts(psychomatrix: Psychomatrix): Deferred<List<Operation>>

    suspend fun getOperations(psychomatrix: Psychomatrix): Deferred<List<Operation>> = async {
        listOf(
            getConverts(psychomatrix),
            getInverts(psychomatrix)
        ).flatMap {
            it.await()
        }
    }

    suspend fun getPsychomatrixHistory(psychomatrix: Psychomatrix): Deferred<List<Operation>>
}