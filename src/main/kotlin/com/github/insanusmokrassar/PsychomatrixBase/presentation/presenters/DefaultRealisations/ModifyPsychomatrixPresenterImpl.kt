package com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.DefaultRealisations

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.ModifyPsychomatrixUseCase
import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.PsychomatrixOperationIsConvert
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.MutablePsychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.Psychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations.*
import com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.ModifyPsychomatrixPresenter
import com.github.insanusmokrassar.PsychomatrixBase.utils.extensions.subscribe
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.launch

class ModifyPsychomatrixPresenterImpl(
    private val modifyPsychomatrixUseCase: ModifyPsychomatrixUseCase
) : ModifyPsychomatrixPresenter {

    override fun openPsychomatrixChangedSubscription(): ReceiveChannel<PsychomatrixOperationIsConvert> {
        return modifyPsychomatrixUseCase.openPsychomatrixChangedSubscription()
    }

    override fun makeConvert(psychomatrix: MutablePsychomatrix, operation: Operation): Deferred<Boolean> {
        return modifyPsychomatrixUseCase.makeConvert(psychomatrix, operation)
    }

    override fun makeInvert(psychomatrix: MutablePsychomatrix, operation: Operation): Deferred<Boolean> {
        return modifyPsychomatrixUseCase.makeInvert(psychomatrix, operation)
    }

    override fun getConverts(psychomatrix: Psychomatrix): Deferred<List<Operation>> {
        return modifyPsychomatrixUseCase.getConverts(psychomatrix)
    }

    override fun getInverts(psychomatrix: Psychomatrix): Deferred<List<Operation>> {
        return modifyPsychomatrixUseCase.getInverts(psychomatrix)
    }

    override fun getPsychomatrixHistory(psychomatrix: Psychomatrix): Deferred<List<Operation>> {
        return getInverts(psychomatrix)
    }
}