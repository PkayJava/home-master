package com.angkorteam.home.query;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class UpdateQuery extends WhereQuery {

    /**
     *
     */
    private static final long serialVersionUID = 1950223463620867110L;

    protected final String tableName;

    protected final Map<String, String> field = new LinkedHashMap<>();

    public UpdateQuery(String tableName) {
        this.tableName = tableName;
    }

    /**
     * simple sql equation expression
     *
     * @param expression name = 'abc' or name = sysdate
     * @return
     */
    public UpdateQuery addValue(String expression) {
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

    public UpdateQuery addValue(String expression, String paramValue) {
        String fieldName = lookupFieldNameDetection(expression);
        String valueExpression = lookupValueExpressionDetection(expression);
        String paramName = lookupParamNameDetection(valueExpression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public UpdateQuery addValue(String expression, Number paramValue) {
        String fieldName = lookupFieldNameDetection(expression);
        String valueExpression = lookupValueExpressionDetection(expression);
        String paramName = lookupParamNameDetection(valueExpression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public UpdateQuery addValue(String expression, char paramValue) {
        String fieldName = lookupFieldNameDetection(expression);
        String valueExpression = lookupValueExpressionDetection(expression);
        String paramName = lookupParamNameDetection(valueExpression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public UpdateQuery addValue(String expression, byte[] paramValue) {
        String fieldName = lookupFieldNameDetection(expression);
        String valueExpression = lookupValueExpressionDetection(expression);
        String paramName = lookupParamNameDetection(valueExpression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public UpdateQuery addValue(String expression, boolean paramValue) {
        String fieldName = lookupFieldNameDetection(expression);
        String valueExpression = lookupValueExpressionDetection(expression);
        String paramName = lookupParamNameDetection(valueExpression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public UpdateQuery addValue(String expression, Date paramValue) {
        String fieldName = lookupFieldNameDetection(expression);
        String valueExpression = lookupValueExpressionDetection(expression);
        String paramName = lookupParamNameDetection(valueExpression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public UpdateQuery addValue(String expression, String paramName, String paramValue) {
        String fieldName = lookupFieldName(expression);
        String valueExpression = lookupValueExpression(expression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public UpdateQuery addValue(String expression, String paramName, Number paramValue) {
        String fieldName = lookupFieldName(expression);
        String valueExpression = lookupValueExpression(expression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public UpdateQuery addValue(String expression, String paramName, char paramValue) {
        String fieldName = lookupFieldName(expression);
        String valueExpression = lookupValueExpression(expression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public UpdateQuery addValue(String expression, String paramName, byte[] paramValue) {
        String fieldName = lookupFieldName(expression);
        String valueExpression = lookupValueExpression(expression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public UpdateQuery addValue(String expression, String paramName, boolean paramValue) {
        String fieldName = lookupFieldName(expression);
        String valueExpression = lookupValueExpression(expression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    public UpdateQuery addValue(String expression, String paramName, Date paramValue) {
        String fieldName = lookupFieldName(expression);
        String valueExpression = lookupValueExpression(expression);
        return addValueInternal(fieldName, valueExpression, paramName, paramValue);
    }

    protected UpdateQuery addValueInternal(String fieldName, String valueExpression, String paramName, Object paramValue) {
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

    public UpdateQuery addValueInternal(String expression, Object paramValue) {
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

    public String toSQL() {
        if (!this.dirty) {
            return this.cached;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ").append(this.tableName);
        List<String> field = new LinkedList<>();
        for (Map.Entry<String, String> entry : this.field.entrySet()) {
            field.add(entry.getKey() + " = " + entry.getValue());
        }
        builder.append(" SET ").append(StringUtils.join(field, ", "));
        if (!this.where.isEmpty()) {
            builder.append(" WHERE ").append(StringUtils.join(this.where, " AND "));
        }
        this.dirty = false;
        this.cached = builder.toString();
        return this.cached;
    }

    @Override
    public UpdateQuery addWhere(String criteria) {
        return (UpdateQuery) super.addWhere(criteria);
    }

    @Override
    public UpdateQuery addWhere(String criteria, String paramValue) {
        return (UpdateQuery) super.addWhere(criteria, paramValue);
    }

    @Override
    public UpdateQuery addWhere(String criteria, char paramValue) {
        return (UpdateQuery) super.addWhere(criteria, paramValue);
    }

    @Override
    public UpdateQuery addWhere(String criteria, boolean paramValue) {
        return (UpdateQuery) super.addWhere(criteria, paramValue);
    }

    @Override
    public UpdateQuery addWhere(String criteria, Number paramValue) {
        return (UpdateQuery) super.addWhere(criteria, paramValue);
    }

    @Override
    public UpdateQuery addWhere(String criteria, Date paramValue) {
        return (UpdateQuery) super.addWhere(criteria, paramValue);
    }

    @Override
    public UpdateQuery addWhere(String criteria, String paramName, String paramValue) {
        return (UpdateQuery) super.addWhere(criteria, paramName, paramValue);
    }

    @Override
    public UpdateQuery addWhere(String criteria, String paramName, char paramValue) {
        return (UpdateQuery) super.addWhere(criteria, paramName, paramValue);
    }

    @Override
    public UpdateQuery addWhere(String criteria, String paramName, boolean paramValue) {
        return (UpdateQuery) super.addWhere(criteria, paramName, paramValue);
    }

    @Override
    public UpdateQuery addWhere(String criteria, String paramName, Number paramValue) {
        return (UpdateQuery) super.addWhere(criteria, paramName, paramValue);
    }

    @Override
    public UpdateQuery addWhere(String criteria, String paramName, Date paramValue) {
        return (UpdateQuery) super.addWhere(criteria, paramName, paramValue);
    }

    @Override
    public <T> UpdateQuery addWhere(String criteria, String paramName, Class<T> type, List<T> paramValue) {
        return (UpdateQuery) super.addWhere(criteria, paramName, type, paramValue);
    }

    @Override
    public <T> UpdateQuery addWhere(String criteria, Class<T> type, Entry<T>... paramNameValue) {
        return (UpdateQuery) super.addWhere(criteria, type, paramNameValue);
    }

    @Override
    public <T> UpdateQuery addWhere(String criteria, Class<T> type, Map<String, T> paramNameValue) {
        return (UpdateQuery) super.addWhere(criteria, type, paramNameValue);
    }

    @Override
    public UpdateQuery addWhereInternal(String criteria, Object paramValue) {
        return (UpdateQuery) super.addWhereInternal(criteria, paramValue);
    }

    @Override
    public UpdateQuery addWhereInternal(String criteria, String paramName, Object paramValue) {
        return (UpdateQuery) super.addWhereInternal(criteria, paramName, paramValue);
    }
}