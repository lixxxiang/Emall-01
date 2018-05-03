package com.example.emall_ec.main.classify

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.NetworkUtils
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.classify.data.fuckOthers.ApiService
import com.example.emall_ec.main.classify.data.fuckOthers.NetUtils
import com.example.emall_ec.main.classify.data.SceneSearch
import com.example.emall_ec.main.classify.data.VideoHomeBean
import com.example.emall_ec.main.classify.data.VideoSearch
import com.example.emall_ec.main.program.ProgramDelegate
import com.example.emall_ec.main.search.SearchDelegate
import kotlinx.android.synthetic.main.delegate_base_classify.*
import retrofit2.Retrofit
import java.util.*

/**
 * Created by lixiang on 2018/3/12.
 */
class BaseClassifyDelegate : BottomItemDelegate() {
    var DELEGATE: EmallDelegate? = null
    var sceneSearch = SceneSearch()
    var videoSearch = VideoSearch()
    var videoHome = VideoHomeBean()

    var ssp: WeakHashMap<String, Any>? = WeakHashMap()
    internal var retrofit: Retrofit? = null
    internal var apiService: ApiService? = null
    override fun setLayout(): Any? {
        return R.layout.delegate_base_classify
    }

    override fun initial() {
        setSwipeBackEnable(false)
        DELEGATE = getParentDelegate()
        base_classify_optics_siv.setOnClickListener {
            val delegate: ClassifyDelegate = ClassifyDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("TYPE", "SCENE")
            delegate.arguments = bundle
            (DELEGATE as EcBottomDelegate).start(delegate)
        }

        base_classify_noctilucence_siv.setOnClickListener {
            val delegate: ClassifyDelegate = ClassifyDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("TYPE", "NOCTILUCENCE")
            delegate.arguments = bundle
            (DELEGATE as EcBottomDelegate).start(delegate)
        }

        base_classify_program_siv.setOnClickListener {

            (DELEGATE as EcBottomDelegate).start(ProgramDelegate().create()!!)
        }

        base_classify_video_siv.setOnClickListener {
            val delegate: ClassifyDelegate = ClassifyDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("TYPE", "VIDEO")
            delegate.arguments = bundle
            (DELEGATE as EcBottomDelegate).start(delegate)
        }

        base_classify_search_rl.setOnClickListener {
            if (!NetworkUtils.isConnected())
                Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
            else {
                val delegate: SearchDelegate = SearchDelegate().create()!!
                (DELEGATE as EcBottomDelegate).start(delegate)
            }
        }

        if(!NetworkUtils.isConnected()){
            classify_no_network_rl.visibility = View.VISIBLE
            base_classify_images_on_top_ll.visibility = View.GONE
            base_classify_images_on_bottom_ll.visibility = View.GONE
            classify_line.visibility = View.GONE
            classify_tv.visibility = View.GONE

        }else{
            classify_no_network_rl.visibility = View.GONE
            base_classify_images_on_top_ll.visibility = View.VISIBLE
            base_classify_images_on_bottom_ll.visibility = View.VISIBLE
            classify_line.visibility = View.VISIBLE
            classify_tv.visibility = View.VISIBLE
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }
}