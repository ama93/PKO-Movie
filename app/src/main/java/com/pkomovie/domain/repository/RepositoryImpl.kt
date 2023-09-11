package com.pkomovie.domain.repository

import com.pkomovie.data.locale.DataStore
import com.pkomovie.data.mapper.toMovie
import com.pkomovie.data.remote.RemoteApi
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(val remoteApi: RemoteApi, val dataStore: DataStore) : Repository {

    override suspend fun getNowPlayingMovies() = remoteApi.getNowPlayingMovies().results.map {
        it.toMovie()
    }

    override suspend fun queryMovies(query: String) = remoteApi.queryMovies(query).results.map {
        it.toMovie()
    }

    override suspend fun saveToFavourites(movieId: Int) = dataStore.addToFavourites(movieId)

    override suspend fun removeFromFavourites(movieId: Int) = dataStore.removeFromFavourites(movieId)

    override suspend fun observeFavouritesChanged(): Flow<Set<String>?> = dataStore.observeFavouritesChanged()

}