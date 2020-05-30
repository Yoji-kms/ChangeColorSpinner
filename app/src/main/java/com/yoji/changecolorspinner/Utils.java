package com.yoji.changecolorspinner;

import android.app.Activity;
import android.content.Intent;

public class Utils {
    private static int switchingTheme;

    public static void changeToTheme(Activity activity, int theme) {
        switchingTheme = theme;
        activity.finish();

        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (switchingTheme) {
            default:
            case Theme.DEFAULT:
                activity.setTheme(R.style.AppTheme);
                break;
            case Theme.GREEN:
                activity.setTheme(R.style.GreenTheme);
                break;
            case Theme.BLUE:
                activity.setTheme(R.style.BlueTheme);
                break;
            case Theme.BLACK:
                activity.setTheme(R.style.BlackTheme);
                break;
        }
    }

    public static int getThemeResId() {
        return switchingTheme;
    }
}
