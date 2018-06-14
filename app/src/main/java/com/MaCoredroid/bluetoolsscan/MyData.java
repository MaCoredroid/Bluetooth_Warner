package com.MaCoredroid.bluetoolsscan;

import android.app.Activity;
import android.app.Application;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MyData extends Activity {
    public static boolean a=false;
    public static boolean b=false;
    public static boolean c=false;
    public static void seta(boolean s)
    {a=s;}
    public static void setb(boolean s)
    {b=s;}
    public static void setc(boolean s)
    {c=s;}
    public static boolean geta() {
        return a;
    }
    public static boolean getb() {
        return b;
    }
    public static boolean getc() {
        return c;
    }

}