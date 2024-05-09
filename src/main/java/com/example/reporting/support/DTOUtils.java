package com.example.reporting.support;

import com.example.reporting.entity.report.AbstractDSEntity;
import com.example.reporting.model.report.AbstractDSDto;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.util.List;

public final class DTOUtils {

    private DTOUtils() {
        throw new UnsupportedOperationException();
    }

    private static final PropertyUtilsBean propUtils = new PropertyUtilsBean();

    public static <T extends AbstractDSDto, E extends AbstractDSEntity> T mapFields(E source, Class<T> destClass, List<String> fields) {
        T dest;
        try {
            dest = destClass.getDeclaredConstructor().newInstance();
            for (String field : fields) {
                if (propUtils.isReadable(source, field) && propUtils.isWriteable(dest, field)) {
                    Object value = propUtils.getProperty(source, field);
                    propUtils.setProperty(dest, field, value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error mapping fields", e);
        }
        return dest;
    }
}
