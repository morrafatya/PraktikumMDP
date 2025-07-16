package com.example.praktikummdp.service.api


import com.example.praktikummdp.model.request.LoginRequest
import com.example.praktikummdp.model.request.NoteCreateRequest
import com.example.praktikummdp.model.request.RegisterRequest
import com.example.praktikummdp.model.response.LoginResponse
import com.example.praktikummdp.model.response.NoteCreateResponse
import com.example.praktikummdp.model.response.NotesResponse
import com.example.praktikummdp.model.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiService{
    @POST("api/register")
    suspend fun  register (@Body request : RegisterRequest) : Response<RegisterResponse>

    @POST("/api/login")
    suspend fun login (@Body request : LoginRequest) : Response<LoginResponse>

    @GET("/api/notes")
    suspend fun  getAllNotes(): Response<NotesResponse>

    @POST("/api/notes")
    suspend fun createNotes (
        @Header ("Authorization") token : String,
        @Body request: NoteCreateRequest
    ): Response<NoteCreateResponse>
}