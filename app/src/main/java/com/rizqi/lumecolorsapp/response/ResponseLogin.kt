package com.rizqi.lumecolorsapp.response

import java.io.Serializable
import com.rizqi.lumecolorsapp.model.MUser

class ResponseLogin(

    var username : String,
    var password : String,
    var name : String,
    var height : String

): Serializable


