package org.home.tutorial.springsecurity.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;

//// NOT IN USE
// went with placing error and 404 files into /src/main/resources/public/error and even didn't have to end up disabling whitelabel setting in application.properties
// Instructions for how to do that are on the same baeldung page below but what I couldn't figure out at first was where to place the files until I stumbled upon them in spring boot's documentation
// See: https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#web.servlet.spring-mvc.error-handling.error-pages
/**
 * Error handling controller
 * <p>
 * Shows error page, See: https://www.baeldung.com/spring-boot-custom-error-page
 */
// @Controller
public class ErrorHandlingController implements ErrorController {

    // @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            // you can specify individual pages for specific error codes, here I'm being
            // lazy and just always showing error.html
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error.html";// return "404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error.html";// return "500";
            }
        }
        return "error.html";
    }
}
