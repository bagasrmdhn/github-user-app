package com.dicoding.mysecondsubmission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.dicoding.mysecondsubmission.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this
        ).get(DetailViewModel::class.java)

        val id = intent.getIntExtra(EXTRA_ID, 0)
        val username: String = intent.getStringExtra(EXTRA_USERNAME).toString()
        val avatarUrl: String = intent.getStringExtra(EXTRA_URL).toString()

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel.setUserDetail(username)

        viewModel.getDetailUsers().observe(this, {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvCompany.text = it.company
                    tvLocation.text = it.location
                    follower.text = it.followers.toString()
                    following.text = it.following.toString()
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .transform(CircleCrop())
                        .into(imgItemAvatar)
                }
            }
        })

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.userCheck(id)
            withContext(Dispatchers.Main){
                if(count != null) {
                    if (count > 0) {
                        binding.toggleFavorite.isChecked = true
                        isChecked = true
                    } else {
                        binding.toggleFavorite.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener{
            isChecked = !isChecked
            if (isChecked){
                viewModel.addFavorite(username, id, avatarUrl)
            }else{
                viewModel.deleteFromFavortie(id)
            }
            binding.toggleFavorite.isChecked = isChecked
        }

        val sectionsPagerAdapter = PagerAdapter(this@DetailActivity, username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TITLE_TAB[position])
        }.attach()

        viewModel.isLoading.observe(this, {
            showLoading(it)
        })
    }
    companion object {
        val EXTRA_URL = "extra_url"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"


        @StringRes
        private val TITLE_TAB = intArrayOf(R.string.followers, R.string.following)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}