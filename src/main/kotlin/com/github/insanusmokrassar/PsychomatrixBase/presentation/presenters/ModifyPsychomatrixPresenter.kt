package com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.PsychomatrixOperationIsConvert
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.Psychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations.Operation
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.ReceiveChannel

interface ModifyPsychomatrixPresenter {

    fun openPsychomatrixChangedSubscription(): ReceiveChannel<PsychomatrixOperationIsConvert>

    suspend fun twoGrowFourAvailable(psychomatrix: Psychomatrix): Deferred<Boolean>
    suspend fun fourGrowTwoAvailable(psychomatrix: Psychomatrix): Deferred<Boolean>

    suspend fun oneGrowEightAvailable(psychomatrix: Psychomatrix): Deferred<Boolean>
    suspend fun eightGrowOneAvailable(psychomatrix: Psychomatrix): Deferred<Boolean>

    suspend fun sixGrowSevenAvailable(psychomatrix: Psychomatrix): Deferred<Boolean>
    suspend fun sevenGrowSixAvailable(psychomatrix: Psychomatrix): Deferred<Boolean>

    suspend fun fiveGrowNineAvailable(psychomatrix: Psychomatrix): Deferred<Boolean>
    suspend fun nineGrowFiveAvailable(psychomatrix: Psychomatrix): Deferred<Boolean>

    suspend fun customGrowAvailable(psychomatrix: Psychomatrix, number: Byte): Deferred<Boolean>

    suspend fun rollback(psychomatrix: Psychomatrix, operations: Int): Job

    suspend fun history(psychomatrix: Psychomatrix): Deferred<List<Operation>>
}