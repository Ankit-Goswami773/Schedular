
package com.schedularproject.jwtfilters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.schedularproject.util.SchedularUtility;

@Component
public class SchedularJwtFilter extends OncePerRequestFilter {

	@Autowired
	SchedularUtility schedularUtility;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		boolean isValidate = false;
		String jwtToken = null;

		final String requestTokenHeader = request.getHeader("Authorization");

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);

			isValidate = schedularUtility.validateToken(jwtToken);
			if (!isValidate) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "user token is expired");
			}
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "user unauthorized");
		}
		chain.doFilter(request, response);
	}

}
