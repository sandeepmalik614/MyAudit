package com.myaudit.utils;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class AppUtils {

    public static String generateLightRenadomNumber() {
        Random r = new Random();
        int red = r.nextInt(223 - 54 + 1) + 54;
        int green = r.nextInt(140 - 29 + 1) + 29;
        int blue = r.nextInt(192 - 172 + 1) + 172;

        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.rgb(red, green, blue));
        String color = String.format("#%02x%02x%02x", red, green, blue);
        color = color.replace("android.graphics.drawable.GradientDrawable@", "");
        return color;
    }

    public static void clearAllIntent(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static String getMonth(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMMM,yyyy");
        return df.format(c);
    }


}
