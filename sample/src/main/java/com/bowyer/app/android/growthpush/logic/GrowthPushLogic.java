package com.bowyer.app.android.growthpush.logic;


import com.bowyer.app.android.growthpush.BuildConfig;
import com.bowyer.app.android.growthpush.ui.activity.MainActivity;
import com.growthpush.GrowthPush;
import com.growthpush.model.Environment;

import android.content.Context;
import android.content.Intent;

import com.bowyer.app.growthpushwrapper.ReceiveHandler;

public class GrowthPushLogic {

    public static void initGrowthPush(Context context){
        GrowthPush
                .getInstance()
                .initialize(context, 4615, "9NG9j67rdRBuaNwt7dg3akwFgqtWMLcs",
                        BuildConfig.DEBUG ? Environment.development : Environment.production, true).register("1049950925077");
        GrowthPush.getInstance().trackEvent("Launch");
        GrowthPush.getInstance().setDeviceTags();
        GrowthPush.getInstance().setReceiveHandler(new ReceiveHandler(new Intent(context,MainActivity.class)));
    }
}
