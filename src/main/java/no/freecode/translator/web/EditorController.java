/**
 *  Project: translator
 *  Created: Dec 3, 2010
 *  Copyright: 2010, FreeCode AS
 *
 *  This file is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published
 *  by the Free Software Foundation; version 3.
 */
package no.freecode.translator.web;

import java.beans.PropertyEditorSupport;
import java.util.HashSet;
import java.util.List;

import no.freecode.translator.domain.MessageLocale;
import no.freecode.translator.domain.MessageSection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Reidar Øksnevad <reidar.oksnevad@freecode.no>
 *
 */
@Controller
public class EditorController {

    private static final Logger logger = Logger.getLogger(EditorController.class);
    
    @RequestMapping(method = RequestMethod.GET, value = "editor")
    public String editMessages(
            Model model,
            @RequestParam(value = "l", required = false) String locale,
            @RequestParam(value = "a", required = false) HashSet<String> availableLocales) {

        List<MessageLocale> locales;
        if (availableLocales == null || availableLocales.size() == 0) {
            locales = MessageLocale.findAllMessageLocalesSorted();
        } else {
            availableLocales.add("");  // always include default locale
            locales = MessageLocale.findMessagesIn(availableLocales);
        }

    	model.addAttribute("currentLocaleString", locale);
    	model.addAttribute("locales", locales);
        return "editor/index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "editor")
    public String saveMessages(
            @ModelAttribute("editor") Editor editor,
            Model model) {

        for (MessageSection section : editor.getSections()) {
//            logger.debug(section);
            section.persist();
        }

//        List<MessageLocale> locales = MessageLocale.findAllMessageLocalesSorted();
//        List<MessageSection> sections = MessageSection.findAllMessageSectionsSorted();
//
//        model.addAttribute("locales", locales);
//        model.addAttribute("sections", sections);
        return "editor/index";
    }


    @ModelAttribute("editor")
    public Editor getEditor() {
        return new Editor(MessageSection.findAllMessageSectionsSorted());
    }
    
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(MessageLocale.class, new PropertyEditorSupport() {
            /* (non-Javadoc)
             * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
             */
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(MessageLocale.findMessageLocale(Long.parseLong(text)));
            }
            
            /* (non-Javadoc)
             * @see java.beans.PropertyEditorSupport#getAsText()
             */
            @Override
            public String getAsText() {
               MessageLocale locale = (MessageLocale) getValue();
                if (locale == null) {
                    return null;
                } else {
                    return String.valueOf(locale.getId());
                }
            }
        });
    }
}
