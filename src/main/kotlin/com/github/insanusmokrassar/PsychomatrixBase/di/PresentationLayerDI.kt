package com.github.insanusmokrassar.PsychomatrixBase.di

import com.github.insanusmokrassar.PsychomatrixBase.data.repository.HistoryDatesRepository
import com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.DatePickerPresenter
import com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.ModifyPsychomatrixPresenter

interface PresentationLayerDI : UseCasesDI {

    val datePickerPresenter: DatePickerPresenter

    val historyDatesRepository: HistoryDatesRepository

    val modifyPsychomatrixPresenter: ModifyPsychomatrixPresenter
}