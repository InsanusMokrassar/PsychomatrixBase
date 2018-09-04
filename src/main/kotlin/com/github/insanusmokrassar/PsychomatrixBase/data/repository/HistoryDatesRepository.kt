package com.github.insanusmokrassar.PsychomatrixBase.data.repository

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import org.joda.time.DateTime

interface HistoryDatesRepository {

    suspend fun openDateAddedSubscription(): ReceiveChannel<DateTime>
    suspend fun openDateRemovedSubscription(): ReceiveChannel<DateTime>

    suspend fun getDates(from: DateTime? = null, to: DateTime? = null): Deferred<List<DateTime>>
    suspend fun removeDate(date: DateTime): Deferred<Boolean>

}