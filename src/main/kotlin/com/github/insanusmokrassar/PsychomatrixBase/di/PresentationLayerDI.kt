package com.github.insanusmokrassar.PsychomatrixBase.di

import com.github.insanusmokrassar.PsychomatrixBase.data.repository.HistoryDatesRepository
import com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.DatePickerPresenter

interface PresentationLayerDI : UseCasesDI {

    val datePickerPresenter: DatePickerPresenter

    val historyDatesRepository: HistoryDatesRepository
}