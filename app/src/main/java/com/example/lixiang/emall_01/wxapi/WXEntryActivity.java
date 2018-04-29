package com.example.lixiang.emall_01.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.lixiang.emall_01.R;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by lixiang on 2017/12/4.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI mWxApi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx);
        mWxApi = WXAPIFactory.createWXAPI(this, "wxd12cdf5edf0f42fd", false);
        mWxApi.handleIntent(getIntent(), this);

        try {
            mWxApi.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWxApi.handleIntent(intent, this);
        System.out.println("------");
    }

    @Override
    public void onReq(BaseReq baseReq) {
        System.out.println("--)(");
    }

    @Override
    public void onResp(BaseResp baseResp) {
        int result = 0;

//        Toast.makeText(this, "baseresp.getType = " + baseResp.getType(), Toast.LENGTH_SHORT).show();

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }

        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        finish();
    }
}
