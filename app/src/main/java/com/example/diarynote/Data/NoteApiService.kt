package com.example.diarynote.Data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("8SE5B/Note")
    fun getAllNotes(): Call<List<Note>>

    @GET("8SE5B/Note/{id}")
    fun getNoteById(@Path("id") id: String): Call<Note>

    @POST("8SE5B/Note")
    fun createNote(@Body note: Note): Call<Note>

    @DELETE("8SE5B/Note/{id}")
    fun deleteNote(@Path("id") id: String): Call<Void>
}
