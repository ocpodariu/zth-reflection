package ro.teamnet.zth.web;

import ro.teamnet.zth.file.LogFileWriter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Author: Ovidiu
 * Date:   5/5/2015
 */
public class HeadersLogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * This filter logs current request headers to filesystem
     * @param req - Client request
     * @param resp - Client response
     * @param chain - Filters chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        // Log content type
        LogFileWriter.logHeader("contentType", req.getContentType());


        // Log all other headers
        Enumeration headers = ((HttpServletRequest) req).getHeaderNames();

        String headerName, headerValue;
        while (headers.hasMoreElements()) {
            headerName = (String) headers.nextElement();
            headerValue = ((HttpServletRequest) req).getHeader(headerName);
            LogFileWriter.logHeader(headerName, headerValue);
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
