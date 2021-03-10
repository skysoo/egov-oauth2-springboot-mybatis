package com.study.egovspringbootmybatis.service;

public interface FunctionWithException<T,R,E extends Exception>  {
    R apply(T t) throws E;
}