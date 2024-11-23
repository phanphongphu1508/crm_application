package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*") // Áp dụng cho tất cả các URL
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(javax.servlet.ServletRequest request, javax.servlet.ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String requestURI = req.getRequestURI();

        // Cho phép truy cập các URL không cần đăng nhập
        if (requestURI.endsWith("/login") || requestURI.endsWith("/logout")) {
            chain.doFilter(request, response);
            return;
        }

        boolean isLoggedIn = checkLoginFromCookies(req);

        if (isLoggedIn) {
            chain.doFilter(request, response); // Người dùng đã đăng nhập, tiếp tục xử lý
        } else {
            resp.sendRedirect(req.getContextPath() + "/login"); // Chuyển hướng đến trang đăng nhập
        }
    }

    /**
     * Kiểm tra trạng thái đăng nhập dựa trên cookie `userId`.
     */
    private boolean checkLoginFromCookies(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userId".equals(cookie.getName())) {
                    return true; // Có thông tin đăng nhập trong cookie
                }
            }
        }
        return false; // Không tìm thấy thông tin đăng nhập
    }

    @Override
    public void destroy() {
    }
}
