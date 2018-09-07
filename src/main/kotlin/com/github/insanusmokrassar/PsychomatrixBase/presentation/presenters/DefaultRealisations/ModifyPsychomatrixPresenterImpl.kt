package com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.DefaultRealisations

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.ModifyPsychomatrixUseCase
import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.PsychomatrixOperationIsConvert
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.MutablePsychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.Psychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.operations.Operation
import com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.ModifyPsychomatrixPresenter
import kotlinx.coroutines.experimental.channels.ReceiveChannel

class ModifyPsychomatrixPresenterImpl(
    private val modifyPsychomatrixUseCase: ModifyPsychomatrixUseCase
) : ModifyPsychomatrixPresenter {

    override fun openPsychomatrixChangedSubscription(): ReceiveChannel<PsychomatrixOperationIsConvert> {
        return modifyPsychomatrixUseCase.openPsychomatrixChangedSubscription()
    }

    override fun makeConvert(psychomatrix: MutablePsychomatrix, operation: Operation): Boolean {
        return modifyPsychomatrixUseCase.makeConvert(psychomatrix, operation)
    }

    override fun makeInvert(psychomatrix: MutablePsychomatrix, operation: Operation): Boolean {
        return modifyPsychomatrixUseCase.makeInvert(psychomatrix, operation)
    }

    override fun getConverts(psychomatrix: Psychomatrix): List<Operation> {
        return modifyPsychomatrixUseCase.getConverts(psychomatrix)
    }

    override fun getInverts(psychomatrix: Psychomatrix): List<Operation> {
        return modifyPsychomatrixUseCase.getInverts(psychomatrix)
    }

    override fun getPsychomatrixHistory(psychomatrix: Psychomatrix): List<Operation> {
        return getInverts(psychomatrix)
    }
}