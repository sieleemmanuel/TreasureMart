package com.sielehub.treasuremart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sielehub.treasuremart.data.remote.PostServiceImp
import com.sielehub.treasuremart.data.remote.dto.PostResponse
import com.sielehub.treasuremart.presentation.ui.theme.KtorClientTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val postService: PostServiceImp by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            KtorClientTheme {
                val posts = produceState<List<PostResponse>>(
                    initialValue = emptyList(),
                    producer = {
                       value = postService.getPosts()
                    }
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (posts.value.isEmpty()){
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }else Posts(posts = posts.value)
                }
            }
        }
    }
}

@Composable
fun Posts(modifier: Modifier = Modifier, posts: List<PostResponse>) {
    LazyColumn(modifier = modifier
        .fillMaxSize()
        .padding(PaddingValues(horizontal = 6.dp)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
        items(posts){ post ->
            Column(modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(16.dp),
                    color = Color.LightGray
                )
                .padding(16.dp)
            ) {
                Text(
                    text = post.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = post.content,
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KtorClientTheme {
        //Posts("Android")
    }
}