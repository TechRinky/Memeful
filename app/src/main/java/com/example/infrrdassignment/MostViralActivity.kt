package com.example.infrrdassignment

import APIResponse
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.infrrdassignment.viewmodels.MostViralMemeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.progress_layout.*

class MostViralActivity : AppCompatActivity() {
    private lateinit var viewModel: MostViralMemeViewModel
    private val num_columns = 2;
    private var filteredList = ArrayList<MemesModel>()
    private val mostViralAdapter = MostViralAdapter(filteredList)
    private var currentPage = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        internetObserver()
        setLoaderObserver()
        observeMemeResponse()
        observeFilteredData()
        initRecylerView()
        callMemeApi()
    }

    /**
     * This method is used to set observer on the loader.
     */
    private fun setLoaderObserver() {
        viewModel.loadingState.observe(this, Observer {
            showProgress(it!!)
        })
    }

    /**
     * This method is responsible to observe data
     */
    private fun observeMemeResponse() {
        viewModel.mostViralResponse.observe(this, Observer {
            if (it != null && !it.hasError()) {
                if(it.response!=null && it.response?.data!=null && it.response?.data?.size!! > 0){
                    val mostViralMemeData = it.response
                    updateUi(mostViralMemeData)
                }else if((it.response==null || it.response?.data==null || it.response?.data?.size == 0 ) && currentPage == 1){
                    showEmptyView()
                }

            } else if (it != null && it.hasError()) {
                if(currentPage == 1){
                    showEmptyView()
                }
                showMessage(it)
            }
        })
    }

    private fun showEmptyView() {
        a_progress_bar.visibility = View.GONE
        mostViral_Recylerview.visibility = View.GONE
        no_data_available_tv.visibility = View.VISIBLE
    }

    /**
     * This method is responsible to observe filtered data
     */
    private fun observeFilteredData() {
        viewModel.memeFilteredListLiveData.observe(this, Observer {
            filteredList.addAll(it)
            mostViralAdapter.notifyDataSetChanged()
        })

    }

    /**
     * This method is responsible to show error message in Toast
     */
    private fun showMessage(it: APIResponse<MostViralFeedModel>?) {
        Toast.makeText(this, it?.error?.message, Toast.LENGTH_LONG).show()
    }

    /**
     * This method is responsible to update ui
     */
    private fun updateUi(mostViralMemeData: MostViralFeedModel?) {
        if (mostViralMemeData != null) {
            val mostViralMemeList = ArrayList<MostViralFeedModel.Data>()
            mostViralMemeList.addAll(mostViralMemeData.data)
            viewModel.calculateImagesToShow(mostViralMemeList)
        }
    }

    /**
     * This method is responsible to call meme api
     */
    private fun callMemeApi() {
        viewModel.getMemes(currentPage)
    }


    /**
     * This method is responsible to initialize the viewmodel
     */
    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MostViralMemeViewModel::class.java)
    }

    /**
     * This method is responsible to initialize recylerview and set adapter into it
     */
    private fun initRecylerView() {
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(num_columns, LinearLayoutManager.VERTICAL)
        mostViral_Recylerview.layoutManager = staggeredGridLayoutManager
        mostViral_Recylerview.adapter = mostViralAdapter
        mostViral_Recylerview.addOnScrollListener(object :
            PaginationScrollListener(staggeredGridLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage = currentPage + 1
                callMemeApi()
            }

            override fun isLastPage(): Boolean {
                return false
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
    }

    /**
     * This function is used to observe internet connectivity. If internet is not connected,
     * then it shows No internet connection .
     */
    private fun internetObserver() {
        viewModel.internetState.observe(this, Observer {
            if (it != null && !it) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
            }
        })
    }


    /**
     * This method is responsible to show progress bar when background task is performed
     * @param show This is the boolean param which shows flag to show progress bar or not
     */
    fun showProgress(show: Boolean) {
        isLoading = show
        if (a_progress_bar != null) {
            a_progress_bar?.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

}
