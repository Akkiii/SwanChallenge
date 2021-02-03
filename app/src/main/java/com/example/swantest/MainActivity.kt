package com.example.swantest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.swantest.fragment.ClosePRFragment
import com.example.swantest.fragment.OpenPRFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var pagerAdapter: PagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureView()
    }

    private fun configureView() {
        pagerAdapter = PagerAdapter(supportFragmentManager)
        pagerAdapter?.addFragment(OpenPRFragment(), "Open PR")
        pagerAdapter?.addFragment(ClosePRFragment(), "Closed PR")
        viewPagerPR.adapter = pagerAdapter

        tabLayout.setupWithViewPager(viewPagerPR)
    }

    inner class PagerAdapter(fm: FragmentManager?) :
        FragmentStatePagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private var mFragmentList: ArrayList<Fragment> = ArrayList()
        private var mFragmentTitleList: ArrayList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }

}