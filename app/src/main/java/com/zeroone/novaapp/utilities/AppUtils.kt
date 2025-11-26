package com.zeroone.novaapp.utilities

import android.content.Context
import android.widget.Toast
import java.text.DecimalFormat

class AppUtils {


    companion object{

        var isDebug: Boolean = true

        fun ToastMessage(msg: String?, ctx: Context?) {
            if (AppUtils.isDebug) {
                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
            }
        }


        fun numberFormatter(number: String?): String? {
            val formatter = DecimalFormat("#,###,###.00")
            return formatter.format(number?.toDouble())
        }
    }


}