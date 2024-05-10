package com.example.reporting.support;

import com.example.reporting.entity.report.AbstractDSEntity;
import com.example.reporting.exception.BadRequestException;
import com.example.reporting.model.report.AbstractDSDto;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.util.List;

public final class DTOUtils {

    private DTOUtils() {
        throw new UnsupportedOperationException();
    }

    private static final PropertyUtilsBean PROP_UTILS = new PropertyUtilsBean();

    public static <T extends AbstractDSDto, E extends AbstractDSEntity> T mapFields(E source, Class<T> destClass, List<String> fields) {
        T dest;
        try {
            dest = ClassUtils.newInstance(destClass);
            for (String field : fields) {
                if (PROP_UTILS.isReadable(source, field) && PROP_UTILS.isWriteable(dest, field)) {
                    Object value = PROP_UTILS.getProperty(source, field);
                    PROP_UTILS.setProperty(dest, field, value);
                }
            }
        } catch (Exception e) {
            throw new BadRequestException("Error mapping fields", e);
        }
        return dest;
    }
}
