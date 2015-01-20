package org.nailedtothex.roller;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.business.plugins.entry.WeblogEntryPlugin;
import org.apache.roller.weblogger.pojos.Weblog;
import org.apache.roller.weblogger.pojos.WeblogEntry;
import org.asciidoctor.Asciidoctor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AsciidoctorPlugin implements WeblogEntryPlugin {
    private static final Logger log = LoggerFactory.getLogger(AsciidoctorPlugin.class);
    private static final Asciidoctor ASCIIDOCTOR = Asciidoctor.Factory.create();
    private static final String NAME = "Asciidoc Syntax";
    private static final String DESCRIPTION = StringEscapeUtils.escapeJavaScript(
            "Allows use of Asciidoc formatting to easily generate HTML.");
    private static final String OPTIONS_PATH = "/asciidoctor.properties";
    private static final String ATTRIBUTES = "attributes";
    private static final String ATTRIBUTES_KEY_PREFIX = ATTRIBUTES + ".";
    Map<String, Object> options;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public void init(Weblog weblog) throws WebloggerException {
        log.debug("init: {}, {}", weblog, this);
        options = instantiateOptions(new Object[]{weblog.getHandle()});
        log.debug("options: {}", options);
    }

    @Override
    public String render(WeblogEntry weblogEntry, String s) {
        log.debug("render(): weblogEntry={}, s={}", weblogEntry, s);
        final String result = ASCIIDOCTOR.convert(s, options);
        log.debug("render(): result={}", result);
        return result;
    }

    static Map<String, Object> instantiateOptions(Object[] args) {
        try (InputStream is = AsciidoctorPlugin.class.getResourceAsStream(OPTIONS_PATH)) {
            if (is == null) {
                log.debug("no {} was present", OPTIONS_PATH);
                return Collections.emptyMap();
            }
            try (Reader r = new InputStreamReader(is, Charset.forName("UTF-8"))) {
                return Collections.unmodifiableMap(parseOptions(r, args));
            }
        } catch (IOException e) {
            log.debug("error during loading " + OPTIONS_PATH, e);
            return Collections.emptyMap();
        }
    }

    static Map<String, Object> parseOptions(Reader r, Object[] args) throws IOException {
        Properties props = new Properties();
        props.load(r);
        Map<String, Object> options = new HashMap<>(props.size());
        Map<String, Object> attributes = new HashMap<>(props.size());
        for (String key : props.stringPropertyNames()) {
            final String value = new MessageFormat(props.getProperty(key)).format(args);
            if (key.startsWith(ATTRIBUTES_KEY_PREFIX)) {
                attributes.put(substringAfter(key, ATTRIBUTES_KEY_PREFIX), value);
                continue;
            }
            options.put(key, value);
        }
        options.put(ATTRIBUTES, attributes);
        return options;
    }

    static String substringAfter(String str, String separator) {
        int pos = str.indexOf(separator);
        return str.substring(pos + separator.length());
    }
}