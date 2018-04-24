package com.example.lixiang.emall_01.wxapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.emall_core.net.RestClient;
import com.example.emall_ec.main.demand.PayMethodDelegate;
import com.example.lixiang.emall_01.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, "wxd12cdf5edf0f42fd");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @SuppressLint({"LongLogTag", "ApplySharedPref"})
    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

        SharedPreferences sp = getSharedPreferences("WX_RETURN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("WX","" + resp.errCode);
        editor.commit();
        finish();
//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle("提示");
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errStr)));
//			builder.show();
//		}
//        if (resp.errCode == 0) {
////            val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
////                    val bundle = Bundle()
////            bundle.putString("PAGE_FROM", "SETTING")
////            delegate.arguments = bundle
////            start(delegate)
//
//        }
//        if (resp.errCode == -1) {
//
//        }
//        if (resp.errCode == -2) {
//            Toast.makeText(this, "user cancle", Toast.LENGTH_LONG).show();
//        }

    }
}