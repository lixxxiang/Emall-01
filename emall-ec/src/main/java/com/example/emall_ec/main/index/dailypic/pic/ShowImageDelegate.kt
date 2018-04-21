package com.example.emall_ec.main.index.dailypic.pic

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_show_image.*
import kotlinx.android.synthetic.main.pic_detail_1.*
import kotlinx.android.synthetic.main.show_image_fragment.*
import kotlinx.android.synthetic.main.show_image_fragment.view.*

class ShowImageDelegate : EmallDelegate() {
    lateinit var string_array: Array<String>
    lateinit var images: Array<String>
    var picAmount = String()
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    fun create(): ShowImageDelegate? {
        return ShowImageDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_show_image
    }

    override fun initial() {
        string_array = arrayOf("0", "1", "2")
        images = arguments.getString("images").split(",").toTypedArray()
        picAmount = arguments.getString("picAmount")

        EmallLogger.d(images)
        string_array[0] = images[0]

        if (picAmount.toInt() >= 2 && !images.elementAt(1).isEmpty())
            string_array[1] = images.elementAt(1)
        if (picAmount.toInt() == 3 && !images.elementAt(2).isEmpty())
            string_array[2] = images.elementAt(2)

        mSectionsPagerAdapter = SectionsPagerAdapter(activity.supportFragmentManager)///////////////////////////////////

        // Set up the ViewPager with the sections adapter.
        show_image_vp.adapter = mSectionsPagerAdapter
        show_image_vp.currentItem = arguments.getString("index").toInt()
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position, string_array)
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return arguments.getString("picAmount").toInt()
        }
    }

    class PlaceholderFragment : EmallDelegate() {
        override fun setLayout(): Any? {
            return R.layout.show_image_fragment
        }

        override fun initial() {
            println("url---->$url")
            Glide.with(context)
                    .load(url[arguments.getInt(ARG_SECTION_NUMBER)])
                    .into(image)
            urlString = url[arguments.getInt(ARG_SECTION_NUMBER)]
            image.setOnClickListener {
                pop()
            }
        }

//        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
//        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                                  savedInstanceState: Bundle?): View? {
//            val rootView = inflater.inflate(, container, false)
//
//
//
//            return rootView
//        }


        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"
            var url: Array<String> = arrayOf("0","1","2")
            var urlString = ""
            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int, array: Array<String>): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                url = array
                return fragment
            }
        }
    }
}