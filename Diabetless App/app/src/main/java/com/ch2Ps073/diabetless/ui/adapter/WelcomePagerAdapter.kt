package com.ch2Ps073.diabetless.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ch2Ps073.diabetless.ui.welcome.fragments.FifthFragment
import com.ch2Ps073.diabetless.ui.welcome.fragments.FirstFragment
import com.ch2Ps073.diabetless.ui.welcome.fragments.FourthFragment
import com.ch2Ps073.diabetless.ui.welcome.fragments.SecondFragment
import com.ch2Ps073.diabetless.ui.welcome.fragments.ThirdFragment

class WelcomePagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FirstFragment()
            1 -> fragment = SecondFragment()
            2 -> fragment = ThirdFragment()
            3 -> fragment = FourthFragment()
            4 -> fragment = FifthFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 5
    }
}