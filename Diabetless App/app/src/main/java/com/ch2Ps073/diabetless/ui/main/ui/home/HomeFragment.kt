package com.ch2Ps073.diabetless.ui.main.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.databinding.FragmentHomeBinding
import com.ch2Ps073.diabetless.ui.articles.ArticlesActivity
import com.ch2Ps073.diabetless.ui.main.MainActivity
import com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.BottomSheetMenuFragment
import com.ch2Ps073.diabetless.ui.main.ui.glycemic.GlycemicIndexFragment
import com.ch2Ps073.diabetless.ui.main.ui.health.HealthFragment
import com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.MealPlanFragment


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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

        binding.articleMore.setOnClickListener {
            val iArticle = Intent(requireContext(), ArticlesActivity::class.java)
            startActivity(iArticle)
        }

        binding.healthButton.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_main, HealthFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        binding.glycemicButton.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_main, GlycemicIndexFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
            val activity =  requireActivity() as MainActivity;
            activity.binding.navView.isVisible = false
        }

        binding.mealPlanButton.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_main, MealPlanFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
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