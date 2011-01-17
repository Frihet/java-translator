// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package no.freecode.translator.web;

import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import no.freecode.translator.domain.Message;
import no.freecode.translator.domain.MessageSection;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect MessageSectionController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String MessageSectionController.create(@Valid MessageSection messageSection, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("messageSection", messageSection);
            return "messagesections/create";
        }
        messageSection.persist();
        return "redirect:/messagesections/" + encodeUrlPathSegment(messageSection.getId().toString(), request);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String MessageSectionController.createForm(Model model) {
        model.addAttribute("messageSection", new MessageSection());
        return "messagesections/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String MessageSectionController.show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("messagesection", MessageSection.findMessageSection(id));
        model.addAttribute("itemId", id);
        return "messagesections/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String MessageSectionController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            model.addAttribute("messagesections", MessageSection.findMessageSectionEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) MessageSection.countMessageSections() / sizeNo;
            model.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            model.addAttribute("messagesections", MessageSection.findAllMessageSections());
        }
        return "messagesections/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String MessageSectionController.update(@Valid MessageSection messageSection, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("messageSection", messageSection);
            return "messagesections/update";
        }
        messageSection.merge();
        return "redirect:/messagesections/" + encodeUrlPathSegment(messageSection.getId().toString(), request);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String MessageSectionController.updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("messageSection", MessageSection.findMessageSection(id));
        return "messagesections/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String MessageSectionController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        MessageSection.findMessageSection(id).remove();
        model.addAttribute("page", (page == null) ? "1" : page.toString());
        model.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/messagesections?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
    
    @ModelAttribute("messages")
    public Collection<Message> MessageSectionController.populateMessages() {
        return Message.findAllMessages();
    }
    
    String MessageSectionController.encodeUrlPathSegment(String pathSegment, HttpServletRequest request) {
        String enc = request.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
