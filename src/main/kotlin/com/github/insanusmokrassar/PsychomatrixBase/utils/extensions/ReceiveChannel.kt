package com.github.insanusmokrassar.PsychomatrixBase.utils.extensions

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import java.util.concurrent.TimeUnit

const val SUBSCRIPTIONS_LARGE = 256
const val SUBSCRIPTIONS_MEDIUM = 128
const val SUBSCRIPTIONS_SMALL = 64
const val SUBSCRIPTIONS_EXTRA_SMALL = 32
const val SUBSCRIPTIONS_SINGLE = 1

fun <T> ReceiveChannel<T>.subscribeChecking(
    throwableHandler: (Throwable) -> Boolean = {
        it.printStackTrace()
        true
    },
    by: suspend (T) -> Boolean
): Job {
    return launch {
        while (isActive && !isClosedForReceive) {
            try {
                val received = receive()

                launch {
                    try {
                        if (!by(received)) {
                            cancel()
                        }
                    } catch (e: Throwable) {
                        if (!throwableHandler(e)) {
                            cancel()
                        }
                    }
                }
            } catch (e: CancellationException) {
                break
            }
        }
        cancel()
    }
}

fun <T> ReceiveChannel<T>.subscribe(
    throwableHandler: (Throwable) -> Boolean = {
        it.printStackTrace()
        true
    },
    by: suspend (T) -> Unit
): Job {
    return subscribeChecking(throwableHandler) {
        by(it)
        true
    }
}

fun <T> ReceiveChannel<T>.debounce(delayMs: Long, awaitedSubscriptions: Int = 256): BroadcastChannel<T> {
    val channel = BroadcastChannel<T>(awaitedSubscriptions)
    var lastReceived: Pair<Long, T>? = null
    var job: Job? = null
    launch {
        while (isActive && !isClosedForReceive) {
            val received = receive()

            lastReceived = Pair(System.currentTimeMillis() + delayMs, received)

            job ?:let {
                job = launch {
                    try {
                        var now = System.currentTimeMillis()
                        while (isActive && lastReceived?.first ?: now >= now) {
                            delay((lastReceived ?.first ?: now) - now, TimeUnit.MILLISECONDS)
                            now = System.currentTimeMillis()
                        }

                        lastReceived?.second?.also {
                            channel.send(it)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        job = null
                    }
                }
            }
        }
        cancel()
    }
    return channel
}
