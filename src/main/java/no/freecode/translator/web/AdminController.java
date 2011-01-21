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

import java.io.IOException;

import no.freecode.translator.business.MessageExporter;
import no.freecode.translator.business.MessageImporter;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Reidar Øksnevad <reidar.oksnevad@freecode.no>
 */
@Controller
@Secured({"ROLE_ADMIN"})
@RequestMapping("admin")
public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    private MessageImporter messageImporter;
    
    @Autowired
    private MessageExporter messageExporter;


    /**
     * Upload messages.
     * 
     * @throws IOException 
     */
    @RequestMapping(method = RequestMethod.POST, value = "upload")
    public @ResponseBody String upload(@RequestParam("file") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            String filename = FilenameUtils.getName(file.getOriginalFilename());
            messageImporter.importData(file.getInputStream(), filename);
            return "Imported data from " + filename + "\n";

        } else {
            return "redirect:uploadFailure";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "dump")
    public @ResponseBody String dump() {
        
        try {
            messageExporter.dumpToFiles();
            return "Dumped messages\n";

        } catch (IOException e) {
            logger.error("Unable to dump to files", e);
            return "Error: unable to dump to files. Message was: " + e.getMessage() + "\n";
        }
        
    }
    
    
    public static void main(String[] args) {

    }
}
