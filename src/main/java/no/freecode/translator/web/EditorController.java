package no.freecode.translator.web;

import java.beans.PropertyEditorSupport;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import no.freecode.translator.domain.Message;
import no.freecode.translator.domain.MessageLocale;
import no.freecode.translator.domain.MessageSection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.annotation.Secured;
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
@Secured({ "ROLE_EDITOR" })
@RequestMapping("editor")
public class EditorController {

    private static final Logger logger = Logger.getLogger(EditorController.class);

    @Autowired
    private transient MailSender mailTemplate;

    @Autowired
    private transient SimpleMailMessage simpleMailMessage;

    @RequestMapping(method = RequestMethod.GET)
    public String editMessages(Model model, @RequestParam(value = "l", required = false) String locale, @RequestParam(value = "s", required = false) HashSet<String> availableLocales) {
        List<MessageLocale> locales;
        if (availableLocales == null || availableLocales.size() == 0) {
            locales = MessageLocale.findAllMessageLocalesSorted();
        } else {
            availableLocales.add("");
            if (locale != null) {
                availableLocales.add(locale);
            }
            locales = MessageLocale.findMessagesIn(availableLocales);
        }
        model.addAttribute("currentLocaleString", locale);
        model.addAttribute("locales", locales);
        return "editor/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveMessages(@ModelAttribute("editor") Editor editor, Model model, HttpSession session) {
        editor.cleanup();
        for (MessageSection section : editor.getSections()) {
            section.persist();
        }
        
        dumpInfoToMail(session, editor);
        
        session.setAttribute("message", "Messages saved");
        return "redirect:editor";
    }

    private void dumpInfoToMail(HttpSession session, Editor editor) {
        try {
            HashMap<MessageLocale, Integer> counters = new HashMap<MessageLocale, Integer>();
            for (MessageSection section : editor.getSections()) {
                for (Message message : section.getMessages()) {
                    for (Entry<MessageLocale, String> entry : message.getTranslations().entrySet()) {
                        Integer counter = counters.get(entry.getKey());
                        if (counter == null) {
                            counter = 1;
                        } else {
                            counter ++;
                        }
                        counters.put(entry.getKey(), counter);
                    }
                }
            }

            StringBuilder sb = new StringBuilder();

            sb.append("Number of entries:\n");
            for (Entry<MessageLocale, Integer> entry : counters.entrySet()) {
                sb.append("  " + entry.getKey().getGuessedLanguage() + ": " + entry.getValue() + "\n");
            }

            sb.append("\n\nSession data:\n");
            Enumeration names = session.getAttributeNames();
            while (names.hasMoreElements()) {
                String key = (String) names.nextElement();
                sb.append(" " + key + ": " + session.getAttribute(key) + "\n");
            }
    
            sendMessage("reidar.oksnevad@freecode.no", "Messages were saved." + sb.toString());

        } catch (Exception e) {
            // Don't let method fail the rest of the saving. It's not important.
        }
    }
    
    @ModelAttribute("editor")
    public Editor getEditor() {
        return new Editor(MessageSection.findAllMessageSectionsSorted());
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(MessageLocale.class, new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(MessageLocale.findMessageLocale(Long.parseLong(text)));
            }

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

    public void sendMessage(String mailTo, String message) {
        simpleMailMessage.setTo(mailTo);
        simpleMailMessage.setText(message);
        mailTemplate.send(simpleMailMessage);
    }
}
