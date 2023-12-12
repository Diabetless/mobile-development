package com.ch2Ps073.diabetless.ui.main.bottomSheetMenu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ch2Ps073.diabetless.databinding.FragmentBottomSheetMenuBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.main.MainViewModel
import com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile.ProfileSettingActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetMenuFragment : BottomSheetDialogFragment() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this.requireActivity())
    }

    private var _binding: FragmentBottomSheetMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentBottomSheetMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.profileButton.setOnClickListener {
            val intent = Intent(this.requireActivity(), ProfileSettingActivity::class.java)
            startActivity(intent)
        }

        binding.notificationSettingBbutton.setOnClickListener {
            showToast("Fitur notification belum tersedia")
        }

        binding.helpCenterButton.setOnClickListener {
            showToast("Fitur help center belum tersedia")
        }

        binding.aboutUsButton.setOnClickListener {
            showToast("Fitur about us belum tersedia")
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }

        return root
    }

    private fun showToast(message: String) {
        Toast.makeText(this.requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

}