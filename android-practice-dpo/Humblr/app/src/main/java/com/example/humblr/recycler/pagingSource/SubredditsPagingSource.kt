package com.example.humblr.recycler.pagingSource

import android.content.ContentValues
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.humblr.data.modelSubList.Children
import com.example.humblr.repository.RetrofitRepository
import com.example.humblr.states.PostsStates
import javax.inject.Inject

class SubredditsPagingSource@Inject
constructor(
    private val token: String,
    //Получаемая категория постов
    private val state: PostsStates,
    //Параметр для поиска
    private val param1: String? = null
): PagingSource<String, Children>() {
    override fun getRefreshKey(state: PagingState<String, Children>): String = FIRST_PAGE

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Children> {
        val page = params.key?: FIRST_PAGE
        return kotlin.runCatching {
            //Сделать запрос по определенной категории
            when(state){
                PostsStates.Search -> RetrofitRepository().getSubsSearch(page = page, token = token, q = param1!!)
                PostsStates.New -> RetrofitRepository().getSubsNew(token = token, page = page)
                PostsStates.Popular -> RetrofitRepository().getSubsPopular(token = token, page = page)
            }
        }.fold(
            onSuccess = {
                Log.d(ContentValues.TAG, "Success")
                LoadResult.Page(
                    data = it.data.children,
                    prevKey = null,
                    nextKey = if (it.data.children.isEmpty()) null else it.data.after?: FIRST_PAGE
                )
            },
            onFailure = {
                Log.d(ContentValues.TAG, "Ошибка: $it")
                Throwable(it)
                LoadResult.Error(it)}
        )
    }
    companion object{
        private const val FIRST_PAGE = ""
    }

}