package com.andy_dev.arpractical.view.activity

import android.app.Activity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andy_dev.arpractical.R
import com.andy_dev.arpractical.databinding.ActivityMainBinding
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
    private lateinit var factsViewModel: FactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this@MainActivity,
            R.layout.activity_main
        )
        init()
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
                binding.rvItemsList.visibility = VISIBLE
                binding.rvItemsList.adapter = FactsListAdapter(ArrayList(it))
            }
        })
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
