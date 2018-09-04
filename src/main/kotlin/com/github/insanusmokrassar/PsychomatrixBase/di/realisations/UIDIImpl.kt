package com.github.insanusmokrassar.PsychomatrixBase.di.realisations

import com.github.insanusmokrassar.PsychomatrixBase.di.PresentationLayerDI
import com.github.insanusmokrassar.PsychomatrixBase.di.UIDI

open class UIDIImpl(
    presentationLayerDI: PresentationLayerDI
) : UIDI, PresentationLayerDI by presentationLayerDI {

}
