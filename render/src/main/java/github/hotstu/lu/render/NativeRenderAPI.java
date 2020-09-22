package github.hotstu.lu.render;

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


    public NativeComponent<?> createComponent(String tag) {
        return new Frame(context, tag);
    }

    public NativeComponent<?> createText(String text) {
        return new Text(context, text);
    }

    public NativeComponent<?> createComment(String text) {
        return new Comment(context, text);
    }

    public boolean isComment(NativeComponent<?> node) {
        return node instanceof Comment;
    }

    public boolean isText(NativeComponent<?> node) {
        return node instanceof Text;
    }

    public boolean isElement(NativeComponent<?> node) {
        return node instanceof Frame;
    }

    public void setTextContent(NativeComponent<?> node, String text) {
        if (node.children.get(0) instanceof Text) {
            ((Text) node.children.get(0)).setText(text);
        }
    }

    public String getTextContent(NativeComponent<?> node) {
        if (node.children.get(0) instanceof Text) {
            return ((Text) node.children.get(0)).text;
        }
        return null;
    }


    public void debug(String what, Object... component) {
        System.out.println(what + "==>" + component);
    }
}
