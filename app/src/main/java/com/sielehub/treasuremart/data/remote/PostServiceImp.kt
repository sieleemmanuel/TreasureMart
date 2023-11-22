package com.sielehub.treasuremart.data.remote

import android.util.Log
import com.sielehub.treasuremart.data.remote.dto.PostRequest
import com.sielehub.treasuremart.data.remote.dto.PostResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PostServiceImp(private val client: HttpClient): PostService {
    override suspend fun getPosts(): List<PostResponse> {
        return try {
            val response = client.get (HttpRoutes.POST_ENDPOINT).body<List<PostResponse>>()
            Log.d("PostServiceImp", "getPosts: $response")
            response
        } catch (e: RedirectResponseException){
            // 3xx errors
            Log.d("PostServiceImp", "Error: ${e.response.status.description}")
            emptyList()
        }catch (e: ClientRequestException){
            // 4xx errors invalid data
            Log.d("PostServiceImp", "Error: ${e.response.status.description}")
            emptyList()
        }catch (e: ServerResponseException){
            // 5xx error from server
            Log.d("PostServiceImp", "Error: ${e.response.status.description}")
            emptyList()
        }catch (e: Exception){
            // general error - no internet connection
            Log.d("PostServiceImp", "Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun createPost(postRequest: PostRequest): PostResponse? {
        return try {
            client.post(HttpRoutes.POST_ENDPOINT){
                contentType(ContentType.Application.Json)
                setBody(postRequest)
            }.body<PostResponse>()
        } catch (e: RedirectResponseException){
            // 3xx errors
            Log.d("PostServiceImp", "Error: ${e.response.status.description}")
           null
        }catch (e: ClientRequestException){
            // 4xx errors invalid data
            Log.d("PostServiceImp", "Error: ${e.response.status.description}")
           null
        }catch (e: ServerResponseException){
            // 5xx error from server
            Log.d("PostServiceImp", "Error: ${e.response.status.description}")
            null
        }catch (e: Exception){
            // general error - no internet connection
            Log.d("PostServiceImp", "Error: ${e.message}")
            null
        }
    }
}