/**
 *  Project: translator
 *  Created: Dec 3, 2010
 *  Copyright: 2010, FreeCode AS
 *
 *  This file is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published
 *  by the Free Software Foundation; version 3.
 */
package no.freecode.translator.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Reidar Øksnevad <reidar.oksnevad@freecode.no>
 *
 */
@Controller("editor")
public class EditorController {

    @RequestMapping(method = RequestMethod.GET, value = "update")
    public String editMessages(
            @RequestParam(value = "locale", required = false) String locale) {
        
        return "editor/update";
    }

}
