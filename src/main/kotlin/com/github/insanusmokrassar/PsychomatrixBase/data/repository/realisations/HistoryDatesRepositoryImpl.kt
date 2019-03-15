package com.github.insanusmokrassar.PsychomatrixBase.data.repository.realisations

import com.github.insanusmokrassar.PsychomatrixBase.data.repository.HistoryDatesRepository
import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CalculatePsychomatrixByDateUseCase
import com.github.insanusmokrassar.PsychomatrixBase.utils.extensions.subscribe
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import org.joda.time.DateTime

abstract class HistoryDatesRepositoryImpl(
    calculatePsychomatrixByDateUseCase: CalculatePsychomatrixByDateUseCase
) : HistoryDatesRepository {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val dateAddedBroadcast = BroadcastChannel<DateTime>(8)
    private val dateRemovedBroadcast = BroadcastChannel<DateTime>(8)

    init {
        scope.launch {
            calculatePsychomatrixByDateUseCase.openPsychomatrixCreatedSubscription().subscribe {
                it.date.also {
                    date ->
                    date.withTime(0, 0, 0, 0).also {
                        dateWithoutTime ->
                        onDateCalculated(dateWithoutTime)
                        dateAddedBroadcast.send(dateWithoutTime)
                    }
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
        return scope.async {
            internalRemoveDate(date).also {
                if (it) {
                    dateRemovedBroadcast.send(date)
                }
            }
        }
    }

    protected abstract fun internalRemoveDate(date: DateTime): Boolean
}