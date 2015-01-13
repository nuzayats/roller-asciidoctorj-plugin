package org.nailedtothex.roller;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class AsciidoctorPluginTest {

    AsciidoctorPlugin instance = new AsciidoctorPlugin();

    @Test
    public void testInit() throws Exception {
        instance.init(null);
    }

    @Test
    public void testGetDescription() throws Exception {
        assertThat(instance.getDescription(),
                equalTo("Allows use of Asciidoc formatting to easily generate HTML."));
    }

    @Test
    public void testGetName() throws Exception {
        assertThat(instance.getName(), equalTo("Asciidoc Syntax"));
    }

    @Test
    public void testRender() throws Exception {
        String input = "Hello World!";
        String expected = "<div class=\"paragraph\">\n" +
                "<p>Hello World!</p>\n" +
                "</div>";
        assertThat(instance.render(null, input), equalTo(expected));
    }

}