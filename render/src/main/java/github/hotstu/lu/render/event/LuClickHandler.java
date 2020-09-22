package github.hotstu.lu.render.event;

import android.view.View;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;

import github.hotstu.lu.render.R;
import github.hotstu.lu.render.RenderContext;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/21/20
 */
public class LuClickHandler implements View.OnClickListener {

    final String event;
    final RenderContext context;
    final Function func;

    public LuClickHandler(final String event, final Function func, final RenderContext context) {
        this.event = event;
        this.func = func;
        this.context = context;
    }

    public static void removeEventListener(View v, String event, Function func) {
        if (v.getTag(R.id.tag_event_click) != null) {
            v.setOnClickListener(null);
            v.setTag(R.id.tag_event_click, null);

        }
    }

    public LuClickHandler attach(View v) {
        v.setTag(R.id.tag_event_click, this);
        v.setOnClickListener(this);
        return this;
    }

    @Override
    public void onClick(View v) {
        Event e = new Event();
        e.type = event;
        context.postWorker(() -> func.call(Context.getCurrentContext(),
                context.getContextScope(), null, new Object[]{e}));
    }
}
