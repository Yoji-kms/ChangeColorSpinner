package com.yoji.changecolorspinner;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({Theme.DEFAULT, Theme.GREEN, Theme.BLUE, Theme.BLACK})
public @interface Theme {
    int DEFAULT = 0;
    int GREEN = 1;
    int BLUE = 2;
    int BLACK = 3;
}
