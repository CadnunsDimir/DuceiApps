package io.github.cadnunsdimir.android.duceiapp.service

import io.github.cadnunsdimir.android.duceiapp.MainActivity

data class Entry(
    val key: String,
    val map: Map<String, String>
)
fun TranslateFunc(key: String) : String{
    val texts = mutableListOf(
        Entry("field_text_less_than_4", translations("Texto deve ter pelo menos 4 letras", "Texto debe tener por lo menos 4 letras", "Text should have at least 4 letters"))
    )
    val languageId = getLanguage()
    val filterText = texts.filter { it.key == key }
    if(filterText.isEmpty()) return key
    return filterText.first().map[languageId] ?: key;
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
