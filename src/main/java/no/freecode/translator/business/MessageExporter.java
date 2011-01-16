/**
 *  Project: translator
 *  Created: Jan 9, 2011
 *  Copyright: 2011, FreeCode AS
 *
 *  This file is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published
 *  by the Free Software Foundation; version 3.
 */
package no.freecode.translator.business;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import no.freecode.translator.domain.Message;
import no.freecode.translator.domain.MessageLocale;
import no.freecode.translator.domain.MessageSection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * For now, we'll just dump all the data to .properties files.
 * 
 * @author Reidar Øksnevad <reidar.oksnevad@freecode.no>
 */
@Repository
@Transactional
public class MessageExporter {
    
    private static final Logger logger = Logger.getLogger(MessageExporter.class);
    
    public void dumpToFiles() throws IOException {
        Dumper dumper = new Dumper();
        
        for (MessageSection section : MessageSection.findAllMessageSections()) {

            Set<Message> messages = section.getMessages();
            
            if (messages != null) {
                for (Message message : messages) {
                    for (Entry<MessageLocale, String> entry : message.getTranslations().entrySet()) {
                        dumper.append(message.getProperty(), entry.getValue(), entry.getKey());
                    }
                }

                // Flush the section, and prepend a description (comment).
                dumper.flushSection(section.getDescription());
            }
        }
        
        dumper.writeFiles();
    }
    
    protected class Dumper {
        
        private Map<MessageLocale, StringBuilder> sectionBuffer = new HashMap<MessageLocale, StringBuilder>();
        private Map<MessageLocale, StringBuilder> mainBuffer = new HashMap<MessageLocale, StringBuilder>();


        public void append(String property, String text, MessageLocale locale) {
            append(sectionBuffer, locale, property + " = " + StringEscapeUtils.escapeJava(text), true);
        }
        
        /**
         * Move everything in the section buffer to the main buffer, thus
         * creating a new section with a description comment, etc.
         * 
         * @param description a descriptive comment of the section-to-be-dumped.
         */
        public void flushSection(String description) {
            for (Entry<MessageLocale, StringBuilder> entry : sectionBuffer.entrySet()) {
                MessageLocale locale = entry.getKey();
                StringBuilder builder = entry.getValue();
                builder.insert(0, "#\n# " + description + "\n#\n");

                append(mainBuffer, locale, builder.toString(), true);
            }

            sectionBuffer = new HashMap<MessageLocale, StringBuilder>();
        }


        private void append(Map<MessageLocale, StringBuilder> buffer, MessageLocale locale, String string, boolean newLine) {
            StringBuilder builder = buffer.get(locale);
            if (builder == null) {
                builder = new StringBuilder();
                buffer.put(locale, builder);
            }

            builder.append(string);
            if (newLine) {
                builder.append('\n');
            }
        }

        public void writeFiles() throws IOException {

            // TODO: move folders and files to configuration
//            String directory = "/tmp/krigsgraver_messages/" + new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss").format(new Date());
            String directory = "/tmp/krigsgraver_messages/";
            String filePrefix = "messages";

            File directoryFile = new File(directory);
            if (!directoryFile.exists()) {
                FileUtils.forceMkdir(directoryFile);
            }
            
            for (Entry<MessageLocale, StringBuilder> entry : mainBuffer.entrySet()) {
                MessageLocale locale = entry.getKey();
                StringBuilder builder = entry.getValue();
                
                String filename = directory + File.separatorChar + filePrefix;
                if (StringUtils.isNotBlank(locale.getName())) {
                    filename += "_" + locale.getName();
                }
                filename += ".properties";

                BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                writer.append(builder);
//                StringEscapeUtils.escapeJava(writer, builder.toString());
                writer.close();
            }

        }
    }

}
