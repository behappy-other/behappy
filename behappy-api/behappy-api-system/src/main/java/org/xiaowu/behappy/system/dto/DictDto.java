package org.xiaowu.behappy.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaowu
 */
@Data
public class DictDto implements Serializable {

    private static final long serialVersionUID = -524920270954998624L;
    private Long id;

    private String name;

    private String value;

    private String type;

}