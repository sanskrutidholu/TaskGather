package com.example.taskgather.models

class ImageModel(
    var userId:String,
    var ImageId: String,
    var ImageTitle: String,
    var ImageUrl: String,
    var ImageDate: Long)
{
    constructor() : this("","","","",0)
}