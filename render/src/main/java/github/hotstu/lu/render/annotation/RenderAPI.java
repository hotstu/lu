package github.hotstu.lu.render.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 10/10/20
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface RenderAPI {

}
