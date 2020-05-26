package com.moriarity_code.practiceapp2.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.moriarity_code.practiceapp2.R

class ProfileFragment : Fragment() {
    lateinit var name: TextView
    lateinit var emailId: TextView
    lateinit var mobileNumber: TextView

    var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        name = view.findViewById(R.id.txtName)
        emailId = view.findViewById(R.id.txtEmailId)
        mobileNumber = view.findViewById(R.id.txtMobileNumber)

        sharedPreferences = context?.getSharedPreferences(
            getString(R.string.preference_file_name),
            Context.MODE_PRIVATE
        )

        name.text = sharedPreferences?.getString("Name", null)
        emailId.text = sharedPreferences?.getString("Mobile Number", null)
        mobileNumber.text = sharedPreferences?.getString("Email Id", null)


        return view
    }

}
