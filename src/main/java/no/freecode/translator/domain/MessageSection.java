package no.freecode.translator.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity
public class MessageSection {

    private String description;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @ElementCollection(fetch = FetchType.EAGER)
//    @OneToMany(cascade = CascadeType.ALL)
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Message> messages = new HashSet<Message>();

    public static List<MessageSection> findAllMessageSectionsSorted() {
        return entityManager().createQuery("select o from MessageSection o order by description", MessageSection.class).getResultList();
    }

}
