package org.xiaowu.behappy.common.core.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 小五
 */
@Builder
public class DeniedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@Setter
	@Getter
	private int code;

	@Setter
	@Getter
	private String msg;

}