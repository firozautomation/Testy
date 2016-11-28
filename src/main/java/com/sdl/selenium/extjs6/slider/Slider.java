package com.sdl.selenium.extjs6.slider;

import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slider extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Slider.class);

    public Slider() {
        withClassName("Slider");
        withBaseCls("x-slider");
    }

    public Slider(WebLocator container) {
        this();
        withContainer(container);
    }

    public Slider(WebLocator container, String label) {
        this(container);
        withLabel(label);
    }

    public boolean move(int distance) {
        boolean exists = true;
        WebLocator element = new WebLocator(this).setTag("descendant::*").setClasses("x-slider-thumb");
        if (element.ready()) {
            element.mouseOver();
            boolean done = false;
            int distanceTemp = distance;
            do {
                boolean vertical = getAttributeClass().contains("x-slider-vert");
                int value = Integer.parseInt(getAttribute("aria-valuenow"));
                if (value > distance || value == 0) {
                    distanceTemp = -(value - distance);
                } else if (value < distance) {
                    distanceTemp = distance - value;
                } else {
                    done = true;
                }
                LOGGER.info("not done for: distance={}, distanceTemp={}, value={}", distance, distanceTemp, value);
                if (!done) {
                    if (vertical) {
                        distanceTemp = distanceTemp * 2 + 4;
                    } else {
                        if (distanceTemp == 1 || distanceTemp == 2) {
                            distanceTemp = distanceTemp + (distanceTemp == 1 ? 5 : 2);
                        } else if (distanceTemp == -1 || distanceTemp == -2) {
                            distanceTemp = distanceTemp - (distanceTemp == -1 ? 5 : 2);
                        } else {
                            distanceTemp = distanceTemp * 2 + 1;
                        }
                    }
                    LOGGER.info("before not done for: distance={}, distanceTemp={}, value={}", distance, distanceTemp, value);

                    if (vertical) {
                        LOGGER.info("Vertical->>>>");
                        new Actions(WebDriverConfig.getDriver()).dragAndDropBy(element.getWebElement(), 1, distanceTemp).build().perform();
                    } else {
                        new Actions(WebDriverConfig.getDriver()).dragAndDropBy(element.getWebElement(), distanceTemp, 1).build().perform();
                    }
                    element.mouseOver();
                }
            } while (!done);
        } else {
            LOGGER.warn("The slider for " + getPathBuilder().getLabel() + " has not been selected or is missing");
            exists = false;
        }
        return exists;
    }
}
