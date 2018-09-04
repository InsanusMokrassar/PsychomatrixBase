package com.github.insanusmokrassar.PsychomatrixBase.di.realisations

import com.github.insanusmokrassar.PsychomatrixBase.di.PresentationLayerDI
import com.github.insanusmokrassar.PsychomatrixBase.di.UseCasesDI
import com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.DatePickerPresenter
import com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.DefaultRealisations.DatePickerPresenterImpl

open class PresentationLayerDIImpl(
    useCasesDI: UseCasesDI
) : PresentationLayerDI, UseCasesDI by useCasesDI {

    override val datePickerPresenter: DatePickerPresenter by lazy {
        DatePickerPresenterImpl(
            calculatePsychomatrixByDate
        )
    }

}