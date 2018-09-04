package com.github.insanusmokrassar.PsychomatrixBase.di.realisations

import com.github.insanusmokrassar.PsychomatrixBase.di.PresentationLayerDI
import com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.DatePickerPresenter
import com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.DefaultRealisations.DatePickerPresenterImpl

open class PresentationLayerDIImpl : PresentationLayerDI, UseCasesDIImpl() {

    override val datePickerPresenter: DatePickerPresenter by lazy {
        DatePickerPresenterImpl(
            calculatePsychomatrixByDate
        )
    }

}