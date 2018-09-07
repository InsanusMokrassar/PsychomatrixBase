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

    fun makeConvert(psychomatrix: MutablePsychomatrix, operation: Operation): Boolean
    fun makeInvert(psychomatrix: MutablePsychomatrix, operation: Operation): Boolean

    fun getConverts(psychomatrix: Psychomatrix): List<Operation>
    fun getInverts(psychomatrix: Psychomatrix): List<Operation>

    fun getPsychomatrixHistory(psychomatrix: Psychomatrix): List<Operation>
}