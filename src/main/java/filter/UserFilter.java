package filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebFilter(filterName = "authenFilter", urlPatterns = { "/users" })
public class UserFilter extends HttpFilter {

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		boolean authen = false;
		boolean isAdmin = false;
		String context = req.getContextPath();
		Cookie[] cookies = req.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("rolename".equals(cookie.getName())) {
					authen = true;
					if ("ROLE_ADMIN".equals(cookie.getValue())) {
						isAdmin = true;
					}
					break;
				}
			}
		}

		if (!authen) {
			res.sendRedirect(context + "/login");
		} else if (!isAdmin) {
			res.sendRedirect(context);
		} else {
			chain.doFilter(req, res);
		}
	}
}
