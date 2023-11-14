package com.CH2PS073.diabetless.ui.main.ui.glycemic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.CH2PS073.diabetless.R
import com.CH2PS073.diabetless.databinding.FragmentGlycemicIndexBinding
import com.CH2PS073.diabetless.ui.main.bottomSheetMenu.BottomSheetMenuFragment

class GlycemicIndexFragment : Fragment() {

    private var _binding: FragmentGlycemicIndexBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val glycemicViewModel =
            ViewModelProvider(this).get(GlycemicViewModel::class.java)

        _binding = FragmentGlycemicIndexBinding.inflate(inflater, container, false)
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

        val textView: TextView = binding.textDashboard
        glycemicViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }
}