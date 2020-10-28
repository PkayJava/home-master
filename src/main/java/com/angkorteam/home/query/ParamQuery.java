package com.angkorteam.home.query;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ParamQuery implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7292387740893956434L;

    protected boolean dirty = true;

    protected String cached = "";

    protected final Map<String, Object> param = new LinkedHashMap<>();

    public abstract String toSQL();

    public Map<String, Object> toParam() {
        return this.param;
    }

    /**
     * supported simple single parameter only
     * example name = :student
     *
     * @param criteria name = :student
     * @return student
     */
    protected String lookupParamName(String criteria) {
        return StringUtils.trimToEmpty(criteria.substring(criteria.indexOf(':') + 1));
    }

    /**
     * supported simple single parameter only
     * example name = :student
     *
     * @param expression name = 'student'
     * @return 'student'
     */
    protected String lookupFieldName(String expression) {
        return StringUtils.trimToEmpty(expression.substring(0, expression.indexOf('=')));
    }

    /**
     * @param expression name = 'student'
     * @return name
     */
    protected String lookupValueExpression(String expression) {
        return StringUtils.trimToEmpty(expression.substring(expression.indexOf('=') + 1));
    }

    protected String lookupParamNameDetection(String valueExpression) {
        int colon = valueExpression.indexOf(':');
        if (colon >= 0) {
            return StringUtils.trimToEmpty(valueExpression.substring(valueExpression.indexOf(':') + 1));
        } else {
            return StringUtils.trimToEmpty(valueExpression);
        }
    }

    protected String lookupValueExpressionDetection(String expression) {
        int equation = expression.indexOf('=');
        String valueExpression;
        if (equation >= 0) {
            valueExpression = StringUtils.trimToEmpty(expression.substring(expression.indexOf('=') + 1));
        } else {
            valueExpression = StringUtils.trimToEmpty(expression);
        }
        if (valueExpression.startsWith(":")) {
            return valueExpression;
        } else {
            return ":" + valueExpression;
        }
    }

    protected String lookupFieldNameDetection(String expression) {
        int equation = expression.indexOf('=');
        if (equation >= 0) {
            return StringUtils.trimToEmpty(expression.substring(0, expression.indexOf('=')));
        } else {
            return StringUtils.trimToEmpty(expression);
        }
    }

}