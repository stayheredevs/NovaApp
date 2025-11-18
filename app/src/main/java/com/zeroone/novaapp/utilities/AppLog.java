package com.zeroone.novaapp.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class AppLog {

    public static final boolean isDebug   = true;
    public static final boolean testEnv   = false;

    public static final void Log(String tag, String text) {
        if (isDebug) {
            //android.util.Log.i(tag, "-"+message + "");

            //below procedure has null checks and tag limit checks
            if(tag==null){
                tag  = "";
            }
            if(text==null){
                text = "";
            }

            if(tag.length()>22){//new max tag length is 22 otherwise log wont show anything
                tag = pickFirstToNthCharacter(tag,22);
            }

            if (text.length() > 4000) {
                Log.d(tag, "-"+text.substring(0, 4000));
                Log(tag, text.substring(4000));
            } else {
                Log.d(tag, "-"+text);
            }
        }
    }

    final public static String  pickFirstToNthCharacter(String text, int length){
        if(text==null){//must check for null
            return "";
        }else{
            if(text.length()<length){//might input a length which is greater than text length
                return text;
            }else{
                return text.substring(0, length);
            }
        }
    }


    public static void ToastMessage(String msg, Context ctx) {
        if (isDebug) {
            Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
        }

    }
}
