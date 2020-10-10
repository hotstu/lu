package github.hotstu.lu.render.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import github.hotstu.lu.render.NativeComponent;
import github.hotstu.lu.render.annotation.WidgetDef;


/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/14/20
 */
@WidgetDef
public class LuLinearLayout extends LinearLayout {
    NativeComponent<?> component;
    public LuLinearLayout(Context context) {
        super(context);
    }

    public void setComponent(NativeComponent<?> component) {
        this.component = component;
    }

    public LuLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LuLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
    }

    @Override
    public String toString() {
        return "LuLinearLayout{" +
                "component=" + component +
                '}';
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);
    }
}
