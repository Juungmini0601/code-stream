package codestream.jungmini.me.support.error;

import lombok.Getter;

@Getter
public class CodeStreamException extends RuntimeException {

    private final ErrorType errorType;

    private final Object data;

    public CodeStreamException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = null;
    }

    public CodeStreamException(ErrorType errorType, Object data) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = data;
    }
}
