package com.study.egovspringbootmybatis.utility;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * @author skysoo
 * @version 1.0.0
 * @since 2020-06-25 오후 3:20
 **/
@Getter
@Log4j2
public class ThreadHandler implements Thread.UncaughtExceptionHandler {
    private String handleName;

    public ThreadHandler(String handleName) {
        this.handleName = handleName;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error(handleName + "caught Exception in Thread - "
                + t.getName()
                + "=>" + e);
    }
}