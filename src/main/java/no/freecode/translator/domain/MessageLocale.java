package no.freecode.translator.domain;

import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity
public class MessageLocale {

    @NotNull
    @Column(unique = true)
    private String name;

    private String description;

    public static List<MessageLocale> findAllMessageLocalesSorted() {
        return entityManager().createQuery("select o from MessageLocale o order by name", MessageLocale.class).getResultList();
    }

    public static List<MessageLocale> findMessagesIn(Collection<String> localeNames) {
        return entityManager().createQuery("select o from MessageLocale o where o.name in (:localeNames) order by name", MessageLocale.class)
            .setParameter("localeNames", localeNames)
            .getResultList();
    }

}
