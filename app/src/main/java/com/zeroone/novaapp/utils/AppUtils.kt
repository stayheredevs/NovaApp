package com.zeroone.novaapp.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import okhttp3.Request
import okio.Buffer
import java.io.IOException
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

        fun showSnackBar(view: View, message: String, action: String = "", actionListener: View.OnClickListener? = null) {
            val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            snackBar.show()

        }

        fun formatPhoneNumber(country_code: String, phone_number: String): String {
            var phone_number = phone_number
            var formatted_number = ""
            if (phone_number.startsWith("+")) {
                phone_number = phone_number.replace("+", "")

                if (phone_number.startsWith("0")) {
                    formatted_number = country_code + phone_number.substring(1)
                } else {
                    formatted_number = country_code + phone_number
                }
            } else {
                if (phone_number.startsWith("0")) {
                    formatted_number = country_code + phone_number.substring(1)
                } else {
                    formatted_number = country_code + phone_number
                }
            }

            AppLog.Log("formatted_phone_number", formatted_number)

            return formatted_number.trim { it <= ' ' }.replace(" ", "")
        }

        fun bodyToStringConverter(request: Request): String {
            try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()
                copy.body!!.writeTo(buffer)
                return buffer.readUtf8()
            } catch (e: IOException) {
                return "did not work"
            }
        }

    }


}