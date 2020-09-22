package github.hotstu.lu.render.style;

import org.mozilla.javascript.NativeObject;

import java.util.Iterator;

import github.hotstu.lu.render.NativeComponent;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc  目前对css的支持只是简单处理（非常简单, 需要完善）
 * @since 9/21/20
 */
public class SimpleStyleEngine {
    private NativeObject mJss;

    public void attach(NativeObject jss) {
        this.mJss = jss;
    }

    public void applyTo(NativeComponent<?> component) {
        Iterator<String> it = component.getClassManager().it();
        while (it.hasNext()) {
            final String next = it.next();
            if (mJss.containsKey(next)) {
                component.onApplyStyle((NativeObject) mJss.get(next));
            }
        }
    }


}
