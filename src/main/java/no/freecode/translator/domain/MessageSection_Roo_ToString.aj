// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package no.freecode.translator.domain;

import java.lang.String;

privileged aspect MessageSection_Roo_ToString {
    
    public String MessageSection.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Description: ").append(getDescription()).append(", ");
        sb.append("Messages: ").append(getMessages() == null ? "null" : getMessages().size());
        return sb.toString();
    }
    
}
