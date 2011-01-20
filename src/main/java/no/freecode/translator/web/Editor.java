/**
 *  Project: translator
 *  Created: Jan 19, 2011
 *  Copyright: 2011, FreeCode AS
 *
 *  This file is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published
 *  by the Free Software Foundation; version 3.
 */
package no.freecode.translator.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import no.freecode.translator.domain.Message;
import no.freecode.translator.domain.MessageLocale;
import no.freecode.translator.domain.MessageSection;

/**
 * @author Reidar Øksnevad <reidar.oksnevad@freecode.no>
 */
public class Editor {

    private List<MessageSection> sections = new ArrayList<MessageSection>();

    public Editor() { }
    
    public Editor(List<MessageSection> sections) {
        setSections(sections);
    }

    /**
     * Remove empty/blank entries.
     */
    public void cleanup() {
        for (MessageSection section : getSections()) {
	    	for (Message m : section.getMessages()) {
	    		Map<MessageLocale, String> translations = m.getTranslations();

	    		Set<Entry<MessageLocale, String>> entries = translations.entrySet();
	    		Iterator<Entry<MessageLocale, String>> it = entries.iterator();
	    		while (it.hasNext()) {
	    			Entry<MessageLocale, String> entry = it.next();

	    			if (StringUtils.isBlank(entry.getValue())) {
	    				it.remove();
	    			}
	    		}
	    	}
        }
	}

    public List<MessageSection> getSections() {
        return sections;
    }

    public void setSections(List<MessageSection> sections) {
        this.sections = sections;
    }
}
