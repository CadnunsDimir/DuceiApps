package io.github.cadnunsdimir.android.duceiapp.models

class Validator {
    private var value: String
    private var field: String
    private var errorList: MutableList<ValidationError> = mutableListOf()

    constructor(field: String, value: String){
        this.field = field
        this.value = value
    }

    fun build(): List<ValidationError> {
        return errorList;
    }

    fun minLen(): Validator {
        if (value.length < 4) {
            errorList.add(ValidationError(field , "field_text_less_than_4"))
        }
        return this
    }

    fun minValue(limit: Float) : Validator{
        replaceNonNumbers()
        if (!value.isEmpty()) {
            val parsedValue = value.toFloat()
            if (parsedValue < limit){
                errorList.add(ValidationError(field , "field_text_min_value|$limit"))
            }
        }
        return this
    }

    fun maxValue(limit: Float) : Validator{
        replaceNonNumbers()
        if (!value.isEmpty()) {
            val parsedValue = value.toFloat()
            if (parsedValue >= limit){
                errorList.add(ValidationError(field , "field_text_max_value|$limit"))
            }
        }
        return this
    }

    fun replaceNonNumbers() {
        value = value.replace(regex = "\\D".toRegex(), replacement = "")
    }

    fun cellphoneBrLength()  : Validator{
        replaceNonNumbers()
        if (value.length != 11){
            errorList.add(ValidationError(field , "field_text_cellphone_length"))
        }
        return this
    }
}