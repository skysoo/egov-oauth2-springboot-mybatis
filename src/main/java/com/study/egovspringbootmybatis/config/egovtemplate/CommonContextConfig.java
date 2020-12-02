package com.study.egovspringbootmybatis.config.egovtemplate;

import egovframework.rte.fdl.cmmn.trace.LeaveaTrace;
import egovframework.rte.fdl.cmmn.trace.handler.DefaultTraceHandler;
import egovframework.rte.fdl.cmmn.trace.handler.TraceHandler;
import egovframework.rte.fdl.cmmn.trace.manager.DefaultTraceHandleManager;
import egovframework.rte.fdl.cmmn.trace.manager.TraceHandlerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

@Configuration
public class CommonContextConfig {
    @Bean
    public LeaveaTrace leaveaTrace(DefaultTraceHandleManager defaultTraceHandleManager){
        LeaveaTrace leaveaTrace = new LeaveaTrace();
        leaveaTrace.setTraceHandlerServices(new TraceHandlerService[]{defaultTraceHandleManager});
        return leaveaTrace;
    }

    @Bean
    public DefaultTraceHandleManager defaultTraceHandleManager(AntPathMatcher antPathMatcher, DefaultTraceHandler defaultTraceHandler){
        DefaultTraceHandleManager defaultTraceHandleManager = new DefaultTraceHandleManager();
        defaultTraceHandleManager.setReqExpMatcher(antPathMatcher);
        defaultTraceHandleManager.setPatterns(new String[]{"*"});
        defaultTraceHandleManager.setHandlers(new TraceHandler[]{defaultTraceHandler});
        return defaultTraceHandleManager;
    }

    @Bean
    public AntPathMatcher antPathMatcher(){
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return antPathMatcher;
    }

    @Bean
    public DefaultTraceHandler defaultTraceHandler(){
        DefaultTraceHandler defaultTraceHandler = new DefaultTraceHandler();
        return defaultTraceHandler;
    }
}
