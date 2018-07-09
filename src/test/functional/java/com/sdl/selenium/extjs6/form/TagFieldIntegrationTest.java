package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;

public class TagFieldIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagFieldIntegrationTest.class);

    private TagField tagField = new TagField(null, "Select a state:");
    private TagField selectTagField = new TagField(null, "Select/add location:");

    @BeforeClass
    public void startTest() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#form-tag");
        tagField.ready(20);
        Utils.sleep(2000);
    }

//    @Test
//    public void testException() {
//        selectTagField.setBaseCls("blalala");
//        Utils.sleep(1);
//
////        Utils.retry(10, () -> {
////            selectTagField.setValue("test");
////        });
//        RetryUtils.waitIfIsNullOrEmpty(2, () -> selectTagField.getValue());
//        Utils.sleep(1);
//    }

    @Test
    public void tagTest() {
        assertThat(tagField.remove("California"), is(true));
        assertThat(tagField.getAllSelectedValues(), is(new ArrayList<>()));
        assertThat(tagField.select("California", "Alaska"), is(true));
        assertThat(tagField.getValue(), containsString("Alaska"));
        assertThat(tagField.getAllSelectedValues(), is(Arrays.asList("California", "Alaska")));

//        LogEntries logEntries = WebDriverConfig.getDriver().manage().logs().get(LogType.PERFORMANCE);
//        for (LogEntry entry : logEntries) {
//            LOGGER.debug("{}", entry.toString());
//        }
    }

    @Test(dependsOnMethods = "tagTest")
    public void selectValuesTagTest() {
        assertThat(tagField.select("Delaware", "Florida"), is(true));
        assertThat(tagField.getAllSelectedValues(), is(Arrays.asList("California", "Alaska", "Delaware", "Florida")));
    }

    @Test(dependsOnMethods = "selectValuesTagTest")
    public void removeValuesTagTest() {
        assertThat(tagField.remove("Alaska", "Delaware"), is(true));
        assertThat(tagField.getAllSelectedValues(), is(Arrays.asList("California", "Florida")));
    }

    @Test(dependsOnMethods = "removeValuesTagTest")
    public void getAllValuesTagTest() {
        assertThat(tagField.getAllValues(), is(Arrays.asList("Alabama", "Alaska", "Arizona", "Arkansas", "Colorado", "Connecticut", "Delaware", "District of Columbia", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming")));
    }

    @Test(dependsOnMethods = "getAllValuesTagTest")
    public void addTagTest() {
        assertThat(selectTagField.setValue("test"), is(true));
        assertThat(selectTagField.getAllSelectedValues(), is(Arrays.asList("Kansas", "test")));
    }
}
