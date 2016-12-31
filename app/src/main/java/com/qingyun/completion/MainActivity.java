package com.qingyun.completion;

import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.qingyun.completion.ui.CEditViewGroup;
import com.qingyun.completion.ui.CTextView;
import com.qingyun.completion.utils.BlankTextUtils;
import com.qingyun.completion.utils.LogUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CTextView.OnViewSizeChangeListener {

    private final String textString = "&&&&&&在自定义&&&&&&中，当要自己view画文本时，需要&&&&&&做不同处理如打点等，这时&&&&&&该方法计算文本的宽高。&&&&&&,&&&&&&在自定义&&&&&&中，当要自己view画文本时，需要&&&&&&做不同处理如打点等，这时&&&&&&该方法计算文本的宽高。&&&&&&";
    private final String replaceChar = "&&&&&&";
    @BindView(R.id.completionBlank)
    CTextView completionBlank;
    @BindView(R.id.editBlankArray)
    CEditViewGroup completionEditViewGroup;
    private BlankTextUtils completionBlankTextUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        completionBlank.setTextSize(14);
        completionBlankTextUtils = new BlankTextUtils(textString, replaceChar, completionBlank.getPaint(), 120);
        completionBlank.setText(completionBlankTextUtils.transferToSpannableStringBuilder());
        completionBlank.setLisener(this);
    }

    public void addEditBox() {
        Map<Integer, RectF> blankRectMap = completionBlankTextUtils.getBlankRectMap();
        for (Integer key : blankRectMap.keySet()) {
            completionEditViewGroup.addView(newEditText(key, blankRectMap.get(key)));
        }
        completionEditViewGroup.requestLayout();
    }

    public EditText newEditText(int id, RectF rectF) {
        EditText editText = new EditText(this);
        editText.setId(id);
        editText.setTextSize(14);
        editText.setSingleLine();
        editText.setGravity(Gravity.CENTER);
        editText.setTextColor(Color.BLACK);
        editText.setLayoutParams(new FrameLayout.LayoutParams(Math.round(rectF.width()), Math.round(editText.getPaint().getFontMetrics().bottom - editText.getPaint().getFontMetrics().top)));
        editText.setTag(rectF);
        editText.setBackgroundColor(Color.BLUE);
        return editText;
    }

    @Override
    public void onMeasuerSize(int width, int height) {
        LogUtils.v("onMeasuerSize:" + width + "," + height);
        completionEditViewGroup.setLayoutParams(new FrameLayout.LayoutParams(width, height));
        addEditBox();
    }

}
