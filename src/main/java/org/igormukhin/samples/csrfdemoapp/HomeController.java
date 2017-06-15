package org.igormukhin.samples.csrfdemoapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public String get() {
        logger.info("/get called: successful");
        return "Some private info from a GET request";
    }

    @RequestMapping(value = "/post", method = POST)
    @ResponseBody
    public String post(HttpServletRequest request,HttpServletResponse reponse) {
    	HttpSession session = request.getSession() ;//watch it with debug mode
    	DefaultCsrfToken defaultCsrfToken = (DefaultCsrfToken)session.getAttribute("org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN");
        String csrfToken = defaultCsrfToken.getToken();
        reponse = reponse;
    	logger.info("/post called: successful  ,csrfToken: "+csrfToken);
        return "this POST is successful,csrfToken: "+csrfToken;
    }

    @RequestMapping(value = "/upload", method = POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        logger.info("/upload called: file {} uploaded", file.getOriginalFilename());
        return String.format("Upload of %s was successful", file.getOriginalFilename());
    }

}
