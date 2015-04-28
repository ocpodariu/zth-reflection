package ro.teamnet.zth.api.em;

import org.junit.Assert;
import org.junit.Test;
import ro.teamnet.zth.api.annotations.Column;
import ro.teamnet.zth.appl.domain.Department;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Ovidiu
 * Date:   4/28/2015
 */
public class EntityUtilsTest {

    @Test
    public void testGetTableNameMethod() {
        System.out.println("Testing EntityUtils.getTableName()... ");

        Department department = new Department();
        String tableName = EntityUtils.getTableName(Department.class);

        Assert.assertEquals("Table name should be departments!", "Departments", tableName);
    }

    @Test
    public void testGetColumns() {
        System.out.println("Testing EntityUtils.getColumns()... ");

        List<ColumnInfo> expectedColumns = new ArrayList<ColumnInfo>();

        ColumnInfo c;
        c = new ColumnInfo();
        c.setColumnName("id"); c.setColumnType(Long.class); c.setDbName("department_id"); c.setIsId(true);
        expectedColumns.add(c);

        c = new ColumnInfo();
        c.setColumnName("departmentName"); c.setColumnType(Long.class); c.setDbName("department_name"); c.setIsId(false);
        expectedColumns.add(c);

        c = new ColumnInfo();
        c.setColumnName("location"); c.setColumnType(Long.class); c.setDbName("location_id"); c.setIsId(false);
        expectedColumns.add(c);

        List<ColumnInfo> columns = EntityUtils.getColumns(Department.class);

        // System.out.println("Columns: " + columns);
        // System.out.println("Expected columns: " + expectedColumns);

        Assert.assertEquals("Incorrect number of retrieved columns!", columns.size(), expectedColumns.size());
    }

    @Test
    public void testCastFromSqlType() {
        System.out.println("Testing EntityUtils.castFromSqlType()... ");

        BigDecimal bigDecimal = new BigDecimal(1234);
        Assert.assertEquals("Returned type should be Integer!",
                EntityUtils.castFromSqlType(bigDecimal, Integer.class).getClass(), Integer.class);
    }

    @Test
    public void testGetFieldsByAnnotations() {
        System.out.println("Testing EntityUtils.getFieldsByAnnotations()... ");

        List<Field> expectedFields = new ArrayList<Field>();

        try {
            expectedFields.add(Department.class.getDeclaredField("departmentName"));
            expectedFields.add(Department.class.getDeclaredField("location"));
        } catch (NoSuchFieldException e) {

        }

        List<Field> fields = EntityUtils.getFieldsByAnnotatations(Department.class, Column.class);

        // System.out.println("Fields: " + fields);
        // System.out.println("Expected fields: " + expectedFields);

        Assert.assertEquals("Incorrect number of retrieved fields!", fields.size(), expectedFields.size());
    }
}