package com.test.thecocktaildb.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.test.thecocktaildb.DrinksItem
import com.test.thecocktaildb.R
import com.test.thecocktaildb.databinding.ActivityDetailCockTailBinding
import com.test.thecocktaildb.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailCockTailActivity : AppCompatActivity() {

    private lateinit var data: DrinksItem
    private val viewModel by viewModels<DetailCockTailViewModel>()
    private var _binding: ActivityDetailCockTailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        data = intent.getParcelableExtra("data")

        _binding = ActivityDetailCockTailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel.initDetail(data.idDrink)
        viewModel.getDetail().observe(this){
            when(it.status){
                Status.SUCCESS -> {
                    binding.progress.visibility = View.GONE

                    if (it.data != null) {
                        binding.lyHeader.tvName.text = it.data.drinks[0].strDrink

                        Glide.with(this)
                                .load(it.data.drinks[0].strDrinkThumb)
                                .centerCrop()
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .error(R.drawable.ic_error)
                                .into(binding.lyHeader.ivThumb)

                        binding.lyInstructions.tvInstructions.text = it.data.drinks[0].strInstructions
                        binding.lyDetails.tvCategory.text = it.data.drinks[0].strCategory
                        binding.lyDetails.tvGlass.text = it.data.drinks[0].strGlass

                    }
                }
                Status.ERROR -> {
                    binding.progress.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                }
            }
        }

    }
}