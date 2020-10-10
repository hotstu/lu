package github.hotstu.lu.render;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 10/10/20
 */
public class ComponentRegistry {
    private static final ComponentRegistry ourInstance = new ComponentRegistry();

    static ComponentRegistry getInstance() {
        return ourInstance;
    }

    private ComponentRegistry() {
    }
}
