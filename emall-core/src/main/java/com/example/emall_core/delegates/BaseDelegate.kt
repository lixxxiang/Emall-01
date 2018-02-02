package com.example.emall_core.delegates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment

/**
 * Created by lixiang on 2018/1/25.
 */
abstract class BaseDelegate : SwipeBackFragment(){
    abstract fun setLayout(): Any?
    abstract fun initial()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView : View? = null
        when {
            setLayout() is Int -> {
                rootView = inflater!!.inflate(setLayout() as Int, container, false)
            }
            setLayout() is View -> {
                rootView = setLayout() as View

            }
            else -> println("the fuck")
        }
         return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initial()
    }

}