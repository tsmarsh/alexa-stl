package com.tailoredshapes.stl.alexa;

import org.uispec4j.Button;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.FileChooserHandler;
import org.uispec4j.interception.MainClassAdapter;
import org.uispec4j.interception.WindowInterceptor;

import java.io.File;
import java.nio.file.Files;

public class AlexaIntegrationTest extends UISpecTestCase{
    @Override
    public void setUp() throws Exception {
        super.setUp();
        setAdapter(new MainClassAdapter(Alexa.class));
    }

    public void testHappyPath() throws Exception {
        String stls = this.getClass().getResource("/stls").getPath();
        File saveDir = Files.createTempDirectory("integration-test").toFile();

        Window mainWindow = getMainWindow();
        Button pickDirectory = mainWindow.getButton();

        assertEquals("Pick Directory", pickDirectory.getLabel());

        WindowInterceptor.init(pickDirectory.triggerClick()).process(
                FileChooserHandler.init().assertAcceptsDirectoriesOnly().select(stls)
        ).run();

        mainWindow.getTextBox("xValue").setText("100.0", true);
        mainWindow.getTextBox("yValue").setText("100.0", true);
        mainWindow.getTextBox("zValue").setText("100.0", true);

        Button save = mainWindow.getButton("Save");

        WindowInterceptor.init(save.triggerClick()).process(
                FileChooserHandler.init()
                        .assertAcceptsDirectoriesOnly()
                        .select(saveDir.getAbsolutePath())
        ).run();

        assertEquals(1, saveDir.list().length);

        saveDir.deleteOnExit();
    }
}
