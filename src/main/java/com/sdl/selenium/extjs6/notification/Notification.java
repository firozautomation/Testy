package com.sdl.selenium.extjs6.notification;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.RetryUtils;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Notification extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Notification.class);

    public Notification() {
        setClasses("msg", "x-border-box");
        setVisibility(true);
        WebLocator c = new WebLocator().setClasses("msg-container");
        setContainer(c);
    }

    public Notification(String msg) {
        this();
        getPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
        setText(msg);
    }

    public Notification(String msg, SearchType... searchTypes) {
        this(msg);
        setSearchTextType(searchTypes);
    }

    public void close() {
        WebLocator close = new WebLocator(this).setClasses("x-tool-close");
        Utils.sleep(350);
        RetryUtils.retry(3, () -> {
            this.waitToRender(300L, false);
            return close.click() || !this.waitToRender(100L, false);
        });
    }
}