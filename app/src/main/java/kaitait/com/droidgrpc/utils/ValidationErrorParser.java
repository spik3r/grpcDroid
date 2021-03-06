package kaitait.com.droidgrpc.utils;

import android.content.Context;
import android.util.Log;

import net.sf.oval.ConstraintViolation;

import kaitait.com.droidgrpc.R;

public class ValidationErrorParser {
    private static final String LENGTH_ERROR = "net.sf.oval.constraint.Length";
    private static final String MIN_LENGTH_ERROR = "net.sf.oval.constraint.MinLength";
    private static final String MAX_LENGTH_ERROR = "net.sf.oval.constraint.MaxLength";
    private static final String NOT_EMPTY_ERROR = "net.sf.oval.constraint.NotEmpty";
    private static final String EQUAL_TO_FIELD_ERROR = "net.sf.oval.constraint.EqualToField";
    private final Context app_context;

    public ValidationErrorParser(Context context) {
        this.app_context = context;
    }

    public String parseOvalError(ConstraintViolation violation) {
        Log.i("parseOvalError", violation.getErrorCode());
        if (violation.getErrorCode().equals(LENGTH_ERROR)) {
            return app_context.getString(R.string.error_min_length);
        } else if (violation.getErrorCode().equals(MIN_LENGTH_ERROR)) {
            return app_context.getString(R.string.error_min_length);
        } else if (violation.getErrorCode().equals(MAX_LENGTH_ERROR)) {
            return app_context.getString(R.string.error_max_length);
        } else if (violation.getErrorCode().equals(NOT_EMPTY_ERROR)) {
            return app_context.getString(R.string.error_not_empty);
        } else if (violation.getErrorCode().equals(EQUAL_TO_FIELD_ERROR)) {
            return app_context.getString(R.string.error_not_equal);
        } else {
            return app_context.getString(R.string.error_invalid);
        }
    }
}
