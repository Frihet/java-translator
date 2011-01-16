package no.freecode.translator.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import no.freecode.translator.domain.MessageSection;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@RooWebScaffold(path = "messagesections", formBackingObject = MessageSection.class)
@RequestMapping("/messagesections")
@Controller
public class MessageSectionController {
}
