package com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters

import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.Psychomatrix
import kotlinx.coroutines.channels.ReceiveChannel
import org.joda.time.DateTime

interface DatePickerPresenter {

    suspend fun openPsychomatrixCreatedSubscription(): ReceiveChannel<Psychomatrix>

    fun userPickDate(date: Long)
    fun userPickDate(date: DateTime)

}
