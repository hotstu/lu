package github.hotstu.lu.play;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/18/20
 */
public interface Function<T, R> {
    R apply(T input);
}
