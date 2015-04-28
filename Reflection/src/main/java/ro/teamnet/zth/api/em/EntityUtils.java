package ro.teamnet.zth.api.em;

import ro.teamnet.zth.api.annotations.Column;
import ro.teamnet.zth.api.annotations.Id;
import ro.teamnet.zth.api.annotations.Table;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Ovidiu
 * Date:   4/28/2015
 *
 * Helper file used for getting information from a table by using annotations.
 */
public class EntityUtils {

    private EntityUtils() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the table name that corresponds to the given entity.
     * If the entity is not mapped to a database table, then it returns its name.
     * @param entity
     * @return database table name
     */
    public static String getTableName(Class entity) {
        // Get table name using the (@Table) annotation
        Annotation annotation = entity.getAnnotation(Table.class);

        // If the entity doesn't have the (@Table) annotation,
        // this reference will be null
        if (annotation != null)
            return ((Table) annotation).name();
        else
            return entity.getSimpleName();
    }

    /**
     * Finds the given entity's fields that are mapped to columns of a database table.
     * @param entity
     * @return list containing all column names of an entity
     */
    public static List<ColumnInfo> getColumns(Class entity) {
        List<ColumnInfo> columns = new ArrayList<ColumnInfo>();

        // Get all declared fields from class
        Field[] fields  = entity.getDeclaredFields();

        Annotation fieldAnnotation;
        for (Field field : fields) {
            // Check if current field has (@Column) annotation
            fieldAnnotation = field.getAnnotation(Column.class);

            if (fieldAnnotation != null) {
                // Create a ColumnInfo object corresponding to the current field/column
                ColumnInfo columnInfo = new ColumnInfo();

                columnInfo.setColumnType(field.getType());
                columnInfo.setColumnName(field.getName());
                columnInfo.setDbName(((Column) fieldAnnotation).name());
                columnInfo.setIsId(false);

                // Store column
                columns.add(columnInfo);
            } else {
                // If the current field doesn't have the (@Column) annotation,
                // then it might have the (@Id) annotation.

                // Check if current field has (@Id) annotation
                fieldAnnotation = field.getAnnotation(Id.class);

                if (fieldAnnotation != null) {
                    // Create a ColumnInfo object corresponding to the current field/column
                    ColumnInfo columnInfo = new ColumnInfo();

                    columnInfo.setColumnType(field.getType());
                    columnInfo.setColumnName(field.getName());
                    columnInfo.setDbName(((Id) fieldAnnotation).name());
                    columnInfo.setIsId(true);

                    // Store column
                    columns.add(columnInfo);
                } else {
                    // The current field doesn't have any of the two annotations:
                    // (@Id) or (@Column)
                }
            }
        }

        return columns;
    }

    /**
     * If the value is stored as BigDecimal in the database, then it casts it to Integer.
     * @param value value stored in database
     * @param wantedType type of value to be used
     * @return an Integer corresponding to the value
     */
    public static Object castFromSqlType(Object value, Class wantedType) {
        if (value instanceof BigDecimal && wantedType == Integer.class)
            return ((BigDecimal) value).intValue();
        else
            return value;
    }

    /**
     * Finds the given class's fields that have the specified annotation.
     * @param pClass
     * @param annotation
     * @return list containing all fields having the specified annotation
     */
    public static List<Field> getFieldsByAnnotatations(Class pClass, Class annotation) {
        List<Field> fields = new ArrayList<>();

        // Get declared fields of "clazz"
        Field[] declaredFields = pClass.getDeclaredFields();

        // Find and store declared fields that have the specified annotation
        for (Field declaredField : declaredFields) {
            if (declaredField.getAnnotation(annotation) != null)
                fields.add(declaredField);
        }

        return fields;
    }

    /*
    public static Object getSqlValue(Object object) {

    }
    */

}