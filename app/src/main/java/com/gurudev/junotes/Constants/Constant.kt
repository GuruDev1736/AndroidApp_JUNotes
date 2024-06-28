package com.gurudev.junotes.Constants

import android.content.Context
import es.dmoral.toasty.Toasty

object Constant {

    fun success(context : Context , message : String)
    {
        Toasty.success(context,message,Toasty.LENGTH_LONG,true).show()
        return
    }

    fun error(context : Context , message : String)
    {
        Toasty.error(context,message,Toasty.LENGTH_LONG,true).show()
        return
    }

}