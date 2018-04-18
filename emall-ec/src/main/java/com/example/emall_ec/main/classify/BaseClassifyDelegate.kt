package com.example.emall_ec.main.classify

import android.os.Bundle
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.classify.data.fuckOthers.ApiService
import com.example.emall_ec.main.classify.data.fuckOthers.NetUtils
import com.example.emall_ec.main.classify.data.SceneSearch
import com.example.emall_ec.main.classify.data.VideoHomeBean
import com.example.emall_ec.main.classify.data.VideoSearch
import com.example.emall_ec.main.program.ProgramDelegate
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

    }
}