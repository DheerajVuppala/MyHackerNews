package com.test.hackernews.ux

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.test.hackernews.R
import com.test.hackernews.ux.fragments.TopStoriesListFragment

class MainActivity : AppCompatActivity(), Communicator {

    override fun showFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(fragment::class.java.simpleName)
        fragmentTransaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showTopStoriesFragment()
    }

    private fun showTopStoriesFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, TopStoriesListFragment())
        fragmentTransaction.commit()
    }
}
