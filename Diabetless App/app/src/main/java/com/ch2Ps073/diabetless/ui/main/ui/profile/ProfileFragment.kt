package com.ch2Ps073.diabetless.ui.main.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.data.remote.response.DetailUser
import com.ch2Ps073.diabetless.databinding.FragmentProfileBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.main.MainViewModel
import com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.BottomSheetMenuFragment
import com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile.ProfileSettingActivity
import com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile.ProfileSettingViewModel
import com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile.ProfileViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private val viewModel by viewModels<ProfileSettingViewModel> {
        ProfileViewModelFactory.getInstance(requireContext(), lifecycleScope)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity?)?.supportActionBar?.hide()

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu -> {
                    BottomSheetMenuFragment().show(childFragmentManager, "bottomSheetMenu")
                    true
                }

                else -> false
            }
        }

        mainViewModel.getSession().observe(requireActivity()) { user ->
            viewModel.getDetailUser(user.token)

            viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                if (isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
            viewModel.users.observe(viewLifecycleOwner) { detail ->
                lifecycleScope.launch {
                    setDetailUser(detail)
                }
            }
        }

        binding.editProfileButton.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileSettingActivity::class.java))
        }

        return root
    }

    private suspend fun setDetailUser(detail: DetailUser) {
        delay(1000)
        binding.nameEditTextLayout.text = detail.fullName
        binding.emailEditTextLayout.text = detail.email
        binding.usernameEditTextLayout.text = detail.username
        binding.birthdayEditTextLayout.text = detail.birthday
        if (detail.profilePicture == "") {
            Glide.with(this@ProfileFragment)
                .load("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png")
                .circleCrop()
                .into(binding.profileImage)
        } else {
            Glide.with(this@ProfileFragment)
                .load(detail.profilePicture.toString())
                .circleCrop()
                .into(binding.profileImage)
        }
    }
}