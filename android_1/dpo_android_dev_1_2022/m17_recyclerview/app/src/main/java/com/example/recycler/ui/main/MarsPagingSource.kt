package com.example.recycler.ui.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recycler.models.Photo
import com.example.recycler.repo.Repository

class MarsPagingSource: PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: 1
        return kotlin.runCatching {
            Repository().getData(page, )
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it.photos,
                    prevKey = null,
                    nextKey = if (it.photos.isEmpty()) null else page+1
                )
            },
            onFailure = {LoadResult.Error(it)}
        )
    }
}