package com.test.hackernews.ux

import android.support.v4.app.Fragment

interface Communicator {
    fun showFragment(fragment: Fragment)
}