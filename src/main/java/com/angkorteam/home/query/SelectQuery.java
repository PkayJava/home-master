package com.angkorteam.home.query;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class SelectQuery extends WhereQuery {

    /**
     *
     */
    private static final long serialVersionUID = -7467608881443340145L;

    protected final String tableName;

    protected final List<String> field = new LinkedList<>();
    protected final List<String> join = new LinkedList<>();
    protected final List<String> orderBy = new LinkedList<>();
    protected final List<String> having = new LinkedList<>();
    protected final List<String> groupBy = new LinkedList<>();

    protected boolean pagination = false;
    protected long offset;
    protected long number;
    protected final boolean forUpdate;

    public SelectQuery(String tableName) {
        this(tableName, false);
    }

    public SelectQuery(String tableName, boolean forUpdate) {
        this.tableName = tableName;
        this.forUpdate = forUpdate;
    }

    public SelectQuery setOffset(Long offset) {
        this.offset = offset;
        this.dirty = true;
        this.pagination = true;
        return this;
    }

    public Long getOffset() {
        return this.offset;
    }

    public SelectQuery setNumber(long number) {
        this.number = number;
        this.dirty = true;
        this.pagination = true;
        return this;
    }

    public Long getNumber() {
        return this.number;
    }

    public SelectQuery addField(String field) {
        this.field.add(field);
        this.dirty = true;
        return this;
    }

    public SelectQuery addField(String... fields) {
        if (fields != null && fields.length > 0) {
            for (String field : fields) {
                addField(field);
            }
            this.dirty = true;
        }
        return this;
    }

    /**
     * criteria is raw text, example name = 'student'
     * noted - name = :student will be mis spell and will have error when perform the query
     *
     * @param criteria
     * @return
     */
    public SelectQuery addHaving(String criteria) {
        this.having.add(criteria);
        this.dirty = true;
        return this;
    }

    /**
     * this support a single parameter
     * in the case you have a complex criteria see {@link WhereQuery#addWhere(String, String, String)}
     *
     * @param criteria   example name = :student
     * @param paramValue Syron
     * @return
     */
    public SelectQuery addHaving(String criteria, String paramValue) {
        String paramName = lookupParamName(criteria);
        return addHavingInternal(criteria, paramName, paramValue);
    }

    /**
     * this support a single parameter
     * in the case you have a complex criteria see {@link WhereQuery#addWhere(String, String, char)}
     *
     * @param criteria   example name = :student
     * @param paramValue 'c'
     * @return
     */
    public SelectQuery addHaving(String criteria, char paramValue) {
        String paramName = lookupParamName(criteria);
        return addHavingInternal(criteria, paramName, paramValue);
    }

    /**
     * this support a single parameter
     * in the case you have a complex criteria see {@link WhereQuery#addWhere(String, String, boolean)}
     *
     * @param criteria   example name = :student
     * @param paramValue true
     * @return
     */
    public SelectQuery addHaving(String criteria, boolean paramValue) {
        String paramName = lookupParamName(criteria);
        return addHavingInternal(criteria, paramName, paramValue);
    }

    /**
     * this support a single parameter
     * in the case you have a complex criteria see {@link WhereQuery#addWhere(String, String, Number)}
     *
     * @param criteria   example name = :student
     * @param paramValue 1
     * @return
     */
    public SelectQuery addHaving(String criteria, Number paramValue) {
        String paramName = lookupParamName(criteria);
        return addHavingInternal(criteria, paramName, paramValue);
    }

    /**
     * this support a single parameter
     * in the case you have a complex criteria see {@link WhereQuery#addWhere(String, String, Date)}
     *
     * @param criteria   example name = :student
     * @param paramValue new Date
     * @return
     */
    public SelectQuery addHaving(String criteria, Date paramValue) {
        String paramName = lookupParamName(criteria);
        return addHavingInternal(criteria, paramName, paramValue);
    }

    /**
     * criteria and paramName must be carefully verified each other
     *
     * @param criteria   name = :student
     * @param paramName  student
     * @param paramValue Sryon
     * @return
     */
    public SelectQuery addHaving(String criteria, String paramName, String paramValue) {
        return addHavingInternal(criteria, paramName, paramValue);
    }

    /**
     * criteria and paramName must be carefully verified each other
     *
     * @param criteria   name = :student
     * @param paramName  student
     * @param paramValue C
     * @return
     */
    public SelectQuery addHaving(String criteria, String paramName, char paramValue) {
        return addHavingInternal(criteria, paramName, paramValue);
    }

    /**
     * criteria and paramName must be carefully verified each other
     *
     * @param criteria   name = :student
     * @param paramName  student
     * @param paramValue true / false
     * @return
     */
    public SelectQuery addHaving(String criteria, String paramName, boolean paramValue) {
        return addHavingInternal(criteria, paramName, paramValue);
    }

    /**
     * criteria and paramName must be carefully verified each other
     *
     * @param criteria   name = :student
     * @param paramName  student
     * @param paramValue 1
     * @return
     */
    public SelectQuery addHaving(String criteria, String paramName, Number paramValue) {
        return addHavingInternal(criteria, paramName, paramValue);
    }

    /**
     * criteria and paramName must be carefully verified each other
     *
     * @param criteria   name = :student
     * @param paramName  student
     * @param paramValue new Date()
     * @return
     */
    public SelectQuery addHaving(String criteria, String paramName, Date paramValue) {
        return addHavingInternal(criteria, paramName, paramValue);
    }

    /**
     * criteria and paramName must be carefully verified each other
     *
     * @param criteria   name = :student
     * @param paramName  student
     * @param paramValue 1
     * @return
     */
    protected SelectQuery addHavingInternal(String criteria, String paramName, Object paramValue) {
        if (paramName == null || "".equals(paramName)) {
            throw new IllegalArgumentException("paramName is required");
        }
        if (paramValue == null) {
            throw new IllegalArgumentException("paramValue value is required");
        }
        this.having.add(criteria);
        this.param.put(paramName, paramValue);
        this.dirty = true;
        return this;
    }

    /**
     * criteria and paramName must be carefully verified each other
     *
     * @param criteria   example name in (:student)
     * @param type       String/Number/Character/Boolean/Date are supported
     * @param paramName  student
     * @param paramValue
     * @return
     */
    public <T> WhereQuery addHaving(String criteria, String paramName, Class<T> type, List<T> paramValue) {
        if (type == null) {
            throw new IllegalArgumentException("type is required");
        }
        if (type != String.class
                && type != char.class && type != Character.class
                && type != boolean.class && type != Boolean.class
                && type != Date.class
                && type != Number.class
                && type != byte.class && type != Byte.class
                && type != short.class && type != Short.class
                && type != int.class && type != Integer.class
                && type != long.class && type != Long.class
                && type != float.class && type != Float.class
                && type != double.class && type != Double.class
                && type != BigInteger.class
                && type != BigDecimal.class
        ) {
            throw new IllegalArgumentException("type is invalid");
        }
        return addHavingInternal(criteria, paramName, paramValue);
    }

    /**
     * criteria and paramNameValue must be carefully verified each other
     *
     * @param criteria       score is between :min and :max
     * @param type           String/Number/Boolean/Character
     * @param paramNameValue min=>1, max=>10
     * @param <T>
     * @return
     */
    public <T> WhereQuery addHaving(String criteria, Class<T> type, Entry<T>... paramNameValue) {
        Map<String, T> params = new HashMap<>();
        if (paramNameValue != null && paramNameValue.length > 0) {
            for (Entry<T> param : paramNameValue) {
                params.put(param.getKey(), param.getValue());
            }
        }
        return addHaving(criteria, type, params);
    }

    /**
     * criteria and paramNameValue must be carefully verified each other
     *
     * @param criteria       score is between :min and :max
     * @param type           String/Number/Boolean/Character
     * @param paramNameValue min=>1, max=>10
     * @param <T>
     * @return
     */
    public <T> WhereQuery addHaving(String criteria, Class<T> type, Map<String, T> paramNameValue) {
        if (type == null) {
            throw new IllegalArgumentException("type is required");
        }
        if (type != String.class
                && type != char.class && type != Character.class
                && type != boolean.class && type != Boolean.class
                && type != Date.class
                && type != Number.class
                && type != byte.class && type != Byte.class
                && type != short.class && type != Short.class
                && type != int.class && type != Integer.class
                && type != long.class && type != Long.class
                && type != float.class && type != Float.class
                && type != double.class && type != Double.class
                && type != BigInteger.class
                && type != BigDecimal.class
        ) {
            throw new IllegalArgumentException("type is invalid");
        }
        if (paramNameValue == null || paramNameValue.isEmpty()) {
            throw new IllegalArgumentException("paramNameValue is required");
        }
        this.having.add(criteria);
        for (Map.Entry<String, T> entry : paramNameValue.entrySet()) {
            if (entry.getKey() == null || "".equals(entry.getKey())) {
                throw new IllegalArgumentException("paramName is empty");
            }
            if (entry.getValue() == null) {
                throw new IllegalArgumentException(entry.getKey() + " value is required");
            }
            this.param.put(entry.getKey(), entry.getValue());
        }
        this.dirty = true;
        return this;
    }

    public SelectQuery addOrderBy(String orderBy) {
        this.orderBy.add(orderBy);
        this.dirty = true;
        return this;
    }

    public SelectQuery addOrderBy(String field, String sort) {
        this.orderBy.add(field + " " + sort);
        this.dirty = true;
        return this;
    }

    public SelectQuery addGroupBy(String field) {
        this.groupBy.add(field);
        this.dirty = true;
        return this;
    }

    public SelectQuery addJoin(String join) {
        this.join.add(join);
        return this;
    }

    public SelectQuery setLimit(Long offset, Long number) {
        this.offset = offset;
        this.number = number;
        this.dirty = true;
        this.pagination = true;
        return this;
    }

    public boolean isForUpdate() {
        return forUpdate;
    }

    public String toSQL() {
        if (!this.dirty) {
            return this.cached;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        if (this.field.isEmpty()) {
            this.field.add("*");
        }
        builder.append(StringUtils.join(this.field, ", "));
        builder.append(" FROM ");
        builder.append(this.tableName);
        if (!this.join.isEmpty()) {
            builder.append(" ").append(StringUtils.join(this.join, " "));
        }
        if (!this.where.isEmpty()) {
            builder.append(" WHERE ").append(StringUtils.join(this.where, " AND "));
        }
        if (!this.groupBy.isEmpty()) {
            builder.append(" GROUP BY ").append(StringUtils.join(this.groupBy, ", "));
        }
        if (!this.having.isEmpty()) {
            builder.append(" HAVING ").append(StringUtils.join(this.having, " AND "));
        }
        if (!this.orderBy.isEmpty()) {
            builder.append(" ORDER BY ").append(StringUtils.join(this.orderBy, ", "));
        }
        if (this.pagination) {
            builder.append(" LIMIT ").append(this.offset).append(",").append(this.number);
        }
        if (this.forUpdate) {
            builder.append(" FOR UPDATE");
        }
        this.cached = builder.toString();
        this.dirty = false;
        return this.cached;
    }

    @Override
    public SelectQuery addWhere(String criteria) {
        return (SelectQuery) super.addWhere(criteria);
    }

    @Override
    public SelectQuery addWhere(String criteria, String paramValue) {
        return (SelectQuery) super.addWhere(criteria, paramValue);
    }

    @Override
    public SelectQuery addWhere(String criteria, char paramValue) {
        return (SelectQuery) super.addWhere(criteria, paramValue);
    }

    @Override
    public SelectQuery addWhere(String criteria, boolean paramValue) {
        return (SelectQuery) super.addWhere(criteria, paramValue);
    }

    @Override
    public SelectQuery addWhere(String criteria, Number paramValue) {
        return (SelectQuery) super.addWhere(criteria, paramValue);
    }

    @Override
    public SelectQuery addWhere(String criteria, Date paramValue) {
        return (SelectQuery) super.addWhere(criteria, paramValue);
    }

    @Override
    public SelectQuery addWhere(String criteria, String paramName, String paramValue) {
        return (SelectQuery) super.addWhere(criteria, paramName, paramValue);
    }

    @Override
    public SelectQuery addWhere(String criteria, String paramName, char paramValue) {
        return (SelectQuery) super.addWhere(criteria, paramName, paramValue);
    }

    @Override
    public SelectQuery addWhere(String criteria, String paramName, boolean paramValue) {
        return (SelectQuery) super.addWhere(criteria, paramName, paramValue);
    }

    @Override
    public SelectQuery addWhere(String criteria, String paramName, Number paramValue) {
        return (SelectQuery) super.addWhere(criteria, paramName, paramValue);
    }

    @Override
    public SelectQuery addWhere(String criteria, String paramName, Date paramValue) {
        return (SelectQuery) super.addWhere(criteria, paramName, paramValue);
    }

    @Override
    public <T> SelectQuery addWhere(String criteria, String paramName, Class<T> type, List<T> paramValue) {
        return (SelectQuery) super.addWhere(criteria, paramName, type, paramValue);
    }

    @Override
    public <T> SelectQuery addWhere(String criteria, Class<T> type, Entry<T>... paramNameValue) {
        return (SelectQuery) super.addWhere(criteria, type, paramNameValue);
    }

    @Override
    public <T> SelectQuery addWhere(String criteria, Class<T> type, Map<String, T> paramNameValue) {
        return (SelectQuery) super.addWhere(criteria, type, paramNameValue);
    }

    @Override
    public SelectQuery addWhereInternal(String criteria, Object paramValue) {
        return (SelectQuery) super.addWhereInternal(criteria, paramValue);
    }

    @Override
    public SelectQuery addWhereInternal(String criteria, String paramName, Object paramValue) {
        return (SelectQuery) super.addWhereInternal(criteria, paramName, paramValue);
    }
}