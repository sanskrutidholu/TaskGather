package com.example.taskgather.models

class UserModel (
    var userId: String,
    var userName: String,
    var userEmail: String,
    var totalNotes:Long,
    var totalImages:Long ) {
    constructor() : this("","","",0,0)
}