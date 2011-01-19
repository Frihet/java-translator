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
import java.util.List;

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


    public List<MessageSection> getSections() {
        return sections;
    }

    public void setSections(List<MessageSection> sections) {
        this.sections = sections;
    }
}
