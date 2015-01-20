package org.nailedtothex.roller;

import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void testSubstringAfter() throws Exception {
        String expected = "imagesdir";
        String input = "attributes.imagesdir";
        assertThat(AsciidoctorPlugin.substringAfter(input, "attributes."), equalTo(expected));
    }

    @Test
    public void testParseOptions() throws Exception {
        Map<String, Object> expected = new HashMap<>();
        Map<String, Object> expectedAttributes = new HashMap<>();
        expectedAttributes.put("imagesdir", "/somewhere/img");
        expectedAttributes.put("backend", "html");
        expected.put("template_dirs", "/tmp/somewhere");
        expected.put("attributes", expectedAttributes);
        
        try (InputStream is = AsciidoctorPluginTest.class.getResourceAsStream("/asciidoctor_for_test.properties");
             Reader r = new InputStreamReader(is, Charset.forName("UTF-8"))) {
            final Map<String, Object> actual = AsciidoctorPlugin.parseOptions(r);
            assertThat(actual, equalTo(expected));
        }
    }
}