package com.example.emall_ec.database

import android.content.Context
import org.greenrobot.greendao.database.Database

/**
 * Created by lixiang on 08/02/2018.
 */
class ReleaseOpenHelper(context: Context?, name: String?) : DaoMaster.OpenHelper(context, name) {
    override fun onCreate(db: Database?) {
        super.onCreate(db)
    }

}
