package com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.MutablePsychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.Psychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations.Operation
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.channels.ReceiveChannel

typealias PsychomatrixOperationIsConvert = Pair<MutablePsychomatrix, Pair<Operation, Boolean>>

interface ModifyPsychomatrixUseCase {

    fun openPsychomatrixChangedSubscription(): ReceiveChannel<PsychomatrixOperationIsConvert>

    suspend fun makeConvert(psychomatrix: MutablePsychomatrix, operation: Operation): Deferred<Boolean>
    suspend fun makeInvert(psychomatrix: MutablePsychomatrix, operation: Operation): Deferred<Boolean>

    suspend fun getConverts(psychomatrix: Psychomatrix): Deferred<List<Operation>>
    suspend fun getInverts(psychomatrix: Psychomatrix): Deferred<List<Operation>>

    suspend fun getPsychomatrixHistory(psychomatrix: Psychomatrix): Deferred<List<Operation>>
}