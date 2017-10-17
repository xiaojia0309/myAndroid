package com.example.john.myapplication.tools_used;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/10/16.
 */

public class ActivityController {

    public static List<Activity> acticities = new ArrayList<>();

    public static void addActivity(Activity activity) {

        acticities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        acticities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : acticities) {

            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
