package com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.DefaultRealisations

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.UserCalculatePsychomatrixByDate
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.Psychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.DatePickerPresenter
import com.github.insanusmokrassar.PsychomatrixBase.utils.extensions.SUBSCRIPTIONS_MEDIUM
import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.launch
import org.joda.time.DateTime

class DatePickerPresenterImpl(
    private val userCalculatePsychomatrixByDate: UserCalculatePsychomatrixByDate
) : DatePickerPresenter {
    private val psychomatrixCreateBroadcastChannel = BroadcastChannel<Psychomatrix>(SUBSCRIPTIONS_MEDIUM)

    override suspend fun openPsychomatrixCreatedSubscription(): ReceiveChannel<Psychomatrix> {
        return psychomatrixCreateBroadcastChannel.openSubscription()
    }

    override fun userPickDate(date: Long) {
        userPickDate(DateTime(date))
    }

    override fun userPickDate(date: DateTime) {
        launch {
            userCalculatePsychomatrixByDate.calculate(date).await().also {
                psychomatrixCreateBroadcastChannel.send(it)
            }
        }
    }
}
