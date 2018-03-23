package com.example.emall_ec.main.search

import com.example.emall_core.delegates.bottom.BaseBottomDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_search.*

/**
 * Created by lixiang on 2018/3/20.
 */
class SearchDelegate : BottomItemDelegate() {

    fun create(): SearchDelegate? {
        return SearchDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_search
    }

    override fun initial() {
        search_searchbar_rl.setOnClickListener {
            start(SearchPoiDelegate().create())
        }

        search_btn.setOnClickListener {
            start(SearchResultDelegate().create())
        }
    }
}