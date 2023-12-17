package com.ch2Ps073.diabetless.ui.main.ui.mealPlanner

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
<<<<<<< HEAD
import com.ch2Ps073.diabetless.data.remote.response.MealItem
import com.ch2Ps073.diabetless.databinding.FragmentMealPlannerIndexBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.adapter.MealsAdapter
=======
import com.CH2PS073.diabetless.ui.adapter.MealsAdapter
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.data.remote.response.MealItem
import com.ch2Ps073.diabetless.databinding.FragmentMealPlannerIndexBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.BottomSheetMenuFragment
>>>>>>> chello
import com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.favorites.FavoritesActivity
import com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.mealCart.MealCartActivity
import com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.mealDetail.MealDetailActivity
import com.ch2Ps073.diabetless.ui.main.ui.mealPlanner.mealDetail.MealDetailActivity.Companion.KEY_MEAL_ID

class MealPlanFragment : Fragment() {

    private var _binding: FragmentMealPlannerIndexBinding? = null
    private val binding get() = _binding!!
    private val mealsAdapter = MealsAdapter()

    private val mealItems = arrayListOf<MealItem>()

    private val viewModel by viewModels<MealPlanIndexViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealPlannerIndexBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity?)?.supportActionBar?.hide()

        observeMeals()
        observeLoading()
        observeMessage()

        setRecyclerView()
        setSearchView()
        setListeners()

        return root
    }

    private fun setListeners() {
        binding.apply {
            btnFav.setOnClickListener {
                startActivity(Intent(requireContext(), FavoritesActivity::class.java))
            }

            btnMeals.setOnClickListener {
                startActivity(Intent(requireContext(), MealCartActivity::class.java))
            }
        }
    }

    private fun observeLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }


    private fun observeMeals() {
        viewModel.allMeals.observe(viewLifecycleOwner) {
            mealItems.addAll(it)
            mealsAdapter.setList(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun setSearchView() {
        with(binding.svMeals) {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(keyword: String): Boolean {
                    return true
                }

<<<<<<< HEAD
                override fun onQueryTextChange(newText: String): Boolean {
                    filter(newText)
                    return false
                }
            })
        }
    }

    private fun setRecyclerView() {
        mealsAdapter.onItemClick = { id ->
            val iMealDetail = Intent(requireContext(), MealDetailActivity::class.java)
            iMealDetail.putExtra(KEY_MEAL_ID, id)
            startActivity(iMealDetail)
        }

        with(binding.rvMeals) {
            adapter = mealsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.HORIZONTAL
                )
            )
        }
    }

    private fun filter(query: String) {
        val filteredlist = ArrayList<MealItem>()

        for (item in mealItems) {
            if (item.title!!.toLowerCase().contains(query.toLowerCase())) {
                filteredlist.add(item)
            }
        }

        if (filteredlist.isNotEmpty()) {
            mealsAdapter.filterList(filteredlist)
        }
    }

    private fun observeMessage() {
        viewModel.toastMessage.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                showToast(message)
            }
        }
=======
                else -> false
            }
        }

        observeMeals()
        observeLoading()
        observeMessage()

        setRecyclerView()
        setSearchView()
        setListeners()

        return root
>>>>>>> chello
    }

    private fun setListeners() {
        binding.apply {
            btnFav.setOnClickListener {
                startActivity(Intent(requireContext(), FavoritesActivity::class.java))
            }

            btnMeals.setOnClickListener {
                startActivity(Intent(requireContext(), MealCartActivity::class.java))
            }
        }
    }

    private fun observeLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }


    private fun observeMeals() {
        viewModel.allMeals.observe(viewLifecycleOwner) {
            mealItems.addAll(it)
            mealsAdapter.setList(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun setSearchView() {
        with(binding.svMeals) {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(keyword: String): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    filter(newText)
                    return false
                }
            })
        }
    }

    private fun setRecyclerView() {
        mealsAdapter.onItemClick = { id ->
            val iMealDetail = Intent(requireContext(), MealDetailActivity::class.java)
            iMealDetail.putExtra(KEY_MEAL_ID, id)
            startActivity(iMealDetail)
        }

        with(binding.rvMeals) {
            adapter = mealsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.HORIZONTAL
                )
            )
        }
    }

    private fun filter(query: String) {
        val filteredlist = ArrayList<MealItem>()

        for (item in mealItems) {
            if (item.title!!.toLowerCase().contains(query.toLowerCase())) {
                filteredlist.add(item)
            }
        }

        if (filteredlist.isNotEmpty()) {
            mealsAdapter.filterList(filteredlist)
        }
    }

    private fun observeMessage() {
        viewModel.toastMessage.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                showToast(message)
            }
        }
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