package com.example.emall_ec.main.sign

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.example.emall_core.app.AccountManager
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R.id.*
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.database.UserProfile


/**
 * Created by lixiang on 08/02/2018.
 */
class SignHandler {
    fun onSignUp(response: String, signListener: ISignListener) {
        val profileJson : JSONObject = JSON.parseObject(response).getJSONObject("userInfo")
//        val profileJson: JSONObject = JSON.parseObject(response)
        val userId = profileJson.getString("userId")!!
        EmallLogger.d(userId)
        val username = profileJson.getString("username")
        val level = profileJson.getString("level")
        val label = profileJson.getString("label")
        val userTelephone = profileJson.getString("userTelephone")
        val userPassword = profileJson.getString("userPassword")
        val imagePath = profileJson.getString("imagePath")
        val gmtCreated = profileJson.getString("gmtCreated")


        val profile = UserProfile(userId, username, level, label, userTelephone, userPassword, imagePath, gmtCreated)
        DatabaseManager().getInstance()!!.getDao()!!.insert(profile)


        AccountManager().setSignState(true)
        signListener.onSignUpSuccess()
    }

    fun onSignIn(response: String, signListener: ISignListener) {
        var profileJson = JSONObject()
        if(JSON.parseObject(response).getJSONObject("userInfo") == null){
            profileJson = JSON.parseObject(response).getJSONObject("user")
        }else{
            profileJson = JSON.parseObject(response).getJSONObject("userInfo")
        }

//        val profileJson: JSONObject = JSON.parseObject(response)
        val userId = profileJson.getString("userId")!!
        val username = profileJson.getString("username")
        val level = profileJson.getString("level")
        val label = profileJson.getString("label")
        val userTelephone = profileJson.getString("userTelephone")
        val userPassword = profileJson.getString("userPassword")
        val imagePath = profileJson.getString("imagePath")
        val gmtCreated = profileJson.getString("gmtCreated")

        val profile = UserProfile(userId, username, level, label, userTelephone, userPassword, imagePath, gmtCreated)
        DatabaseManager().getInstance()!!.getDao()!!.insert(profile)


        AccountManager().setSignState(true)
        signListener.onSignInSuccess()
    }

}
