/**
 *  Project: translator
 *  Created: Jan 9, 2011
 *  Copyright: 2011, FreeCode AS
 *
 *  This file is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published
 *  by the Free Software Foundation; version 3.
 */
package no.freecode.translator.domain;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Base class for entities.
 * 
 * @author Reidar Øksnevad <reidar.oksnevad@freecode.no>
 */
@MappedSuperclass
public abstract class BaseEntity {

    private Date created;
    
    private Date updated;

    @PrePersist
    protected void onCreate() {
      created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
      updated = new Date();
    }
}
