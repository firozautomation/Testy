package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FieldSetTest {
    public static ExtJsComponent container = new ExtJsComponent(By.classes("container"));

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new FieldSet(), "//fieldset[contains(concat(' ', @class, ' '), ' x-fieldset ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new FieldSet(container, "FieldSetText"), "//*[contains(concat(' ', @class, ' '), ' container ')]//fieldset[contains(concat(' ', @class, ' '), ' x-fieldset ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked')) and count(.//*[normalize-space(text())='FieldSetText']) > 0]"},
                {new FieldSet(container).setText("FieldSetText"), "//*[contains(concat(' ', @class, ' '), ' container ')]//fieldset[contains(concat(' ', @class, ' '), ' x-fieldset ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked')) and count(.//*[normalize-space(text())='FieldSetText']) > 0]"},
                {new FieldSet(container, "FieldSetCls", "FieldSetText"), "//*[contains(concat(' ', @class, ' '), ' container ')]//fieldset[contains(concat(' ', @class, ' '), ' x-fieldset ') and contains(concat(' ', @class, ' '), ' FieldSetCls ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked')) and count(.//*[normalize-space(text())='FieldSetText']) > 0]"},

                {new FieldSet(By.container(container), By.text("FieldSetText")), "//*[contains(concat(' ', @class, ' '), ' container ')]//fieldset[contains(concat(' ', @class, ' '), ' x-fieldset ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked')) and count(.//*[normalize-space(text())='FieldSetText']) > 0]"},
                {new FieldSet(By.container(container), By.classes("FieldSetCls"), By.text("FieldSetText")), "//*[contains(concat(' ', @class, ' '), ' container ')]//fieldset[contains(concat(' ', @class, ' '), ' x-fieldset ') and contains(concat(' ', @class, ' '), ' FieldSetCls ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked')) and count(.//*[normalize-space(text())='FieldSetText']) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(FieldSet fieldSet, String expectedXpath) {
        Assert.assertEquals(fieldSet.getPath(), expectedXpath);
    }
}
