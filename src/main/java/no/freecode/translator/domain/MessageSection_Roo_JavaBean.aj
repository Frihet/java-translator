// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package no.freecode.translator.domain;

import java.lang.String;
import java.util.Set;
import no.freecode.translator.domain.Message;

privileged aspect MessageSection_Roo_JavaBean {
    
    public String MessageSection.getId() {
        return this.id;
    }
    
    public void MessageSection.setId(String id) {
        this.id = id;
    }
    
    public String MessageSection.getDescription() {
        return this.description;
    }
    
    public void MessageSection.setDescription(String description) {
        this.description = description;
    }
    
    public Set<Message> MessageSection.getMessages() {
        return this.messages;
    }
    
    public void MessageSection.setMessages(Set<Message> messages) {
        this.messages = messages;
    }
    
}
