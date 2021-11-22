package com.test.thecocktaildb.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.test.thecocktaildb.DrinksItem
import com.test.thecocktaildb.R
import com.test.thecocktaildb.databinding.FragmentDrinksBinding
import com.test.thecocktaildb.ui.details.DetailCockTailActivity
import com.test.thecocktaildb.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrinkFragment : Fragment(), CockTailAdapter.OnItemClickListener {

    private val viewModel by viewModels<DrinkViewModel>()
    private var _binding: FragmentDrinksBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CockTailAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDrinksBinding.bind(view)

        adapter = CockTailAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = CockTailLoadStateAdapter { adapter.retry() },
                footer = CockTailLoadStateAdapter { adapter.retry() },
            )
            buttonRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.cockTail.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_drink, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchCockTail(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.category -> {
                viewModel.queryCategory.observe(viewLifecycleOwner) {
                    adapter.submitData(viewLifecycleOwner.lifecycle, PagingData.empty())
                    adapter.submitData(viewLifecycleOwner.lifecycle, it)
                }

                return true
            }
            R.id.glass -> {
                viewModel.queryGlass.observe(viewLifecycleOwner) {
                    adapter.submitData(viewLifecycleOwner.lifecycle, PagingData.empty())
                    adapter.submitData(viewLifecycleOwner.lifecycle, it)

                }
                return true
            }
            R.id.alcoholic -> {
                viewModel.queryAlcoholic.observe(viewLifecycleOwner) {
                    adapter.submitData(viewLifecycleOwner.lifecycle, PagingData.empty())
                    adapter.submitData(viewLifecycleOwner.lifecycle, it)

                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drinks, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(drinksItem: DrinksItem) {
        val intent = Intent(context, DetailCockTailActivity::class.java)
        intent.putExtra("data", drinksItem)
        startActivity(intent)
    }


}