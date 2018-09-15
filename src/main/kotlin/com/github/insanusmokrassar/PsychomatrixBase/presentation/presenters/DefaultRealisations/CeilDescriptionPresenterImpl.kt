package com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.DefaultRealisations

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CeilDescriptionUseCase
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilInfo
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilState
import com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.CeilDescriptionPresenter
import com.github.insanusmokrassar.PsychomatrixBase.utils.Container
import com.github.insanusmokrassar.PsychomatrixBase.utils.extensions.subscribeChecking
import kotlinx.coroutines.experimental.*

class CeilDescriptionPresenterImpl(
    private val ceilDescriptionUseCase: CeilDescriptionUseCase
) : CeilDescriptionPresenter {
    override fun onUserChooseCeil(ceilState: CeilState): Deferred<CeilInfo> {
        return async {
            val container = Container<CeilInfo>()
            val subscription = ceilDescriptionUseCase.openCeilDescriptionReadySubscription().subscribeChecking(
                {
                    container.throwable = it
                    false
                }
            ) {
                if (it.first == ceilState) {
                    container.value = it.second
                    false
                } else {
                    true
                }
            }

            ceilDescriptionUseCase.requestDescription(ceilState)

            subscription.join()
            container.value ?: throw container.throwable ?: throw IllegalStateException("Strange state - must be set value or error")
        }
    }
}