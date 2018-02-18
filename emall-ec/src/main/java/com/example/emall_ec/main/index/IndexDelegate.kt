package com.example.emall_ec.main.index

import android.graphics.Color
import android.os.Bundle
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_index.*
import com.example.emall_core.ui.refresh.RefreshHandler
 import android.support.v7.widget.GridLayoutManager
import com.example.emall_core.util.log.EmallLogger


/**
 * Created by lixiang on 15/02/2018.
 */
class IndexDelegate : BottomItemDelegate() {

    var refreshHandler: RefreshHandler? = null
    override fun setLayout(): Any? {
        return R.layout.delegate_index
    }

    fun initRefreshLayout() {

        swipe_refresh_layout_index.setColorSchemeColors(Color.parseColor("#b4a078"))
        swipe_refresh_layout_index.setProgressViewOffset(true, 120, 300)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        EmallLogger.d("on lazy")
        super.onLazyInitView(savedInstanceState)
        initRefreshLayout()
        initRecyclerView()
        refreshHandler!!.firstPage("http://10.0.2.2:3003/data")

    }

    private fun initRecyclerView() {
        val manager = GridLayoutManager(context, 4)
        recycler_view_index.layoutManager = manager
//        recycler_view_index.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(context, R.color.app_background), 5))
//        val ecBottomDelegate = getParentDelegate()
//        recycler_view_index.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate))
    }
    override fun initial() {
        EmallLogger.d("initial")
        refreshHandler = RefreshHandler.create(swipe_refresh_layout_index, recycler_view_index, IndexDataConverter())
//        RestClient().builder()
//                .url("http://10.0.2.2:3003/data")
//                .success(object : ISuccess {
//                    override fun onSuccess(response: String) {
//                        val converter : IndexDataConverter = IndexDataConverter()
//                        converter.setJsonData(response)
//                        val list : ArrayList<MultipleItemEntity> = converter.convert()
//                        val image : String = list[1].getField(MultipleFields.IMAGE_URL)
//                        EmallLogger.d("INDEX_DELEGATE",image)
////                        EmallLogger.d(response)
//                    }
//                })
//                .build()
//                .get()
    }
}