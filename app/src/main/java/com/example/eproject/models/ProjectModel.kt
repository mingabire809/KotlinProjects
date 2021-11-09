package com.example.eproject.models

import java.util.*

data class ProjectModel(
    val projectName: String?, val purpose: String, val startingDate: Date, val endingDate: Date, val funder: String, val image: Int,
    val id:Int) {

}