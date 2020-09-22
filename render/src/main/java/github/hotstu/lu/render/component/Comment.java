package github.hotstu.lu.render.component;

import android.view.View;

import github.hotstu.lu.render.NativeComponent;
import github.hotstu.lu.render.RenderContext;


/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/14/20
 */
public class Comment extends NativeComponent<View> {

    public  String text;

    public Comment(RenderContext context, String text) {
        super(context, "!");
        this.text = text;
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
}
