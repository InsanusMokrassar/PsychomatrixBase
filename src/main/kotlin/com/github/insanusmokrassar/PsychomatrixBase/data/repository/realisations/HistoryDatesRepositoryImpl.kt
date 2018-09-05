package com.github.insanusmokrassar.PsychomatrixBase.data.repository.realisations

import com.github.insanusmokrassar.PsychomatrixBase.data.repository.HistoryDatesRepository
import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CalculatePsychomatrixByDateUseCase
import com.github.insanusmokrassar.PsychomatrixBase.utils.extensions.SUBSCRIPTIONS_EXTRA_SMALL
import com.github.insanusmokrassar.PsychomatrixBase.utils.extensions.subscribe
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import org.joda.time.DateTime

abstract class HistoryDatesRepositoryImpl(
    calculatePsychomatrixByDateUseCase: CalculatePsychomatrixByDateUseCase
) : HistoryDatesRepository {
    private val dateAddedBroadcast = BroadcastChannel<DateTime>(SUBSCRIPTIONS_EXTRA_SMALL)
    private val dateRemovedBroadcast = BroadcastChannel<DateTime>(SUBSCRIPTIONS_EXTRA_SMALL)

    init {
        launch {
            calculatePsychomatrixByDateUseCase.openPsychomatrixCreatedSubscription().subscribe {
                it.date.also {
                    date ->
                    onDateCalculated(date)
                    dateAddedBroadcast.send(date)
                }
            }
        }
    }

    override suspend fun openDateAddedSubscription(): ReceiveChannel<DateTime> {
        return dateAddedBroadcast.openSubscription()
    }

    override suspend fun openDateRemovedSubscription(): ReceiveChannel<DateTime> {
        return dateRemovedBroadcast.openSubscription()
    }

    protected abstract fun onDateCalculated(dateTime: DateTime)

    override suspend fun removeDate(date: DateTime): Deferred<Boolean> {
        return async {
            internalRemoveDate(date).also {
                if (it) {
                    dateRemovedBroadcast.send(date)
                }
            }
        }
    }

    protected abstract fun internalRemoveDate(date: DateTime): Boolean
}