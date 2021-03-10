package com.study.egovspringbootmybatis.utility;

import com.study.egovspringbootmybatis.service.FunctionWithException;
import com.study.egovspringbootmybatis.service.ThrowingConsumer;
import lombok.extern.log4j.Log4j2;

import java.util.function.Consumer;
import java.util.function.Function;

@Log4j2
public class LambdaExceptionUtil {

    private static final LambdaExceptionUtil o = new LambdaExceptionUtil();

    public LambdaExceptionUtil() {
    }

    public static LambdaExceptionUtil get(){
        return o;
    }

    /**
     * Return 값이 없는 Lambda Exception 처리 핸들러
     **/
    public <T> Consumer<T> throwingConsumerWrapper(
            ThrowingConsumer<T, Exception> throwingConsumer) {
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    /**
     * Return 값이 있는 Lambda Exception 처리 핸들러
     **/
    public <T, E extends Exception> Consumer<T> handlingConsumerWrapper(
            ThrowingConsumer<T, E> throwingConsumer, Class<E> exceptionClass) {

        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = exceptionClass.cast(ex);
                    log.error(
                            "Exception occured : " + exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
    public <T, R, E extends Exception> Function<T, R> throwingApplyWrapper(FunctionWithException<T, R, E> fe) {
        return arg -> {
            try {
                return fe.apply(arg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
