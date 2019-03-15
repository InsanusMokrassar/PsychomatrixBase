package com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.DefaultRealisations

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CalculatePsychomatrixByDateUseCase
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.Psychomatrix
import com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.DatePickerPresenter
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import org.joda.time.DateTime

class DatePickerPresenterImpl(
    private val CalculatePsychomatrixByDateUseCase: CalculatePsychomatrixByDateUseCase,
    broadcastChannelCapacity: Int = 4
) : DatePickerPresenter {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val psychomatrixCreateBroadcastChannel = BroadcastChannel<Psychomatrix>(broadcastChannelCapacity)

    override suspend fun openPsychomatrixCreatedSubscription(): ReceiveChannel<Psychomatrix> {
        return psychomatrixCreateBroadcastChannel.openSubscription()
    }

    override fun userPickDate(date: Long) {
        userPickDate(DateTime(date))
    }

    override fun userPickDate(date: DateTime) {
        scope.launch {
            CalculatePsychomatrixByDateUseCase.calculate(date).await().also {
                psychomatrixCreateBroadcastChannel.send(it)
            }
        }
    }
}
