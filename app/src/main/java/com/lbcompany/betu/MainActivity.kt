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

        setAdapterList(list)

        setMyBetsAdapter(list)
        setPopularAdapter(list)
        setCategoriesAdapter(list)

        setViews()

    }

    private fun setViews() {
        my_bets_layout.setOnClickListener {
            changeVisibility(my_bets_recycler)
        }

        most_popular_layout.setOnClickListener {
            changeVisibility(most_popular_recycler)
        }

        categories_layout.setOnClickListener {
            changeVisibility(categories_recycler)
        }
    }

    private fun changeVisibility(itemView: View) {
        if (itemView.visibility == View.GONE) {
            itemView.visibility = View.VISIBLE
        } else {
            itemView.visibility = View.GONE
        }
    }

    private fun setMyBetsAdapter(list: ArrayList<Any>) {
        my_bets_recycler.visibility = View.VISIBLE
        val mAdapter = MyBetsAdapter(this@MainActivity, list)
        val layoutManager = LinearLayoutManager(this@MainActivity)
        my_bets_recycler.layoutManager = layoutManager
        my_bets_recycler.adapter = mAdapter
    }

    private fun setPopularAdapter(list: ArrayList<Any>) {
        most_popular_recycler.visibility = View.VISIBLE
        val mAdapter = MyBetsAdapter(this@MainActivity, list)
        val layoutManager = LinearLayoutManager(this@MainActivity)
        most_popular_recycler.layoutManager = layoutManager
        most_popular_recycler.adapter = mAdapter
    }

    private fun setCategoriesAdapter(list: ArrayList<Any>) {
        categories_recycler.visibility = View.VISIBLE
        val mAdapter = MyBetsAdapter(this@MainActivity, list)
        val layoutManager = LinearLayoutManager(this@MainActivity)
        categories_recycler.layoutManager = layoutManager
        categories_recycler.adapter = mAdapter
    }

    private fun setAdapterList(list: ArrayList<Any>) {
        list.add("my first bet")
        list.add("my second bet")
        list.add("my third bet")
        list.add("more bets...")
    }
}
