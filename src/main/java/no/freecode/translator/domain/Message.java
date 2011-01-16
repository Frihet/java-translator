package no.freecode.translator.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findMessagesByPropertyEquals" })
public class Message extends BaseEntity {

    @NotNull
    @Column(unique = true)
    @Size(min = 1)
    private String property;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<MessageLocale, String> translations = new HashMap<MessageLocale, String>();
    
}
