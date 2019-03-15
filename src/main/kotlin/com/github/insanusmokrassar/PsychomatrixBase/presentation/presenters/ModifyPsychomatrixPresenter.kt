package com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.PsychomatrixOperationIsConvert
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.MutablePsychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.Psychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations.Operation
import kotlinx.coroutines.channels.ReceiveChannel

interface ModifyPsychomatrixPresenter {

    fun openPsychomatrixChangedSubscription(): ReceiveChannel<PsychomatrixOperationIsConvert>

    fun makeConvert(psychomatrix: MutablePsychomatrix, operation: Operation): Boolean
    fun makeInvert(psychomatrix: MutablePsychomatrix, operation: Operation): Boolean

    fun getConverts(psychomatrix: Psychomatrix): List<Operation>
    fun getInverts(psychomatrix: Psychomatrix): List<Operation>

    fun getPsychomatrixHistory(psychomatrix: Psychomatrix): List<Operation>
}