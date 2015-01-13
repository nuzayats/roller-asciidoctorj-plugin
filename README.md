Roller Asciidoctorj Plugin
===========================

### Tested environment

- Roller 5.1.1
- WildFly 8.2.0.Final

### How to use for WildFly

Install Apache Roller

    git clone https://github.com/apache/roller.git
    cd roller; mvn clean install

Build roller-asciidoctorj-plugin

    git clone https://github.com/lbtc-xxx/roller-asciidoctorj-plugin.git
    cd roller-asciidoctorj-plugin; mvn clean package -Droller.version=${roller version here}

Put the plugin jar into your Roller deployment

    cp target/roller-asciidoctorj-plugin-1.0-SNAPSHOT.jar ${ROLLER_HOME}/WEB-INF/lib

Install Asciidoctorj as module

    curl https://gist.githubusercontent.com/lbtc-xxx/03ba4be0a78921241d62/raw/53bf9869a4367ab56aaaab77494ed0a18ce743d8/module.xml > /tmp/module.xml
    ${WILDFLY_HOME}/bin/jboss-cli.sh --connect
    module add \
     --name=org.asciidoctor \
     --module-xml=/tmp/module.xml \
     --resources=${PLUGIN_HOME}/target/dependency/asciidoctorj-1.5.2.jar,${PLUGIN_HOME}/target/dependency/jcommander-1.35.jar,${PLUGIN_HOME}/target/dependency/jruby-complete-1.7.16.1.jar \
     --resource-delimiter=,

Add dependency to Asciidoctorj at ${ROLLER_HOME}/META-INF/MANIFEST.MF (make sure the file is ended with a line break)

    Dependencies: org.asciidoctor

Add FQCN of the plugin into your roller-custom.properties

    plugins.page=\
    org.nailedtothex.roller.AsciidoctorPlugin \
    ,org.apache.roller.weblogger.business.plugins.entry.ConvertLineBreaksPlugin \
    ,org.apache.roller.weblogger.business.plugins.entry.ObfuscateEmailPlugin \
    ,org.apache.roller.weblogger.business.plugins.entry.SmileysPlugin

### How to use for except WildFly

I've not tested yet but just copy dependencies into your Roller deployment instead of module installation then it would work.

    cp ${PLUGIN_HOME}/target/dependency/asciidoctorj-1.5.2.jar \
       ${PLUGIN_HOME}/target/dependency/jcommander-1.35.jar \
       ${PLUGIN_HOME}/target/dependency/jruby-complete-1.7.16.1.jar \
       ${ROLLER_HOME}/WEB-INF/lib

### Notes

- More information can be found at [author's blog](http://www.nailedtothex.org/roller/)