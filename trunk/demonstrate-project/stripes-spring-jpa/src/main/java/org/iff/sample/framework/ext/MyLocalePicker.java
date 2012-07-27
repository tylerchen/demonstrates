/***
 * Excerpted from "Stripes: and Java Web Development is Fun Again",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/fdstr for more book information.
***/

package org.iff.sample.framework.ext;


import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sourceforge.stripes.localization.DefaultLocalePicker;
 

public class MyLocalePicker extends DefaultLocalePicker {
    public static final String LOCALE = "locale";

    @Override
    public Locale pickLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();

        // Look in the request.
        String locale = request.getParameter(LOCALE);
        if (locale != null) {
            session.setAttribute(LOCALE, locale);
        }
        // Not found in the request? Look in the session.
        else {
            locale = (String) session.getAttribute(LOCALE);
        }
        // Use the locale if found.
        if (locale != null) {
            return new Locale(locale);
        }
        // Otherwise, use the default.
        return super.pickLocale(request);
    }
}

