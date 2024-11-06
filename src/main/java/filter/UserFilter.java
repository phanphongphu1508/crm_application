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
@WebFilter("/users")
public class UserFilter extends HttpFilter {
	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		String context = req.getContextPath();
		Cookie[] cookies = req.getCookies();

		for (Cookie cookie : cookies) {
			String value = cookie.getValue();
			if (value.equals("ROLE_ADMIN")) {
				chain.doFilter(req, resp);
				break;
			} else if (value.equals("ROLE_MANAGER") || value.equals("ROLE_USER")) {
				resp.sendRedirect(context + "/index");
				break;
			}
		}
	}
}