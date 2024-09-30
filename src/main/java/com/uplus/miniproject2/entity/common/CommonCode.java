package com.uplus.miniproject2.entity.common;

import com.uplus.miniproject2.entity.common.Key.CodeKey;

public enum CommonCode {
    REGISTER(new CodeKey("A01", "010")),
    APPROVED(new CodeKey("A02", "010")),
    UPDATE(new CodeKey("A01", "020")),
    PENDING(new CodeKey("A02", "020")),
    REJECTED(new CodeKey("A02", "030"));

    private CodeKey codeKey;

    CommonCode(CodeKey codeKey) {
        this.codeKey = codeKey;
    }


    public CodeKey getCodeKey() {
       return codeKey;
    }
}
