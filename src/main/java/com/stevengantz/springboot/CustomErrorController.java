/**
 * 
 */
package com.stevengantz.springboot;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by Steven Gantz on 3/5/2017.
 *
 * Redirect from /error back to /
 * 
 * Based on the following gist:
 * https://gist.github.com/jonikarppinen/662c38fb57a23de61c8b
 */
@RestController
public class CustomErrorController implements ErrorController {

	private static final String PATH = "/error";

	@RequestMapping(value = PATH)
	RedirectView redirectToRoot(HttpServletRequest request, HttpServletResponse response) {
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("/");
		return redirectView;
	}

	/**
	 * @see org.springframework.boot.autoconfigure.web.ErrorController#getErrorPath()
	 */
	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
