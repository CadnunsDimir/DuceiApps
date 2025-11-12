package io.github.cadnunsdimir.android.duceiapp.service

import io.github.cadnunsdimir.android.duceiapp.MainActivity

data class Entry(
    val key: String,
    val map: Map<String, String>
)
fun translate(key: String) : String {
    val keyAndValues = key.split("|")
    val realKey = keyAndValues[0]
    val languageId = getLanguage()
    val filterText = texts.filter { it.key == realKey }
    if(filterText.isEmpty()) return realKey
    var text = filterText.first().map[languageId];
    if (text != null) {
        for (i in 1 until keyAndValues.size){
            val valueToSearch = "$$i"
            text = text?.replace(valueToSearch, keyAndValues[i])
        }
        return text?:realKey;
    }
    return realKey;
}

fun getLanguage(): String {
    val supportedLanguages = listOf("pt", "es", "en")
    if (supportedLanguages.contains(MainActivity.languageId)) {
        return MainActivity.languageId
    }
    return supportedLanguages[0]
}

fun translations(
    pt: String,
    es: String,
    en: String
): kotlin.collections.Map<String, String> {
    return hashMapOf(
        "pt" to pt,
        "es" to es,
        "en" to en)
}

val texts = mutableListOf(
    Entry("field_text_less_than_4", translations("deve ter pelo menos 4 letras", "Texto debe tener por lo menos 4 letras", "Text should have at least 4 letters")),
    Entry("field_text_min_value", translations("valor deve ser maior ou igual a $1","valor debe ser mas grande o igual que $1","value should be greater or equal than $1")),
    Entry("field_text_max_value", translations("valor deve ser menor ou igual a $1","valor debe ser menor o igual que $1","value should be smaller or equal than $1")),
    Entry("field_text_cellphone_length", translations("telefone deve conter 11 caracteres","el teléfono debe contener 11 caracteres","phone number should have 11 chars")),
    Entry("app_logo_description", translations("Dulceí Logo", "Dulceí Logo", "Dulceí Logo")),
    Entry("home_hello", translations("Olá, $1", "Holla, $1", "Hello, $1")),
    Entry("home_text", translations("Qual vai será sua opção de hoje?", "¿Cuál será tu elección hoy?", "What will your choice be today?"))
)
