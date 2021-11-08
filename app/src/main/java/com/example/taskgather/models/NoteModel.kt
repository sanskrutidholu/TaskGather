package com.example.taskgather.models

class NoteModel(
    var userId: String,
    var noteId: String,
    var noteTitle: String,
    var noteDesc: String,
    var noteDate: Long
        ) {
    constructor() : this("","","","",0)
}