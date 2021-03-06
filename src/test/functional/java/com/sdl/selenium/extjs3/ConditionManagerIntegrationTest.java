package com.sdl.selenium.extjs3;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.conditions.Condition;
import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.RenderSuccessCondition;
import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.extjs3.conditions.MessageBoxFailCondition;
import com.sdl.selenium.extjs3.conditions.MessageBoxSuccessCondition;
import com.sdl.selenium.extjs3.panel.Panel;
import com.sdl.selenium.extjs3.window.MessageBox;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.form.TextField;
import com.sdl.selenium.web.link.WebLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class ConditionManagerIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionManagerIntegrationTest.class);

    private Panel panel = new Panel("Condition Manager");
    private Button expect1Button = new Button(panel, "Expect1");
    private Button expect2Button = new Button(panel, "Expect2");
    private Button expect3Button = new Button(panel, "Expect3");

    @BeforeTest
    public void before() {
        TextField email = new TextField().setId("email");
        TextField pass = new TextField().setId("password");
        com.sdl.selenium.bootstrap.button.Button login = new com.sdl.selenium.bootstrap.button.Button().setId("loginButton");
        email.setValue("eu@fast.com");
        pass.setValue("eu.pass");
        login.click();
        WebLink link = new WebLink(null, "ExtJs Demo", SearchType.CONTAINS);
        link.click();
        panel.waitToRender(2000L, false);
    }

    private Condition doClick(Button button) {
        button.click();

        ConditionManager conditionManager = new ConditionManager(10000);
        conditionManager.add(new MessageBoxSuccessCondition("Expect1 button was pressed"));
        conditionManager.add(new MessageBoxFailCondition("Expect2 button was pressed"));
        conditionManager.add(new MessageBoxFailCondition("Expect3 button was pressed"));

        Condition condition = conditionManager.execute();
        //RenderCondition renderCondition = (RenderCondition)condition;

        return condition;
    }

    @Test
    public void conditionManagerTest() {
        Condition condition = doClick(expect1Button);

        if (condition.isFail()) {
            LOGGER.warn(condition.getResultMessage());
        }
        assertTrue(condition.isSuccess());

        MessageBox.pressOK();
    }

    @Test
    public void conditionManagerTest1() {
        expect2Button.click();
        ConditionManager conditionManager = new ConditionManager(10000);
        conditionManager.add(new MessageBoxSuccessCondition("Expect2 button was pressed"));
        conditionManager.add(new MessageBoxFailCondition("Expect1 button was pressed"));
        conditionManager.add(new MessageBoxFailCondition("Expect3 button was pressed"));
        assertTrue(conditionManager.execute().isSuccess());
        MessageBox.pressOK();
    }

    @Test
    public void conditionManagerTest2() {
        expect3Button.click();
        ConditionManager conditionManager = new ConditionManager(10000);
        conditionManager.add(new MessageBoxSuccessCondition("Are you sure you want to do that?"));
        conditionManager.add(new MessageBoxFailCondition("Expect2 button was pressed"));
        conditionManager.add(new MessageBoxFailCondition("Expect1 button was pressed"));
        assertTrue(conditionManager.execute().isSuccess()); //&& new MessageBox("Are you sure you want to do that?").pressYES());
        MessageBox.pressYes();
    }

    @Test
    public void conditionManagerTest3() {
        ConditionManager conditionManager = new ConditionManager(100);
        conditionManager.add(new RenderSuccessCondition(new Button(null, "Test")));
        assertTrue(conditionManager.execute().isTimeout());
    }

    /*@Test
    public void conditionManagerContainsMessage() {
        showYesAlert.click();

        ConditionManager conditionManager = new ConditionManager(10000);
        conditionManager.add(new MessageBoxSuccessCondition("Would you like to save your changes?", true));
        String msg = "";
        if(conditionManager.execute().isSuccess()){
            msg = MessageBox.pressYes();
        }
        Assert.assertEquals(msg, "You are closing a tab that has unsaved changes.\n" +
                "Would you like to save your changes?");
    }

    @Test
    public void messageBoxTest() {
        showAlert.click();
        MessageBox.pressOK();
        //Assert.assertTrue(new MessageBox("Changes saved successfully.").pressOk());
        showAlert.click();
        Assert.assertEquals(MessageBox.pressOK(), "Changes saved successfully.");
    }*/
}
