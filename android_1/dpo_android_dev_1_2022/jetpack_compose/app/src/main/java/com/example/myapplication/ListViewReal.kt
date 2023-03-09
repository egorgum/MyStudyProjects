package com.example.myapplication

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.myapplication.first.ui.Characters
import com.example.myapplication.first.ui.ListView


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListViewReal(items: LazyPagingItems<Characters>, navController: NavController){

    LazyColumn(){
        items(items){
            Log.d(TAG, "${it?.image}")
            Surface(
                modifier = Modifier.fillMaxSize(),
                onClick = {
                    navController.navigate(Routes.Person.route
                            + "/${it?.name}" + "/${it?.status}"
                            + "/${it?.locationInfo}" + "/${it?.species}"
                            + "/${it?.gender}" + "/${it?.origin}" + "/${it?.image}")
                    Log.d(TAG, "Клик: ${it?.image}")
                }) {
                it?.let {k ->
                    ListView(character = k) } ?: Text(text = "Ooops")
            }
        }
        items.apply {
            when{
                loadState.refresh is  LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            CircularProgressIndicator()
                        }
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item{ CircularProgressIndicator() }
                }
                loadState.refresh is LoadState.Error ->{
                    val e = items.loadState.refresh as LoadState.Error
                    item {
                        Column(modifier = Modifier.fillParentMaxSize()) {
                            e.error.localizedMessage?.let {Text(text = it)}
                            Button(onClick = { retry()}) {
                                Text(text = "Retry")
                            }
                        }
                    }
                }
                loadState.append is LoadState.Error ->{
                    val e = items.loadState.append as LoadState.Error
                    item {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            e.error.localizedMessage?.let {Text(text = it)}
                            Button(onClick = { retry()}) {
                                Text(text = "Retry")
                            }
                        }
                    }
                }
            }
        }
    }
}