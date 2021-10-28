package com.rizqi.lumecolorsapp.response

import com.rizqi.lumecolorsapp.model.MUser
import java.io.Serializable

class ResponseLogin(

     val status : Int,
     val message : String,
     val data : ArrayList<MUser>,

): Serializable


