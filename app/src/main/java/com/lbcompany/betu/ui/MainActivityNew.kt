package com.lbcompany.betu.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.lbcompany.betu.R
import com.lbcompany.betu.ui.fragment.GroupalBetsFragment
import kotlinx.android.synthetic.main.activity_main_new.*

class MainActivityNew : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_new)

        setSupportActionBar(toolbar_mainnn)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupViewPager(viewpager)

        tabs.setupWithViewPager(viewpager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(GroupalBetsFragment(), "ONE")
        adapter.addFragment(GroupalBetsFragment(), "TWO")
        viewPager.adapter = adapter
    }

    class ViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment =mFragmentList[position]

        override fun getCount(): Int = mFragmentList.size

        override fun getPageTitle(position: Int): CharSequence = mFragmentTitleList[position]

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

    }
}

