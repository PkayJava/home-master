package com.angkorteam.home.query;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class DeleteQuery extends WhereQuery {

    /**
     *
     */
    private static final long serialVersionUID = -7027321930702898882L;

    protected final String tableName;

    public DeleteQuery(String tableName) {
        this.tableName = tableName;
    }

    public String toSQL() {
        if (!this.dirty) {
            return cached;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM ").append(this.tableName);
        if (!this.where.isEmpty()) {
            builder.append(" WHERE ").append(StringUtils.join(this.where, " AND "));
        }
        this.cached = builder.toString();
        this.dirty = false;
        return this.cached;
    }

    @Override
    public DeleteQuery addWhere(String criteria) {
        return (DeleteQuery) super.addWhere(criteria);
    }

    @Override
    public DeleteQuery addWhere(String criteria, String paramValue) {
        return (DeleteQuery) super.addWhere(criteria, paramValue);
    }

    @Override
    public DeleteQuery addWhere(String criteria, char paramValue) {
        return (DeleteQuery) super.addWhere(criteria, paramValue);
    }

    @Override
    public DeleteQuery addWhere(String criteria, boolean paramValue) {
        return (DeleteQuery) super.addWhere(criteria, paramValue);
    }

    @Override
    public DeleteQuery addWhere(String criteria, Number paramValue) {
        return (DeleteQuery) super.addWhere(criteria, paramValue);
    }

    @Override
    public DeleteQuery addWhere(String criteria, Date paramValue) {
        return (DeleteQuery) super.addWhere(criteria, paramValue);
    }

    @Override
    public DeleteQuery addWhere(String criteria, String paramName, String paramValue) {
        return (DeleteQuery) super.addWhere(criteria, paramName, paramValue);
    }

    @Override
    public DeleteQuery addWhere(String criteria, String paramName, char paramValue) {
        return (DeleteQuery) super.addWhere(criteria, paramName, paramValue);
    }

    @Override
    public DeleteQuery addWhere(String criteria, String paramName, boolean paramValue) {
        return (DeleteQuery) super.addWhere(criteria, paramName, paramValue);
    }

    @Override
    public DeleteQuery addWhere(String criteria, String paramName, Number paramValue) {
        return (DeleteQuery) super.addWhere(criteria, paramName, paramValue);
    }

    @Override
    public DeleteQuery addWhere(String criteria, String paramName, Date paramValue) {
        return (DeleteQuery) super.addWhere(criteria, paramName, paramValue);
    }

    @Override
    public DeleteQuery addWhereInternal(String criteria, String paramName, Object paramValue) {
        return (DeleteQuery) super.addWhereInternal(criteria, paramName, paramValue);
    }

    @Override
    public DeleteQuery addWhereInternal(String criteria, Object paramValue) {
        return (DeleteQuery) super.addWhereInternal(criteria, paramValue);
    }

    @Override
    public <T> DeleteQuery addWhere(String criteria, String paramName, Class<T> type, List<T> paramValue) {
        return (DeleteQuery) super.addWhere(criteria, paramName, type, paramValue);
    }

    @Override
    public <T> DeleteQuery addWhere(String criteria, Class<T> type, Entry<T>... paramNameValue) {
        return (DeleteQuery) super.addWhere(criteria, type, paramNameValue);
    }

    @Override
    public <T> DeleteQuery addWhere(String criteria, Class<T> type, Map<String, T> paramNameValue) {
        return (DeleteQuery) super.addWhere(criteria, type, paramNameValue);
    }
}