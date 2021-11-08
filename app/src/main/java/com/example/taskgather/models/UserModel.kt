package com.example.taskgather.models

class UserModel (
    var userId: String,
    var userName: String,
    var userEmail: String,
    var totalNotes:Int,
    var totalImages:Int ) {
    constructor() : this("","","",0,0)
}