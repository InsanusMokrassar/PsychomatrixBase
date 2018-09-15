package com.github.insanusmokrassar.PsychomatrixBase.domain.entities

data class CeilState(
    val x: Int,
    val y: Int,
    val count: Int?
) {
    val ceilCoordsAsPair = x to y
    val isUpperDiag = ceilCoordsAsPair == UPPER_DIAG_MATRIX_INDEX
    val isDownDiag = ceilCoordsAsPair == DOWN_DIAG_MATRIX_INDEX

    val isFirstRowSum = ceilCoordsAsPair == FIRST_ROW_SUM_MATRIX_INDEX
    val isSecondRowSum = ceilCoordsAsPair == SECOND_ROW_SUM_MATRIX_INDEX
    val isThirdRowSum = ceilCoordsAsPair == THIRD_ROW_SUM_MATRIX_INDEX

    val isFirstColumnSum = ceilCoordsAsPair == FIRST_COLUMN_SUM_MATRIX_INDEX
    val isSecondColumnSum = ceilCoordsAsPair == SECOND_COLUMN_SUM_MATRIX_INDEX
    val isThirdColumnSum = ceilCoordsAsPair == THIRD_COLUMN_SUM_MATRIX_INDEX

    val isCalculated =
        ceilCoordsAsPair in EMPTY_CEILS_OF_MATRIX
        || isUpperDiag || isDownDiag
        || isFirstRowSum || isSecondRowSum || isThirdRowSum
        || isFirstColumnSum || isSecondColumnSum || isThirdColumnSum

    val numberOfCharacteristic: Byte? = if (
        isCalculated
    ) {
        null
    } else {
        (x * 3 + y).toByte()
    }
}
