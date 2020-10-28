package com.angkorteam.home.query;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class InsertQuery extends ParamQuery {

    /**
     *
     */
    private static final long serialVersionUID = -8830389077520597417L;

    protected final String tableName;

    protected final Map<String, String> field = new LinkedHashMap<>();

    public InsertQuery(String tableName) {
        this.tableName = tableName;
    }

    /**
     * simple sql equation expression
     *
     * @param expression name = 'abc' or name = sysdate
     * @return
     */
    public InsertQuery addValue(String expression) {
        String fieldName = lookupFieldName(expression);
        String valueExpression = lookupValueExpression(expression);
        if (fieldName == null || "".equals(fieldName)) {
            throw new IllegalArgumentException("expression is invalid due to fieldName empty");
        }
        if (valueExpression == null || "".equals(valueExpression)) {
            throw new IllegalArgumentException("expression is invalid due to valueExpression empty");
        }
        this.field.put(fieldName, valueExpression);
        this.dirty = true;
        return this;
    }

    public InsertQuery addValue(String expression, String paramValue) {
        String fieldName = lookupFieldNameDetection(expression);
        String valueExpression = lookupValueExpressionDetection(expression);
        String paramName = lookupParamNameDetection(valueExpression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public InsertQuery addValue(String expression, Number paramValue) {
        String fieldName = lookupFieldNameDetection(expression);
        String valueExpression = lookupValueExpressionDetection(expression);
        String paramName = lookupParamNameDetection(valueExpression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public InsertQuery addValue(String expression, char paramValue) {
        String fieldName = lookupFieldNameDetection(expression);
        String valueExpression = lookupValueExpressionDetection(expression);
        String paramName = lookupParamNameDetection(valueExpression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public InsertQuery addValue(String expression, boolean paramValue) {
        String fieldName = lookupFieldNameDetection(expression);
        String valueExpression = lookupValueExpressionDetection(expression);
        String paramName = lookupParamNameDetection(valueExpression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public InsertQuery addValue(String expression, Date paramValue) {
        String fieldName = lookupFieldNameDetection(expression);
        String valueExpression = lookupValueExpressionDetection(expression);
        String paramName = lookupParamNameDetection(valueExpression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public InsertQuery addValue(String expression, byte[] paramValue) {
        String fieldName = lookupFieldNameDetection(expression);
        String valueExpression = lookupValueExpressionDetection(expression);
        String paramName = lookupParamNameDetection(valueExpression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public InsertQuery addValue(String expression, String paramName, String paramValue) {
        String fieldName = lookupFieldName(expression);
        String valueExpression = lookupValueExpression(expression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public InsertQuery addValue(String expression, String paramName, Number paramValue) {
        String fieldName = lookupFieldName(expression);
        String valueExpression = lookupValueExpression(expression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public InsertQuery addValue(String expression, String paramName, char paramValue) {
        String fieldName = lookupFieldName(expression);
        String valueExpression = lookupValueExpression(expression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public InsertQuery addValue(String expression, String paramName, byte[] paramValue) {
        String fieldName = lookupFieldName(expression);
        String valueExpression = lookupValueExpression(expression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public InsertQuery addValue(String expression, String paramName, boolean paramValue) {
        String fieldName = lookupFieldName(expression);
        String valueExpression = lookupValueExpression(expression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public InsertQuery addValue(String expression, String paramName, Date paramValue) {
        String fieldName = lookupFieldName(expression);
        String valueExpression = lookupValueExpression(expression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public InsertQuery addValueInternal(String expression, Object paramValue) {
        String fieldName = lookupFieldName(expression);
        if (fieldName == null || "".equals(fieldName)) {
            throw new IllegalArgumentException("expression is invalid due to fieldName empty");
        }
        String valueExpression = lookupValueExpression(expression);
        if (valueExpression == null || "".equals(valueExpression)) {
            throw new IllegalArgumentException("expression is invalid due to valueExpression empty");
        }
        String paramName = lookupParamName(valueExpression);
        if (paramName == null || "".equals(paramName)) {
            throw new IllegalArgumentException("paramName is required");
        }
        if (paramValue == null) {
            throw new IllegalArgumentException("paramValue is required");
        }
        this.field.put(fieldName, valueExpression);
        this.param.put(paramName, paramValue);
        this.dirty = true;
        return this;
    }

    public InsertQuery addValueInternal(String fieldName, String valueExpression, String paramName, Object paramValue) {
        if (fieldName == null || "".equals(fieldName)) {
            throw new IllegalArgumentException("expression is invalid due to fieldName empty");
        }
        if (valueExpression == null || "".equals(valueExpression)) {
            throw new IllegalArgumentException("expression is invalid due to valueExpression empty");
        }
        if (paramName == null || "".equals(paramName)) {
            throw new IllegalArgumentException("paramName is required");
        }
        this.field.put(fieldName, valueExpression);
        this.param.put(paramName, paramValue);
        this.dirty = true;
        return this;
    }

    public String toSQL() {
        if (!this.dirty) {
            return this.cached;
        }
        this.cached = "INSERT INTO " + this.tableName + "(" + StringUtils.join(this.field.keySet(), ", ") + ")" + " " + "VALUES" + "(" + StringUtils.join(this.field.values(), ", ") + ")";
        this.dirty = false;
        return this.cached;
    }
}