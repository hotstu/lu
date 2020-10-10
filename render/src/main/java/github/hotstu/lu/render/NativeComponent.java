package github.hotstu.lu.render;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.CallSuper;

import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import github.hotstu.lu.render.event.LuClickHandler;
import github.hotstu.lu.render.module.ClassManager;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 7/17/20
 */
public abstract class NativeComponent<T extends View> {
    public String tag;
    private NativeComponent<?> parent;
    public final List<NativeComponent<?>> children = new LinkedList<>();
    public final Map<String, String> attrs = new HashMap<>();
    protected T mView;
    protected RenderContext mRenderContext;
    protected ClassManager classManager;


    public NativeComponent(RenderContext context, String tag) {
        this.mRenderContext = context;
        this.tag = tag;
        this.classManager = new ClassManager(this);
    }

    @Override
    public String toString() {
        return "NativeComponent{" +
                "tag='" + tag + '\'' +
                ", attrs=" + attrs +
                '}';
    }

    public void setAttribute(String attr, String value) {
        if ("class".equals(attr)) {
            classManager.setClass(value.split(" "));
        } else {
            attrs.put(attr, value);
        }
    }

    public T getView() {
        return mView;
    }

    public ClassManager getClassManager() {
        return classManager;
    }

    public RenderContext getRenderContext() {
        return mRenderContext;
    }

    public boolean addEventListener(String event, Function func) {
        if ("click".equals(event)) {
            new LuClickHandler(event, func, mRenderContext).attach(mView);
            return true;
        }
        return false;
    }

    public boolean removeEventListener(String event, Function func) {
        if ("click".equals(event)) {
            LuClickHandler.removeEventListener(mView, event, func);
            return true;
        }
        return false;
    }

    protected abstract void onAttachChild(NativeComponent<?> child);

    protected abstract void onAttachChild(NativeComponent<?> child, int index);

    protected abstract void onDetachChild(NativeComponent<?> child);

    @CallSuper
    public void onAttached() {
        classManager.update();
    }

    public void insertBefore(NativeComponent<?> child, NativeComponent<?> reference) {
        if (reference == null) {
            appendChild(child);
            return;
        }
        int i = children.indexOf(reference);
        if (i >= 0) {
            children.add(i, child);
            child.setParent(this);
            this.onAttachChild(child, i);
        }
    }

    public void removeChild(NativeComponent<?> child) {
        child.setParent(null);
        children.remove(child);
        this.onDetachChild(child);
    }

    public void appendChild(NativeComponent<?> child) {
        child.setParent(this);
        children.add(child);
        this.onAttachChild(child);
    }

    public NativeComponent<?> findNextSibling(NativeComponent<?> child) {
        int i = children.indexOf(child);
        if (i + 1 > children.size()) {
            return children.get(i + 1);
        }
        return null;
    }

    public NativeComponent<?> findParent() {
        return parent;
    }

    public void setParent(NativeComponent<?> parent) {
        this.parent = parent;
    }

    protected int dp2px(int dp) {
        float density = getRenderContext().getContext().getResources().getDisplayMetrics().density;
        return (int) (density * dp);
    }

    protected int parseGravity(String gravity) {
        if ("CENTER".equalsIgnoreCase(gravity)) {
            return Gravity.CENTER;
        } else if("TOP".equalsIgnoreCase(gravity)){
            return Gravity.TOP;
        } else if("BOTTOM".equalsIgnoreCase(gravity)) {
            return Gravity.BOTTOM;
        } else {
            return Gravity.NO_GRAVITY;
        }
    }

    protected int parseOrientation(String ori) {
        if ("HORIZONTAL".equalsIgnoreCase(ori)) {
            return LinearLayout.HORIZONTAL;
        } else if ("VERTICAL".equalsIgnoreCase(ori)) {
            return LinearLayout.VERTICAL;
        } else {
            return LinearLayout.VERTICAL;
        }
    }

    @CallSuper
    public void onApplyStyle(NativeObject o) {
        Iterator<?> iterator = o.keySet().iterator();
        while (iterator.hasNext()) {
            String next = (String) iterator.next();
            String value = (String) o.get(next);
            switch (next) {
                case "width":
                    if (mView.getLayoutParams() != null) {
                        mView.getLayoutParams().width = dp2px(Integer.parseInt(value));
                    }
                    break;
                case "height":
                    if (mView.getLayoutParams() != null) {
                        mView.getLayoutParams().height = dp2px(Integer.parseInt(value));
                    }
                    break;
                case "background":
                    int i = Color.parseColor(value);
                    mView.setBackgroundColor(i);
            }
        }
    }
}
