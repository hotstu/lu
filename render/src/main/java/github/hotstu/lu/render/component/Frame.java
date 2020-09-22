package github.hotstu.lu.render.component;

import android.widget.LinearLayout;

import org.mozilla.javascript.NativeObject;

import java.util.Iterator;

import github.hotstu.lu.render.NativeComponent;
import github.hotstu.lu.render.RenderContext;
import github.hotstu.lu.render.widget.LuLinearLayout;


/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/10/20
 */
public class Frame extends NativeComponent<LinearLayout> {
    public Frame(RenderContext context, LinearLayout view) {
        super(context, "ROOT");
        this.mView = view;
    }

    public Frame(RenderContext context, String tag) {
        super(context, tag);
        LuLinearLayout mView = new LuLinearLayout(context.getContext());
        mView.setOrientation(LinearLayout.VERTICAL);
        mView.setComponent(this);
        //Random random = new Random();
        //int[] colors = new int[]{Color.GREEN, Color.RED, Color.BLUE};


        //mView.setBackgroundColor(colors[random.nextInt(colors.length)]);
        //mView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.mView = mView;

    }

    @Override
    public void setAttribute(String attr, String value) {
        super.setAttribute(attr, value);
    }

    @Override
    protected void onAttachChild(NativeComponent<?> child) {
        //TODO batch update view
        if (child.getView() != null) {
            mRenderContext.post(() -> {
                this.mView.addView(child.getView());
                child.onAttached();
            });
        }
    }

    @Override
    protected void onAttachChild(NativeComponent<?> child, int index) {
        //TODO batch update view
        if (child.getView() != null) {
            mRenderContext.post(() -> {
                this.mView.addView(child.getView(), index);
                child.onAttached();
            });
        }

    }

    @Override
    protected void onDetachChild(NativeComponent<?> child) {
        mRenderContext.post(() -> {
            this.mView.removeView(child.getView());
        });
    }

    @Override
    public void onAttached() {
        super.onAttached();
    }



    @Override
    public void onApplyStyle(NativeObject o) {
        super.onApplyStyle(o);
        Iterator<?> iterator = o.keySet().iterator();
        while (iterator.hasNext()) {
            String next = (String) iterator.next();
            String value = (String) o.get(next);
            switch (next) {
                case "orientation":
                    mView.setOrientation(parseOrientation(value));
                    break;
                case "gravity":
                    mView.setGravity(parseGravity(value));
                    break;
            }
        }
    }
}
