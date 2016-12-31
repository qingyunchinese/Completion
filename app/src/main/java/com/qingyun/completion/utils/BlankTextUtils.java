package com.qingyun.completion.utils;

import android.graphics.Paint;
import android.graphics.RectF;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

/**
 * 作者： qingyun on 16/12/4.
 * 邮箱：419254872@qq.com
 * 版本：v1.0
 */
public class BlankTextUtils implements BlankSpan.OnBlankSpanInitRectListener{
    private String text = "";
    private String replaceChar = "";
    private Paint paint=null;
    private int editWidth=0;
    private final String space=" ";
    private Map<Integer,RectF> blankRectMap=new HashMap<>();

    /**
     *
     * @param text        原字符串
     * @param replaceChar 替换字符串
     * @param paint  textView 画笔
     * @param editWidth  设定的editText的宽度
     */
    public BlankTextUtils(String text, String replaceChar, Paint paint, int editWidth) {
        this.text = text;
        this.replaceChar = replaceChar;
        this.paint=paint;
        this.editWidth=editWidth;
    }

    public SpannableStringBuilder transferToSpannableStringBuilder() {
        if (TextUtils.isEmpty(text)) {
            return new SpannableStringBuilder(text);
        }
        if (TextUtils.isEmpty(replaceChar)) {
            return new SpannableStringBuilder(text);
        }
        if (text.indexOf(replaceChar) == -1) {
            return new SpannableStringBuilder(text);
        }
        String spaceChar = "";
        float singleSpaceWidth=paint.measureText(space,0,space.length());
        int count=Math.round(editWidth/singleSpaceWidth);
        for (int i = 0; i < count; i++) {
            spaceChar = spaceChar +space;
        }
        String[] targetStrArray = text.split(replaceChar);
        text = text.replace(replaceChar, spaceChar);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        int start = 0;
        int end = 0;
        int editBoxIndex=0;
        if (text.indexOf(spaceChar) == 0) {
            start = 0;
            end = spaceChar.length();
            BlankSpan blankSpan = new BlankSpan(this,editBoxIndex++);
            spannableStringBuilder.setSpan(blankSpan, start, end, SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (int i=0;i<targetStrArray.length-1;i++) {
            start = start + targetStrArray[i].length();
            end = start + spaceChar.length();
            BlankSpan blankSpan = new BlankSpan(this,editBoxIndex++);
            spannableStringBuilder.setSpan(blankSpan, start, end, SPAN_EXCLUSIVE_EXCLUSIVE);
            LogUtils.v(""+(end-start));
            start = end;
        }
        if (text.lastIndexOf(spaceChar) == text.length()-spaceChar.length()) {
            BlankSpan blankSpan = new BlankSpan(this,editBoxIndex++);
            spannableStringBuilder.setSpan(blankSpan, text.length()-spaceChar.length(), text.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableStringBuilder;
    }

    @Override
    public void onBlankRectF(int editBoxIndex,RectF rectF) {
        blankRectMap.put(editBoxIndex,rectF);
    }

    public Map<Integer,RectF> getBlankRectMap(){
        return blankRectMap;
    }
}
