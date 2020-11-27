package com.dh.spike.exception;

import com.dh.spike.result.CodeMsg;

/**
 * Create by DiaoHao on 2020/10/23 13:31
 */
public class GlobalException extends RuntimeException{

    CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
