package com.kingsoft.sdktest.base


import android.content.Context
import org.robolectric.Robolectric
import pl.polidea.robospock.RoboSpecification

/**
 * Created by jianan on 15/10/12.
 */
class BaseTestCase extends RoboSpecification{
    protected Context mContext;
    protected  mApplication;

    def init() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false)
        Robolectric.addPendingHttpResponse(400, "Bad Request");
        mContext = Robolectric.getShadowApplication().getApplicationContext();
        mApplication = Robolectric.application;
        Robolectric.shadowOf(mContext);
        Robolectric.shadowOf(mContext.getResources());
        Robolectric.shadowOf(mContext.getAssets());
    }

}
