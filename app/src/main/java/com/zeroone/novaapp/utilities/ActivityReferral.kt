package com.zeroone.novaapp.utilities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zeroone.novaapp.R
import com.zeroone.novaapp.databinding.ActivityReferBinding
import com.zeroone.novaapp.utils.EdgeToEdgeManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityReferral: AppCompatActivity() {

    lateinit var binding: ActivityReferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //event listeners
        setListeners()

        // Enable edge-to-edge for Android 15+ devices
        EdgeToEdgeManager.handleEdgeToEdge(window, binding.root, R.color.primary_color)
    }

    fun setListeners(){
        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.txtCopyCode.setOnClickListener {
            copyReferralCode(binding.txtReferralCode.text.toString())
        }

        binding.llShareApp.setOnClickListener {
            shareApp()
        }
    }

    fun copyReferralCode(referralCode: String?) {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", referralCode)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(this, "Referral code copied", Toast.LENGTH_LONG).show()
    }

    fun shareApp() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "NovaApp")

        val app_url = "Install NovaApp and gain access to a platform that exposes you to professionals that offers world class cleaning service. \n https://play.google.com/store/apps/details?id=my.example.javatpoint"
        shareIntent.putExtra(Intent.EXTRA_TEXT, app_url)
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }
}