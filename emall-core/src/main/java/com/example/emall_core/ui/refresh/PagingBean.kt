package com.example.emall_core.ui.refresh

/**
 * Created by lixiang on 18/02/2018.
 */
class PagingBean {
    //当前是第几页
    private var mPageIndex = 0
    //总数据条数
    private var mTotal = 0
    //一页显示几条数据
    private var mPageSize = 0
    //当前已经显示了几条数据
    private var mCurrentCount = 0
    //加载延迟
    private var mDelayed = 0

    fun getPageIndex(): Int {
        return mPageIndex
    }

    fun setPageIndex(mPageIndex: Int): PagingBean {
        this.mPageIndex = mPageIndex
        return this
    }

    fun getTotal(): Int {
        return mTotal
    }

    fun setTotal(mTotal: Int): PagingBean {
        this.mTotal = mTotal
        return this
    }

    fun getPageSize(): Int {
        return mPageSize
    }

    fun setPageSize(mPageSize: Int): PagingBean {
        this.mPageSize = mPageSize
        return this
    }

    fun getCurrentCount(): Int {
        return mCurrentCount
    }

    fun setCurrentCount(mCurrentCount: Int): PagingBean {
        this.mCurrentCount = mCurrentCount
        return this
    }

    fun getDelayed(): Int {
        return mDelayed
    }

    fun setDelayed(mDelayed: Int): PagingBean {
        this.mDelayed = mDelayed
        return this
    }

    internal fun addIndex(): PagingBean {
        mPageIndex++
        return this
    }
}
