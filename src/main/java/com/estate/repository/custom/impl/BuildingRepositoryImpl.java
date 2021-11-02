package com.estate.repository.custom.impl;

import com.estate.builder.BuildingSearchBuilder;
import com.estate.entity.BuildingEntity;
import com.estate.repository.custom.BuildingRepositoryCustom;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.apache.commons.lang.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.data.domain.Pageable;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BuildingRepositoryImpl  implements BuildingRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BuildingEntity> findByCondition(BuildingSearchBuilder builder , Pageable pageable) {
       try {
           StringBuilder sql = new StringBuilder("select * from building as b  where 1 = 1 ");
           createWhereClausePart1(builder, sql);
           createWhereClausePart2(builder, sql);
           Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
           if (pageable != null){
               query.setFirstResult((int) pageable.getOffset());
               query.setMaxResults(pageable.getPageSize());
           }
           return query.getResultList();
       }catch (Exception e){
           System.out.println(e);
       }
       return  new ArrayList<>();
    }

    @Override
    public Long count(BuildingSearchBuilder builder) {
        try {
            StringBuilder sql = new StringBuilder("select COUNT(*) from building as b  where 1 = 1");
            createWhereClausePart1(builder, sql);
            createWhereClausePart2(builder, sql);
            Query query = entityManager.createNativeQuery(sql.toString());
            List<BigInteger> resultList = query.getResultList();
            if(resultList.size() != 0) {
                return Long.parseLong(resultList.get(0).toString(), 10);
            } else {
                return 0L;
            }
        }catch (Exception e){
            System.out.println(e);
        }
       return null;
    }

    private void createWhereClausePart1(BuildingSearchBuilder builder, StringBuilder sql) {
        try{
            Field[] fields = builder.getClass().getDeclaredFields();
            for (Field field : fields){
                field.setAccessible(true);
                String name = field.getName();
                Object objectValue = field.get(builder);
                if (!name.equals("buildingTypes") && !name.equals("staffId")
                        && !name.startsWith("areaRent") && !name.startsWith("costRent")){
                    if (field.getType().equals(String.class)) {
                        String value = (String) objectValue;
                        if (StringUtils.isNotBlank(value)) {
                            sql.append("AND b." + name + " LIKE '%" + objectValue + "%' \n");
                        }
                    } else if (field.getType().equals(Integer.class) || field.getType().equals(Double.class)) {
                        if (objectValue != null) {
                            sql.append("AND b." + name + " = " + objectValue + " \n");
                        }
                    }
                }
            }

        }catch (Exception e){
            throw new InternalException(e.getMessage());
        }
    }

    private void createWhereClausePart2(BuildingSearchBuilder builder, StringBuilder sql) {
        if (builder.getStaffId() != null) {
            sql.append("AND EXISTS (SELECT * FROM assignment_building ab WHERE ab.building_id = b.id AND ab.user_id = "
                    + builder.getStaffId() + " )");
        }
        if (builder.getCostRentFrom() != null || builder.getCostRentTo() != null) {
            sql.append(buildBetweenQuery("b.rentprice", builder.getCostRentFrom(), builder.getCostRentTo()));
            sql.append("\n");
        }

        if (builder.getAreaRentFrom() != null || builder.getAreaRentTo() != null) {
            sql.append("AND EXISTS (SELECT * FROM rentarea ra WHERE ra.building_id = b.id ");
            sql.append(buildBetweenQuery("ra.value", builder.getAreaRentFrom(), builder.getAreaRentTo()));
            sql.append(")\n");
        }
        if (builder.getBuildingTypes() != null && builder.getBuildingTypes().length > 0) {
            sql.append(" and ( ");
            String typesSQL = Arrays.stream(builder.getBuildingTypes())
                    .map(item -> "b.type like '%" + item + "%' ")
                    .collect(Collectors.joining("or "));
            sql.append(typesSQL);
            sql.append(")");
        }
    }

    private String buildBetweenQuery(String column, Object from, Object to) {
        if (from == null && to == null) return "";
        else {
            if (from == null) from = 0;
            if (to == null) {
                if (from.getClass().equals(Integer.class)) {
                    to = Integer.MAX_VALUE;
                } else if (from.getClass().equals(Double.class)) {
                    to = Double.MAX_VALUE;
                }
            }
            return String.format(" AND %s BETWEEN %s AND %s", column, from, to);
        }

    }


}
