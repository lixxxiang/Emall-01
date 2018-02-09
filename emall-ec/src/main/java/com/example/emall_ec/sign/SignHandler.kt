package com.example.emall_ec.sign

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.example.emall_core.app.AccountManager
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.database.UserProfile



/**
 * Created by lixiang on 08/02/2018.
 */
class SignHandler  {
    fun onSignUp(response : String, signListener : ISignListener) {
//        val profileJson : JSONObject = JSON.parseObject(response).getJSONObject("data")
        val profileJson : JSONObject = JSON.parseObject(response)
        val userId = profileJson.getLong("userId")!!
        val name = profileJson.getString("name")
        val avatar = profileJson.getString("avatar")
        val gender = profileJson.getString("gender")
        val address = profileJson.getString("address")

        val profile = UserProfile(userId, name, avatar, gender, address)
        DatabaseManager().getInstance()!!.getDao()!!.insert(profile)


        AccountManager().setSignState(true)
        signListener.onSignUpSuccess()
    }

}
