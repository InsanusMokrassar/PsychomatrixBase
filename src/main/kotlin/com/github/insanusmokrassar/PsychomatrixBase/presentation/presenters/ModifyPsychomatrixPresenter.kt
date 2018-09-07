package com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.PsychomatrixOperationIsConvert
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.MutablePsychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.Psychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations.Operation
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.ReceiveChannel

interface ModifyPsychomatrixPresenter {

    fun openPsychomatrixChangedSubscription(): ReceiveChannel<PsychomatrixOperationIsConvert>

    fun makeConvert(psychomatrix: MutablePsychomatrix, operation: Operation): Deferred<Boolean>
    fun makeInvert(psychomatrix: MutablePsychomatrix, operation: Operation): Deferred<Boolean>

    fun getConverts(psychomatrix: Psychomatrix): Deferred<List<Operation>>
    fun getInverts(psychomatrix: Psychomatrix): Deferred<List<Operation>>

    fun getPsychomatrixHistory(psychomatrix: Psychomatrix): Deferred<List<Operation>>
}