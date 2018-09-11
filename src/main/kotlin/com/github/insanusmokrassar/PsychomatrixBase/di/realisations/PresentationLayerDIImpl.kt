package com.github.insanusmokrassar.PsychomatrixBase.di.realisations

import com.github.insanusmokrassar.PsychomatrixBase.di.PresentationLayerDI
import com.github.insanusmokrassar.PsychomatrixBase.di.UseCasesDI
import com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.*
import com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.DefaultRealisations.*

abstract class PresentationLayerDIImpl(
    useCasesDI: UseCasesDI
) : PresentationLayerDI, UseCasesDI by useCasesDI {

    override val datePickerPresenter: DatePickerPresenter by lazy {
        DatePickerPresenterImpl(
            calculatePsychomatrixByDateUseCase
        )
    }

    override val modifyPsychomatrixPresenter: ModifyPsychomatrixPresenter by lazy {
        ModifyPsychomatrixPresenterImpl(
            modifyPsychomatrixUseCase
        )
    }

    override val ceilDescriptionPresenter: CeilDescriptionPresenter by lazy {
        CeilDescriptionPresenterImpl(
            ceilDescriptionUseCase
        )
    }
}