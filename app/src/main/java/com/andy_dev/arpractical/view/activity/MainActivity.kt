package com.andy_dev.arpractical.view.activity

import android.app.Activity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.andy_dev.arpractical.R
import com.andy_dev.arpractical.databinding.ActivityMainBinding
import com.andy_dev.arpractical.model.Facts
import com.andy_dev.arpractical.remote.RetrofitFactory
import com.andy_dev.arpractical.room.FactDatabase
import com.andy_dev.arpractical.utils.LogUtil
import com.andy_dev.arpractical.utils.Utility
import com.andy_dev.arpractical.view.adapter.FactsListAdapter
import com.andy_dev.arpractical.view_model.FactsRepo
import com.andy_dev.arpractical.view_model.FactsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName
    private lateinit var mActivity: Activity
    private val STATE_ITEMS = "items"
    private val STATE_POS = "position"
    private var factItems: ArrayList<Facts> = ArrayList()
    private var pos = 0
    private lateinit var factsViewModel: FactsViewModel
    private var hasConfig: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this@MainActivity,
            R.layout.activity_main
        )
        when {
            savedInstanceState != null -> {
                hasConfig = true
                factItems = savedInstanceState.getSerializable(STATE_ITEMS) as ArrayList<Facts>
                pos = savedInstanceState.getInt(STATE_POS, 0)
            }
        }
        init()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val manager = binding.rvItemsList.layoutManager as LinearLayoutManager
        outState.putSerializable(STATE_ITEMS, factItems)
        outState.putInt(STATE_POS, manager.findFirstCompletelyVisibleItemPosition())
    }

    private fun init() {
        mActivity = this@MainActivity
        supportActionBar?.title = "Facts"

        initViewModel()

        Utility.setSwipeColor(binding.swipeLayout)
        binding.swipeLayout.setOnRefreshListener {
            factsViewModel.loadFacts(false)
        }
    }

    private fun initViewModel() {

        factsViewModel = ViewModelProviders.of(this).get(FactsViewModel::class.java)
        val database = FactDatabase.getDatabase(this)
        val factsRepo = FactsRepo(RetrofitFactory.makeRetrofitService(), database.factsDao())
        factsViewModel.setFactRepo(factsRepo)

        if (hasConfig)
            initFactItems()
        else
            factsViewModel.loadFacts(true)


        factsViewModel.factLoader().observe(this, Observer {
            binding.progressBar.visibility = when {
                it -> VISIBLE
                else -> GONE
            }

            when {
                !it -> hideSwipe()
            }
        })

        factsViewModel.showNoData().observe(this, Observer {
            binding.txtNoData.visibility = when {
                it -> VISIBLE
                else -> GONE
            }
        })

        factsViewModel.factError().observe(this, Observer {
            LogUtil.LOGE(TAG, "factError::$it")
        })

        factsViewModel.factResults().observe(this, Observer {
            LogUtil.LOGE(TAG, "factResults::$it")

            if (!it.isNullOrEmpty()) {
                factItems = ArrayList(it)
                initFactItems()
            }
        })
    }

    private fun initFactItems() {
        binding.rvItemsList.visibility = VISIBLE
        binding.rvItemsList.adapter = FactsListAdapter(factItems)
        when (pos > 0) {
            true -> binding.rvItemsList.scrollToPosition(pos)
        }

    }

    private fun hideSwipe() {
        if (binding.swipeLayout.isRefreshing)
            binding.swipeLayout.isRefreshing = false
    }

    override fun onDestroy() {
        factsViewModel.disposeElements()
        super.onDestroy()
    }
}
