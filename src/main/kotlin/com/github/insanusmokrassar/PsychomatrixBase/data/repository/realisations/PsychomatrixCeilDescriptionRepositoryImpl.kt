package com.github.insanusmokrassar.PsychomatrixBase.data.repository.realisations

import com.github.insanusmokrassar.PsychomatrixBase.data.repository.PsychomatrixCeilDescriptionRepository
import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CeilDescriptionUseCase
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilInfo
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilState
import com.github.insanusmokrassar.PsychomatrixBase.utils.CeilDescriptions.models.CeilsInfosRoot
import com.github.insanusmokrassar.PsychomatrixBase.utils.CeilDescriptions.resolveCeilsDescriptionsByLanguage
import com.github.insanusmokrassar.PsychomatrixBase.utils.extensions.subscribe

const val CEILS_DESCRIPTION_ENGLISH="en_US"

class PsychomatrixCeilDescriptionRepositoryImpl(
    ceilDescriptionUseCase: CeilDescriptionUseCase,
    language: String = CEILS_DESCRIPTION_ENGLISH
) : PsychomatrixCeilDescriptionRepository {

    private val ceilsInfosRoot: CeilsInfosRoot = resolveCeilsDescriptionsByLanguage(language)

    init {
        ceilDescriptionUseCase.openCeilDescriptionRequestedSubscription().subscribe {
            getCeilDescription(it).also {
                description ->
                ceilDescriptionUseCase.descriptionReady(
                    it,
                    description
                )
            }
        }
    }

    override fun getCeilDescription(ceilState: CeilState): CeilInfo {
        return ceilsInfosRoot.resolveCeilDescription(ceilState)
    }
}