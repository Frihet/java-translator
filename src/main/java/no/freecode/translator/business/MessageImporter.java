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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.freecode.translator.domain.Message;
import no.freecode.translator.domain.MessageLocale;
import no.freecode.translator.domain.MessageSection;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Reidar Øksnevad <reidar.oksnevad@freecode.no>
 *
 */
@Repository
@Transactional
public class MessageImporter {

    private static final Logger logger = Logger.getLogger(MessageImporter.class);
    
    public void importData(InputStream input, String filename) throws IOException {
        MessageLocale locale = getLocale(filename);
        locale.persist();
        
        BufferedReader csvReader = new BufferedReader(new InputStreamReader(input));

//        HashMap<String, MessageSection> sections = new HashMap<String, MessageSection>();

//        MessageSection curSection = new MessageSection();
        String curSectionId;
        String curComment = null;

        String line;
        while ((line = StringEscapeUtils.unescapeJava(StringUtils.strip(csvReader.readLine()))) != null) {

            if (StringUtils.startsWith(line, "#")) {
                String comment = StringUtils.stripStart(line, "# ");
                if (StringUtils.isNotEmpty(comment)) {
                    curComment = comment;
                }

            } else {
                // Proper line
                String[] entry = StringUtils.split(line, "=", 2);
                
                if (entry.length == 2) {
                    String key = StringUtils.strip(entry[0]);
                    String value = StringUtils.strip(entry[1]);

                    String[] keyFragments = StringUtils.split(key, ".", 2);

                    if (keyFragments.length < 2) {
                        // Root section
                        curSectionId = "root";

                    } else {
                        // Split into sections based on what's before the first '.' in the key.
                        curSectionId = keyFragments[0];
                    }

                    MessageSection curSection = MessageSection.findMessageSection(curSectionId);
//                    curSection = sections.get(curSectionId);

                    if (curSection == null) {
                        curSection = new MessageSection();
                        curSection.setId(curSectionId);
//                        sections.put(curSectionId, curSection);
                    }

                    if (StringUtils.isNotBlank(curComment)) {
                    	curSection.setDescription(curComment);
                    	curComment = null;
                    }

                    Set<Message> messages = curSection.getMessages();
                    if (messages == null) {
                        messages = new HashSet<Message>();
                        curSection.setMessages(messages);
                    }

                    // Does this message already have one or more translation? If not, create a new message.
                    List<Message> res = Message.findMessagesByPropertyEquals(key).getResultList();
                    Message message;
                    if (res.size() > 0) {
                        message = res.get(0);
                        logger.debug("Yes, found an already persisted one: " + message);

                    } else {
                        message = new Message();
                    }

                    message.setProperty(key);
                    message.getTranslations().put(locale, value);

                    messages.add(message);
//                    message.persist();
                    
                    curSection.persist();

                } else {
                    // Could be an empty line, or an invalid line. Ignore it.
                }
            }
        }

//        logger.info("sections: " + sections.size());
//        for (MessageSection section : sections.values()) {
//            System.out.println(": (id:" + section.getId() + ") " + section);
//            
//            for (Message message : section.getMessages()) {
//                System.out.println("--: (id:" + message.getId() + ") " + message);
//                
//                for (Entry<MessageLocale, String> entry : message.getTranslations().entrySet()) {
//                    System.out.println("-----: " + entry.getKey() + ": " + entry.getValue());
//                }
//            }
//            
//            section.persist();
//        }
    }

    /**
     * @param basename
     *            e.g. "messages_nb_NO.properties", "messages_nb.properties" or
     *            "messages.properties" (default/base locale).
     * @return
     */
    private static MessageLocale getLocale(String filename) {
        String basename = FilenameUtils.getBaseName(filename);
        String localeString = StringUtils.substringAfter(basename, "_");  // --> e.g "nb" or "nb_NO"

        MessageLocale locale = new MessageLocale();
        locale.setName(StringUtils.defaultIfEmpty(localeString, ""));
        return locale;
    }

    public static void main(String[] args) {
//        System.out.println(StringEscapeUtils.escapeJava("Her har du en stræng!"));
//        System.out.println(StringEscapeUtils.unescapeJava("Her har du en str\u00E6ng!"));

//        String s0 = "# Maps";
//        String s1 = "merge.info = Note that this will completely erase the element";
//
//        String curSectionId = null;
//        System.out.println("sdf".equals(curSectionId));
        
//        System.out.println(StringUtils.split(s1, "=", 2));
        
//        System.out.println(StringUtils.stripStart("###   ", "# "));
    }
}
