package com.angkorteam.home.query;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public abstract class WhereQuery extends ParamQuery {

    /**
     *
     */
    private static final long serialVersionUID = 3458379584516491058L;

    protected final List<String> where = new LinkedList<>();

    /**
     * criteria is raw text, example name = 'student'
     * noted - name = :student will be mis spell and will have error when perform the query
     *
     * @param criteria
     * @return
     */
    protected WhereQuery addWhere(String criteria) {
        this.where.add(criteria);
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
    protected WhereQuery addWhere(String criteria, String paramValue) {
        String paramName = lookupParamName(criteria);
        return addWhereInternal(criteria, paramName, paramValue);
    }

    /**
     * this support a single parameter
     * in the case you have a complex criteria see {@link WhereQuery#addWhere(String, String, char)}
     *
     * @param criteria   example name = :student
     * @param paramValue 'c'
     * @return
     */
    protected WhereQuery addWhere(String criteria, char paramValue) {
        String paramName = lookupParamName(criteria);
        return addWhereInternal(criteria, paramName, paramValue);
    }

    /**
     * this support a single parameter
     * in the case you have a complex criteria see {@link WhereQuery#addWhere(String, String, boolean)}
     *
     * @param criteria   example name = :student
     * @param paramValue true
     * @return
     */
    protected WhereQuery addWhere(String criteria, boolean paramValue) {
        String paramName = lookupParamName(criteria);
        return addWhereInternal(criteria, paramName, paramValue);
    }

    /**
     * this support a single parameter
     * in the case you have a complex criteria see {@link WhereQuery#addWhere(String, String, Number)}
     *
     * @param criteria   example name = :student
     * @param paramValue 1
     * @return
     */
    protected WhereQuery addWhere(String criteria, Number paramValue) {
        String paramName = lookupParamName(criteria);
        return addWhereInternal(criteria, paramName, paramValue);
    }

    /**
     * this support a single parameter
     * in the case you have a complex criteria see {@link WhereQuery#addWhere(String, String, Date)}
     *
     * @param criteria   example name = :student
     * @param paramValue new Date
     * @return
     */
    protected WhereQuery addWhere(String criteria, Date paramValue) {
        String paramName = lookupParamName(criteria);
        return addWhereInternal(criteria, paramName, paramValue);
    }

    /**
     * criteria and paramName must be carefully verified each other
     *
     * @param criteria   name = :student
     * @param paramName  student
     * @param paramValue Sryon
     * @return
     */
    protected WhereQuery addWhere(String criteria, String paramName, String paramValue) {
        return addWhereInternal(criteria, paramName, paramValue);
    }

    /**
     * criteria and paramName must be carefully verified each other
     *
     * @param criteria   name = :student
     * @param paramName  student
     * @param paramValue C
     * @return
     */
    protected WhereQuery addWhere(String criteria, String paramName, char paramValue) {
        return addWhereInternal(criteria, paramName, paramValue);
    }

    /**
     * criteria and paramName must be carefully verified each other
     *
     * @param criteria   name = :student
     * @param paramName  student
     * @param paramValue true / false
     * @return
     */
    protected WhereQuery addWhere(String criteria, String paramName, boolean paramValue) {
        return addWhereInternal(criteria, paramName, paramValue);
    }

    /**
     * criteria and paramName must be carefully verified each other
     *
     * @param criteria   name = :student
     * @param paramName  student
     * @param paramValue 1
     * @return
     */
    protected WhereQuery addWhere(String criteria, String paramName, Number paramValue) {
        return addWhereInternal(criteria, paramName, paramValue);
    }

    /**
     * criteria and paramName must be carefully verified each other
     *
     * @param criteria   name = :student
     * @param paramName  student
     * @param paramValue new Date()
     * @return
     */
    protected WhereQuery addWhere(String criteria, String paramName, Date paramValue) {
        return addWhereInternal(criteria, paramName, paramValue);
    }

    /**
     * this support a single parameter
     * in the case you have a complex criteria see {@link WhereQuery#addWhere(String, String, String)}
     *
     * @param criteria   example name = :student
     * @param paramValue Syron
     * @return
     */
    protected WhereQuery addWhereInternal(String criteria, Object paramValue) {
        String paramName = lookupParamName(criteria);
        return addWhereInternal(criteria, paramName, paramValue);
    }

    /**
     * criteria and paramName must be carefully verified each other
     *
     * @param criteria   name = :student
     * @param paramName  student
     * @param paramValue 1
     * @return
     */
    protected WhereQuery addWhereInternal(String criteria, String paramName, Object paramValue) {
        if (paramName == null || "".equals(paramName)) {
            throw new IllegalArgumentException("paramName is required");
        }
        if (paramValue == null) {
            throw new IllegalArgumentException("paramValue value is required");
        }
        this.where.add(criteria);
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
    protected <T> WhereQuery addWhere(String criteria, String paramName, Class<T> type, List<T> paramValue) {
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
        return addWhereInternal(criteria, paramName, paramValue);
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
    protected <T> WhereQuery addWhere(String criteria, Class<T> type, Entry<T>... paramNameValue) {
        Map<String, T> params = new HashMap<>();
        if (paramNameValue != null && paramNameValue.length > 0) {
            for (Entry<T> param : paramNameValue) {
                params.put(param.getKey(), param.getValue());
            }
        }
        return addWhere(criteria, type, params);
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
    protected <T> WhereQuery addWhere(String criteria, Class<T> type, Map<String, T> paramNameValue) {
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
        this.where.add(criteria);
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

}