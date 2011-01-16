/**
 *  Project: translator
 *  Created: Jan 16, 2011
 *  Copyright: 2011, FreeCode AS
 *
 *  This file is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published
 *  by the Free Software Foundation; version 3.
 */
package no.freecode.translator.business;

import junit.framework.TestCase;
import no.freecode.translator.business.MessageExporter.Dumper;
import no.freecode.translator.domain.MessageLocale;


/**
 * @author Reidar Øksnevad <reidar.oksnevad@freecode.no>
 *
 */
public class MessageExporterTest extends TestCase {

    public void testDumper() throws Exception {
        Dumper dumper = (new MessageExporter()).new Dumper();

        MessageLocale locale1 = new MessageLocale(); locale1.setName("");
        MessageLocale locale2 = new MessageLocale(); locale2.setName("nb");

        dumper.append("link.clickToGoBack", "Click here to go back", locale1);
        dumper.append("link.clickToGoBack", "Trykk her for å gå tilbake", locale2);
        dumper.append("link.ost", "rai rai! aaah!!!", locale1);
        dumper.append("link.ost", "rai rai! æææh!!!", locale2);
        dumper.flushSection("Hovedkommentar for denne avdelingen");

        dumper.append("link.clickToGoBack", "Click here to go back", locale1);
        dumper.append("link.clickToGoBack", "Trykk her for å gå tilbake", locale2);
        dumper.append("link.ost", "rai rai! aaah!!!", locale1);
        dumper.append("link.ost", "rai rai! æææh!!!", locale2);
        dumper.flushSection("Hovedkommentar for andre avdeling");

        dumper.writeFiles();
    }
}
