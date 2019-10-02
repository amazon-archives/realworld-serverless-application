package com.amazonaws.serverless.apprepo.container;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import lombok.extern.slf4j.Slf4j;

/**
 * Request filter to support CORS.
 */
@Provider
@Slf4j
public class ResponseFilter implements ContainerResponseFilter {
  @Override
  public void filter(ContainerRequestContext requestContext,
                     ContainerResponseContext responseContext) {
    String origin = "*";
    log.info("Set CORS header to allow request from {}", origin);
    responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
  }
}