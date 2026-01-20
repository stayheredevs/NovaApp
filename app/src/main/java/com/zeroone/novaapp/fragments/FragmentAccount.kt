package com.zeroone.novaapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.freshchat.consumer.sdk.Freshchat
import com.freshchat.consumer.sdk.exception.MethodNotAllowedException
import com.zeroone.novaapp.databinding.FragmentAccountBinding
import com.zeroone.novaapp.utilities.ActivityAbout
import com.zeroone.novaapp.utilities.ActivityManageNotifications
import com.zeroone.novaapp.utilities.ActivityPrivacyPolicy
import com.zeroone.novaapp.utilities.ActivityReferral
import com.zeroone.novaapp.utilities.ActivityTermsAndConditions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAccount: Fragment() {

    lateinit var binding: FragmentAccountBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAccountBinding.inflate(inflater, container, false)


        //event listeners
        setListeners()

        return binding.root
    }

    fun setListeners(){
        binding.layoutManageNotification.setOnClickListener {
            startActivity(Intent(requireContext(), ActivityManageNotifications::class.java))

        }

        binding.layoutContactSupport.setOnClickListener {
            Freshchat.showConversations(requireActivity())

            try {
                val freshchatUser = Freshchat.getInstance(requireActivity().applicationContext).user
                freshchatUser.firstName = "Josaya"
                freshchatUser.lastName = "Muriuki"
                freshchatUser.email = "josaya01@mail.com"
                freshchatUser.setPhone("+254", "254711304624")

                // Call setUser so that the user information is synced with Freshchat's servers
                Freshchat.getInstance(requireActivity().applicationContext).user = freshchatUser
            } catch (e: MethodNotAllowedException) {
                Log.d("exception", e.let { e.message.toString() })
            }

        }

        binding.layoutSendReferral.setOnClickListener {
            startActivity(Intent(requireContext(), ActivityReferral::class.java)
            )}

        binding.layoutAbout.setOnClickListener {
            startActivity(Intent(requireContext(), ActivityAbout::class.java)
            )}

        binding.layoutTermsAndConditions.setOnClickListener {
            startActivity(Intent(requireContext(), ActivityTermsAndConditions::class.java)
            )}

        binding.layoutPrivacyPolicy.setOnClickListener {
            startActivity(Intent(requireContext(), ActivityPrivacyPolicy::class.java)
            )}


    }
}