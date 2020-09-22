package github.hotstu.lu.render.component;

import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import github.hotstu.lu.render.NativeComponent;
import github.hotstu.lu.render.RenderContext;


/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/10/20
 */
public class Text extends NativeComponent<TextView> {
    public String text;

    public Text(RenderContext context, String text) {
        super(context, null);
        this.text = text;
        this.mView = new TextView(context.getContext());
        this.mView.setText(this.text);
    }

    @Override
    protected void onAttachChild(NativeComponent<?> child) {
        throw new IllegalStateException("should never happen");
    }

    @Override
    protected void onAttachChild(NativeComponent<?> child, int index) {
        throw new IllegalStateException("should never happen");
    }

    @Override
    protected void onDetachChild(NativeComponent<?> child) {
        throw new IllegalStateException("should never happen");
    }

    @Override
    public void onAttached() {
        super.onAttached();
        if (this.mView.getLayoutParams() != null) {
            ((LinearLayout.LayoutParams) this.mView.getLayoutParams()).gravity = Gravity.CENTER;
        }
        this.mView.setText(this.text);

    }

    public void setText(String text) {
        this.text = text;
        mRenderContext.post(() -> {
            this.mView.setText(this.text);
        });

    }
}
