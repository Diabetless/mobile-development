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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.databinding.FragmentHomeBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.adapter.CarouselAdapter
import com.ch2Ps073.diabetless.ui.articles.ArticlesActivity
import com.ch2Ps073.diabetless.ui.articles.ArticlesViewModel
import com.ch2Ps073.diabetless.ui.main.MainActivity
import com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.BottomSheetMenuFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ArticlesViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private val carouselAdapter = CarouselAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvCarousel.layoutManager = layoutManager

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
            findNavController().navigate(R.id.navigation_health)
        }

        binding.glycemicButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_glycemic)
            val activity =  requireActivity() as MainActivity;
            activity.binding.navView.isVisible = false
        }

        binding.mealPlanButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_meal_planner)
        }

        setItemCarousel()

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

    private fun setItemCarousel(){
        binding.rvCarousel.adapter = carouselAdapter
        viewModel.allArticles.observe(requireActivity()){
            carouselAdapter.setList(it)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }
}