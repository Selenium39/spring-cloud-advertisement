package com.selenium.ad.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class AccessLogFilter extends ZuulFilter {
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    public int filterOrder() {
        //最后执行
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() throws ZuulException {
        //获取请求上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Long reqStartTime = (Long) ctx.get("reqStartTime");
        Long useTime = System.currentTimeMillis() - reqStartTime;
        String uri = request.getRequestURI();
        log.info("uri: " + uri + "," + "useTime: " + useTime);
        return null;
    }
}
