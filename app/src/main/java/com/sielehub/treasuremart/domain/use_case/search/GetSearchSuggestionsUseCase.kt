package com.sielehub.treasuremart.domain.use_case.search

import com.sielehub.treasuremart.core.Resource
import com.sielehub.treasuremart.data.repository.StoreRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSearchSuggestionsUseCase(private val storeRepositoryImpl: StoreRepositoryImpl) {
    operator fun invoke(query: String): Flow<Resource<List<String>>> = flow {
        try {
            val allProducts = storeRepositoryImpl.getProducts()
            val suggestions = mutableListOf<String>()
            val formattedQuery = query.trim().lowercase()
            allProducts.forEach {
                suggestions.addAll(getPhrasesFromProducts(it.title))
                suggestions.addAll(getPhrasesFromProducts(it.description))
            }
            val sortedSuggestions = suggestions
                .filter {
                    it.lowercase().startsWith(formattedQuery) && it.lowercase() != formattedQuery
                }
                .sortedBy { it.length }
                .take(10)
            emit(Resource.Success(sortedSuggestions.distinct()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error occurred during search"))
        }
    }

    fun getPhrasesFromProducts(textContent: String): List<String> {
        val formattedText = textContent.lowercase().replace(Regex("[^a-z0-9\\s]"), "")
        val words = formattedText.split(Regex("\\s+")).filter { it.length > 2 }
        val phrases = mutableListOf<String>()

        phrases.addAll(words.filter { it.length > 3 })

        for (i in words.indices) {
            for (j in i + 1 until words.size) {
                phrases.add(words.subList(i, j + 1).joinToString(" "))
            }
        }
        return phrases.distinct()
    }
}