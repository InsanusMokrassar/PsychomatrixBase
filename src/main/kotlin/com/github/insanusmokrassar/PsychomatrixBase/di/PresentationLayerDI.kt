package com.github.insanusmokrassar.PsychomatrixBase.di

import com.github.insanusmokrassar.PsychomatrixBase.data.repository.HistoryDatesRepository
import com.github.insanusmokrassar.PsychomatrixBase.data.repository.PsychomatrixCeilDescriptionRepository
import com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.*

interface PresentationLayerDI : UseCasesDI {

    val datePickerPresenter: DatePickerPresenter
    val modifyPsychomatrixPresenter: ModifyPsychomatrixPresenter
    val ceilDescriptionPresenter: CeilDescriptionPresenter

    val historyDatesRepository: HistoryDatesRepository
    val psychomatrixCeilDescriptionRepository: PsychomatrixCeilDescriptionRepository
}