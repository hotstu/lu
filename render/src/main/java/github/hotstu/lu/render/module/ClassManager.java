package github.hotstu.lu.render.module;

import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

import github.hotstu.lu.render.NativeComponent;
import github.hotstu.lu.render.RenderContext;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/21/20
 */
public class ClassManager {
    //TODO support remove style when class removed
    private final TreeSet<String> clazzes = new TreeSet<>();
    private final NativeComponent<?> compoent;

    public ClassManager(NativeComponent<?> component) {
        this.compoent = component;
    }

    public void addClass(String clazz) {
        boolean add = clazzes.add(clazz);
        if (add) {
            update();
        }
    }

    public Iterator<String> it() {
        return clazzes.iterator();
    }

    public void removeClass(String clazz) {
        boolean remove = clazzes.remove(clazz);
        if (remove) {
            update();
        }
    }

    public void setClass(String... clazzes) {
        this.clazzes.clear();
        this.clazzes.addAll(Arrays.asList(clazzes));
        update();
    }

    public void update() {
        RenderContext renderContext = compoent.getRenderContext();
        if (renderContext.getStyle() != null) {
            renderContext.getStyle().applyTo(compoent);
        }
    }

    public boolean hasClass(String clazz) {
        return this.clazzes.contains(clazz);
    }
}

