package com.vasist.projecthilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vasist.projecthilt.adopter.RecyclerAdopter
import com.vasist.projecthilt.databinding.ActivityMainBinding
import com.vasist.projecthilt.model.Result
import com.vasist.projecthilt.network.NetworkService
import com.vasist.projecthilt.viewmodel.MainModelFactory
import com.vasist.projecthilt.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var uData  : MutableList<Result>
    private lateinit var adopter: RecyclerAdopter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val quoteService =
            NetworkService.provideRetrofit().create(NetworkService.ResultDataService::class.java)
        val repository = Repository(quoteService)
        mainViewModel =
            ViewModelProvider(this, MainModelFactory(repository))[MainViewModel::class.java]
        mainViewModel.quotes.observe(this) { it ->
            uData = it.results as MutableList<Result>
            it.results.sortBy { it.name.first }
            adopter = RecyclerAdopter(this@MainActivity, it.results)
            binding.recyclerViewID.layoutManager = LinearLayoutManager(this@MainActivity)
            binding.recyclerViewID.adapter = adopter
        }
        binding.projectHiltSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                fileList(newText.toString())
                return true
            }
        })
    }
    private fun fileList(filterItem: String) {
        val tempList:MutableList<Result> = ArrayList()
        for (d in uData) {
            if (filterItem in d.name.toString().lowercase()){
                tempList.add(d)

            }else if (filterItem in d.phone){
                tempList.add(d)
            }
        }
        adopter.updateList(tempList)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.MenuFemale ->{
                adopter.updateList(uData.filter { it.gender=="female" } as MutableList<Result>)
            }
            R.id.menuMale ->{
                adopter.updateList(uData.filter { it.gender=="male" } as MutableList<Result>)
            }
            R.id.MenuClearFilter -> {
                adopter.updateList(uData)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}