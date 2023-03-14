package com.example.recycler_new.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recycler_new.models.Result
import com.example.recycler_new.repository.Repository

class RickAndMortyPagingSource:PagingSource<Int, com.example.recycler_new.models.Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key?: 1
        return kotlin.runCatching {
            Repository().getData(page)
        }.fold(
            onSuccess = {
                Log.d(TAG, "Success")
                LoadResult.Page(
                    data = it.results,
                    prevKey = null,
                    nextKey = if (it.results.isEmpty()) null else page+1
                )
            },
            onFailure = {
                LoadResult.Error(it)}
        )
    }

}