package com.github.insanusmokrassar.PsychomatrixBase.data.repository.realisations

import com.github.insanusmokrassar.PsychomatrixBase.data.repository.PsychomatrixCeilDescriptionRepository
import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CeilDescriptionUseCase
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.CeilDescription
import com.github.insanusmokrassar.PsychomatrixBase.domain.entities.PsychomatrixCeilInfo
import com.github.insanusmokrassar.PsychomatrixBase.utils.CeilDescriptions.models.CeilsDescriptionsRoot
import com.github.insanusmokrassar.PsychomatrixBase.utils.CeilDescriptions.resolveCeilsDescriptionsByLanguage
import com.github.insanusmokrassar.PsychomatrixBase.utils.extensions.subscribe

const val CEILS_DESCRIPTION_ENGLISH="en_US"

class PsychomatrixCeilDescriptionRepositoryImpl(
    ceilDescriptionUseCase: CeilDescriptionUseCase,
    language: String = CEILS_DESCRIPTION_ENGLISH
) : PsychomatrixCeilDescriptionRepository {

    private val ceilsDescriptionsRoot: CeilsDescriptionsRoot = resolveCeilsDescriptionsByLanguage(language)

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

    override fun getCeilDescription(psychomatrixCeilInfo: PsychomatrixCeilInfo): CeilDescription {
        return ceilsDescriptionsRoot.resolveCeilDescription(psychomatrixCeilInfo)
    }
}