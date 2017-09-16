package com.lbcompany.betu

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lbcompany.betu.adapter.MyBetsAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = ArrayList<Any>()

        list.add("my first bet")
        list.add("my second bet")
        list.add("my third bet")
        list.add("more bets...")

        my_bets_layout.setOnClickListener {
            if (my_bets_recycler.visibility == View.GONE) {
                my_bets_recycler.visibility = View.VISIBLE
                val mAdapter = MyBetsAdapter(this@MainActivity, list)
                val layoutManager = LinearLayoutManager(this@MainActivity)
                my_bets_recycler.layoutManager = layoutManager
                my_bets_recycler.adapter = mAdapter
            } else{
                my_bets_recycler.visibility = View.GONE
            }
        }

        most_popular_layout.setOnClickListener {
            if (most_popular_recycler.visibility == View.GONE) {
                most_popular_recycler.visibility = View.VISIBLE
                val mAdapter = MyBetsAdapter(this@MainActivity, list)
                val layoutManager = LinearLayoutManager(this@MainActivity)
                most_popular_recycler.layoutManager = layoutManager
                most_popular_recycler.adapter = mAdapter
            } else{
                most_popular_recycler.visibility = View.GONE
            }
        }

        categories_layout.setOnClickListener {
            if (categories_recycler.visibility == View.GONE) {
                categories_recycler.visibility = View.VISIBLE
                val mAdapter = MyBetsAdapter(this@MainActivity, list)
                val layoutManager = LinearLayoutManager(this@MainActivity)
                categories_recycler.layoutManager = layoutManager
                categories_recycler.adapter = mAdapter
            } else{
                categories_recycler
                        .visibility = View.GONE
            }        }

    }
}
