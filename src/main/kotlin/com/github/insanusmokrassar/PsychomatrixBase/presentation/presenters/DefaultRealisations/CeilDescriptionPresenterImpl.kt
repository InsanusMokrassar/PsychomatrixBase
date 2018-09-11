package com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.DefaultRealisations

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CeilDescriptionUseCase
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilDescription
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.PsychomatrixCeilInfo
import com.github.insanusmokrassar.PsychomatrixBase.presentation.presenters.CeilDescriptionPresenter
import com.github.insanusmokrassar.PsychomatrixBase.utils.Container
import com.github.insanusmokrassar.PsychomatrixBase.utils.extensions.subscribeChecking
import kotlinx.coroutines.experimental.*

class CeilDescriptionPresenterImpl(
    private val ceilDescriptionUseCase: CeilDescriptionUseCase
) : CeilDescriptionPresenter {
    override fun onUserChooseCeil(ceilInfo: PsychomatrixCeilInfo): Deferred<CeilDescription> {
        return async {
            val container = Container<CeilDescription>()
            val subscription = ceilDescriptionUseCase.openCeilDescriptionReadySubscription().subscribeChecking(
                {
                    container.throwable = it
                    false
                }
            ) {
                if (it.first == ceilInfo) {
                    container.value = it.second
                    false
                } else {
                    true
                }
            }

            ceilDescriptionUseCase.requestDescription(ceilInfo)

            subscription.join()
            container.value ?: throw container.throwable ?: throw IllegalStateException("Strange state - must be set value or error")
        }
    }
}