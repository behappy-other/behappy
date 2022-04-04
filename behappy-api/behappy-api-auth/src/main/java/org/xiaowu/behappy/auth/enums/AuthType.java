package org.xiaowu.behappy.auth.enums;

import lombok.Getter;

/**
 * @author xiaowu
 * @apiNote
 */
@Getter
public enum AuthType {
    H5(1),APP(2),WEB(3);

    private int type;

    AuthType(int type) {
        this.type = type;
    }
}
