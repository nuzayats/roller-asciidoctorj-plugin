package org.nailedtothex.roller;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.business.plugins.entry.WeblogEntryPlugin;
import org.apache.roller.weblogger.pojos.Weblog;
import org.apache.roller.weblogger.pojos.WeblogEntry;
import org.asciidoctor.Asciidoctor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

public class AsciidoctorPlugin implements WeblogEntryPlugin {
    private static final Logger log = LoggerFactory.getLogger(AsciidoctorPlugin.class);
    private static final Asciidoctor ASCIIDOCTOR = Asciidoctor.Factory.create();
    private static final Map<String, Object> EMPTY_MAP = Collections.emptyMap();
    private static final String NAME = "Asciidoc Syntax";
    private static final String DESCRIPTION = StringEscapeUtils.escapeJavaScript(
            "Allows use of Asciidoc formatting to easily generate HTML.");

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
        log.debug("init: {}", weblog);
    }

    @Override
    public String render(WeblogEntry weblogEntry, String s) {
        log.debug("render(): weblogEntry={}, s={}", weblogEntry, s);
        final String result = ASCIIDOCTOR.convert(s, EMPTY_MAP);
        log.debug("render(): result={}", result);
        return result;
    }
}