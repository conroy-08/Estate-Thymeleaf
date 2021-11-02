package com.estate.repository.custom.impl;

import com.estate.builder.CustomerSearchBuilder;
import com.estate.entity.CustomerEntity;
import com.estate.repository.CustomerRepository;
import com.estate.repository.custom.CustomerRepositoryCustom;
import org.apache.commons.lang.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepositoryCustom {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CustomerEntity> findByCondition(CustomerSearchBuilder builder) {
        StringBuilder sql = new StringBuilder("select  * from customer as c where 1 = 1 ");
        createWhereClause(builder, sql);
        Query query = entityManager.createNativeQuery(sql.toString(), CustomerEntity.class);
        return query.getResultList();

    }

    private void createWhereClause(CustomerSearchBuilder builder, StringBuilder sql) {
        if (builder.getStaffId() != null) {
            sql.append("AND EXISTS (SELECT * FROM assignment_customer ac WHERE ac.customer_id = c.id AND ac.user_id = "
                    + builder.getStaffId() + " )");
        }

        try {
            Field[] fields = CustomerSearchBuilder.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                Object objectValue = field.get(builder);
                if (!name.equals("staffId")) {
                    if (field.getType().equals(String.class)) {
                        String value = (String) objectValue;
                        if (StringUtils.isNotBlank(value)) {
                            sql.append("AND c." + name + " LIKE '%" + objectValue + "%' \n");
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
