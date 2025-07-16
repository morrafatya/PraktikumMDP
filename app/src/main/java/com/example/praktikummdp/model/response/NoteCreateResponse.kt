package com.example.praktikummdp.model.response

data class NoteCreateResponse(
    val code: Int,
    val message: String,
    val data : NoteItemCreate

)
data class NoteItemCreate(
    val id_notes: String,
    val tittle: String,
    val content: String
)