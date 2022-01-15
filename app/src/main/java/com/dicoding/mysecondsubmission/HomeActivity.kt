package com.dicoding.mysecondsubmission

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mysecondsubmission.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {


    private lateinit var binding : ActivityHomeBinding
    private lateinit var adapter: UserAdapter
    private val viewModel by viewModels<MainViewModel>()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = adapter
        binding.rvUser.setHasFixedSize(true)

        showLoading(false)

        viewModel.getListUsers().observe(this, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })

        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

        adapter.setOnItemClick(object : UserAdapter.OnItemClick{
            override fun onItemClicked(data: User) {
                Intent(this@HomeActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_URL, data.avatar_url )
                    startActivity(it)
                }
            }

        })
    }



    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }





    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                viewModel.searchUser(query)
                return true
            }
            override fun onQueryTextChange(query: String): Boolean {
                showLoading(true)
                viewModel.searchUser(query)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite ->{
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.preference -> {
                val moveIntentAbout = Intent(this@HomeActivity, PreferencesActivity::class.java)
                startActivity(moveIntentAbout)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}