package com.selenium.ad.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter {

    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * filter优先级
     * @return 数字越小，优先级越高
     */
    public int filterOrder() {
        //最先开始执行
        return 0;
    }

    /**
     * 是否需要执行此过滤器
     * @return
     */
    public boolean shouldFilter() {
        return true;
    }

    public Object run() throws ZuulException {
        //获取Zuul的请求上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set("reqStartTime",System.currentTimeMillis());
        return null;
    }
}
