package com.github.insanusmokrassar.PsychomatrixBase.di

import com.github.insanusmokrassar.PsychomatrixBase.domain.UseCases.CalculatePsychomatrixByDate

interface UseCasesDI : EntitiesDI {
    val calculatePsychomatrixByDate: CalculatePsychomatrixByDate
}