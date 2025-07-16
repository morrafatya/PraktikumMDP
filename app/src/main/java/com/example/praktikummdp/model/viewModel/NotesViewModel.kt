package com.example.praktikummdp.model.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.praktikummdp.model.response.NoteItem
import com.example.praktikummdp.service.api.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel : ViewModel() {

    private  val  _notes = MutableStateFlow<List<NoteItem>>(emptyList())

    val notes : StateFlow<List<NoteItem>> = _notes

    private fun getALLNotes(){
        viewModelScope.launch {
            try{
                val response = ApiClient.instance.getAllNotes()
                _notes.value = response.body()?.data?.notes!!
            } catch(e: Exception){
                Log.e("getNotesError","Gagal Mengambil Data Catatan")
            }
        }
    }
    init{
        getALLNotes()
    }
}
