package com.github.insanusmokrassar.PsychomatrixBase.data.repository.realisations

import com.github.insanusmokrassar.PsychomatrixBase.data.repository.PsychomatrixCeilDescriptionRepository
import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CeilDescriptionUseCase
import com.github.insanusmokrassar.PsychomatrixBase.utils.extensions.subscribe

abstract class PsychomatrixCeilDescriptionRepositoryImpl(
    ceilDescriptionUseCase: CeilDescriptionUseCase
) : PsychomatrixCeilDescriptionRepository {
    init {
        ceilDescriptionUseCase.openCeilDescriptionRequestedSubscription().subscribe {
            getCeilDescription(it).also {
                description ->
                ceilDescriptionUseCase.descriptionReady(
                    it, description
                )
            }
        }
    }
}