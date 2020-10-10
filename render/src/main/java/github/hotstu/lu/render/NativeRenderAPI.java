package github.hotstu.lu.render;

import github.hotstu.lu.render.annotation.RenderAPI;
import github.hotstu.lu.render.component.Comment;
import github.hotstu.lu.render.component.Frame;
import github.hotstu.lu.render.component.Text;


/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/10/20
 */
public class NativeRenderAPI {

    private final RenderContext context;

    public NativeRenderAPI(RenderContext context) {
        this.context = context;
    }

    @RenderAPI
    public NativeComponent<?> createComponent(String tag) {
        return new Frame(context, tag);
    }

    @RenderAPI
    public NativeComponent<?> createText(String text) {
        return new Text(context, text);
    }

    @RenderAPI
    public NativeComponent<?> createComment(String text) {
        return new Comment(context, text);
    }

    @RenderAPI
    public boolean isComment(NativeComponent<?> node) {
        return node instanceof Comment;
    }

    @RenderAPI
    public boolean isText(NativeComponent<?> node) {
        return node instanceof Text;
    }

    @RenderAPI
    public boolean isElement(NativeComponent<?> node) {
        return node instanceof Frame;
    }

    @RenderAPI
    public void setTextContent(NativeComponent<?> node, String text) {
        if (node.children.get(0) instanceof Text) {
            ((Text) node.children.get(0)).setText(text);
        }
    }

    @RenderAPI
    public String getTextContent(NativeComponent<?> node) {
        if (node.children.get(0) instanceof Text) {
            return ((Text) node.children.get(0)).text;
        }
        return null;
    }

    @RenderAPI
    public void debug(String what, Object... component) {
        System.out.println(what + "==>" + component);
    }
}
