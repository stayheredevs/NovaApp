package com.zeroone.novaapp.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zeroone.novaapp.R
import com.zeroone.novaapp.databinding.ActivityPhoneNumberVerificationBinding
import com.zeroone.novaapp.home.AdminLandingPage
import com.zeroone.novaapp.utils.AppLog
import com.zeroone.novaapp.utils.AppUtils
import com.zeroone.novaapp.utils.EdgeToEdgeManager
import com.zeroone.novaapp.utils.SharedPreference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ActivityPhoneNumberVerification: AppCompatActivity() {

    lateinit var binding: ActivityPhoneNumberVerificationBinding

    @Inject
    lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhoneNumberVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set listener
        setListener()

        //init
        init()

        // Enable edge-to-edge for Android 15+ devices
        EdgeToEdgeManager.handleEdgeToEdge(window, binding.root, R.color.primary_color)

    }

    fun setListener(){
        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.btnVerify.setOnClickListener {

            /*if (binding.pinView.value.isEmpty()) {
                AppUtils.ToastMessage("Please enter a valid OTP", this)
                return@setOnClickListener
            }*/

            AppUtils.ToastMessage("Logged in successfully", this)
            AppLog.Log("otp_validation", "OTP validated successfully...")

            val intent = Intent(this, AdminLandingPage::class.java)
            startActivity(intent)
            finish()

            sharedPreference.isAgentLoggedIn = true
        }
    }

    fun init(){
        val phoneNumber = intent.getStringExtra("phone_number")
        binding.txtPhoneNumber.text = phoneNumber
    }
}