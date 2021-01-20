package com.andrian.githubuserapi

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrian.githubuserapi.adapter.MainAdapter
import com.andrian.githubuserapi.data.User
import com.andrian.githubuserapi.data.UserList
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val userList: MutableList<User> = mutableListOf()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainAdapter = MainAdapter(userList)

        AndroidNetworking.initialize(this)

        AndroidNetworking.get("https://api.github.com/search/users?q=sidiqpermana")
            .build()
            .getAsObject(UserList::class.java, object : ParsedRequestListener<UserList> {
                override fun onResponse(response: UserList) {
                    userList.addAll(response.items)
                    mainAdapter.notifyDataSetChanged()
                }

                override fun onError(anError: ANError?) {

                }
            }
            )


        recyclerMain.layoutManager = LinearLayoutManager(this)
        recyclerMain.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
        recyclerMain.adapter = mainAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}
