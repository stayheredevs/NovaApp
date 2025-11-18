package com.zeroone.novaapp.utilities

import android.content.Context
import android.widget.Toast

class AppUtils {


    companion object{

        var isDebug: Boolean = true

        fun ToastMessage(msg: String?, ctx: Context?) {
            if (AppUtils.isDebug) {
                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }


}