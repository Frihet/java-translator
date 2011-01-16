package no.freecode.translator.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import no.freecode.translator.domain.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@RooWebScaffold(path = "messages", formBackingObject = Message.class)
@RequestMapping("/messages")
@Controller
public class MessageController {
}
