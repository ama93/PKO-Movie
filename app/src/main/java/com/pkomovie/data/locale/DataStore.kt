package com.pkomovie.data.locale

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_storage")


class DataStore @Inject constructor(@ApplicationContext private val context: Context) {

    fun observeFavouritesChanged(): Flow<Set<String>?> =
        context.dataStore.data.map { preferences ->
            preferences[FAVOURITE_MOVIES]
        }.distinctUntilChanged()

    suspend fun addToFavourites(movieId: Int) {
        updateFavourites(movieId, true)
    }

    suspend fun removeFromFavourites(movieId: Int) {
        updateFavourites(movieId, false)
    }

    private suspend fun updateFavourites(movieId: Int, shouldAdd: Boolean) {
        context.dataStore.edit { preferences ->
            val favourites = preferences[FAVOURITE_MOVIES]?.toMutableSet() ?: mutableSetOf()
            if (shouldAdd) {
                favourites.add(movieId.toString())
            } else {
                favourites.remove(movieId.toString())
            }
            preferences[FAVOURITE_MOVIES] = favourites
        }
    }

    companion object {
        private val FAVOURITE_MOVIES = stringSetPreferencesKey("favourite_movies")
    }

}