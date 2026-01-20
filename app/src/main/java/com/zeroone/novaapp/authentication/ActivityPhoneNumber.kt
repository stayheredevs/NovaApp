package com.zeroone.novaapp.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import com.zeroone.novaapp.R
import com.zeroone.novaapp.databinding.ActivityPhoneNumberBinding
import com.zeroone.novaapp.home.AdminLandingPage
import com.zeroone.novaapp.utils.AppLog
import com.zeroone.novaapp.utils.AppUtils
import com.zeroone.novaapp.utils.EdgeToEdgeManager
import com.zeroone.novaapp.utils.SharedPreference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ActivityPhoneNumber: AppCompatActivity() {

    @Inject
    lateinit var sharedPreference: SharedPreference

    lateinit var binding: ActivityPhoneNumberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set listeners
        setListeners()

        // Enable edge-to-edge for Android 15+ devices
        EdgeToEdgeManager.handleEdgeToEdge(window, binding.root, R.color.primary_color)


    }

    fun setListeners(){

        binding.edtPhoneNumber.doOnTextChanged {
            text, start, before, count ->

            binding.btnContinue.isEnabled = text?.length == 10

        }


        binding.btnContinue.setOnClickListener {

            AppLog.Log("login_", "Got here 1")


            if (binding.edtPhoneNumber.text.toString().isEmpty()){
                //AppUtils.showSnackBar(binding.root, "Please enter a valid phone number")
                binding.txtError.visibility = View.VISIBLE
                binding.txtError.text = R.string.invalid_phone_number.toString()

                AppUtils.ToastMessage(R.string.invalid_phone_number.toString(), this)


                AppLog.Log("login_", "Got here 2")

            } else {
                val intent = Intent(this, ActivityPhoneNumberVerification::class.java)

                val phoneNumber = AppUtils.formatPhoneNumber(binding.txtCountryCode.text.toString(), binding.edtPhoneNumber.text.toString())
                intent.putExtra("phone_number", phoneNumber)

                startActivity(intent)
                finish()

                AppLog.Log("login_", "Got here 3")
            }
        }
    }


}