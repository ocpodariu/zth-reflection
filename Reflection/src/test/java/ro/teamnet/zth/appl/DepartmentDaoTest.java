package ro.teamnet.zth.appl;

import org.junit.Assert;
import org.junit.Test;
import ro.teamnet.zth.appl.dao.DepartmentDao;
import ro.teamnet.zth.appl.domain.Department;

/**
 * Author: Ovidiu
 * Date:   4/30/2015
 */
public class DepartmentDaoTest {

    @Test
    public void testFindById() {
        Long departmentId = new Long(10);
        Department department = new Department();
        department.setId(departmentId);
        department.setDepartmentName("Administration");
        department.setLocation(new Long(1700));

        DepartmentDao departmentDao = new DepartmentDao();
        Assert.assertEquals("Error retrieving department record!", department, departmentDao.findById(departmentId));
    }

    @Test
    public void testFindAll() {
        int expectedNumberOfDepartments = 27;

        DepartmentDao departmentDao = new DepartmentDao();
        Assert.assertEquals("Number of results doesn't match!", expectedNumberOfDepartments, departmentDao.findAll().size());
    }

    @Test
    public void testInsert() {
        Department department = new Department();
        department.setDepartmentName("ZeroToHero");
        department.setLocation(new Long(1700));

        DepartmentDao departmentDao = new DepartmentDao();
        Assert.assertEquals("Error inserting record!", department.getDepartmentName(), departmentDao.insert(department).getDepartmentName());
    }

    @Test
    public void testUpdate() {
        Department department = new Department();
        department.setId(new Long(271));
        department.setDepartmentName("ZeroToHero-JAVA");
        department.setLocation(new Long(1700));

        DepartmentDao departmentDao = new DepartmentDao();
        Assert.assertEquals("Error updating record!", department, departmentDao.update(department));
    }

    @Test
    public void testDelete() {
        Department department = new Department();
        department.setId(new Long(271));
        department.setDepartmentName("ZeroToHero-JAVA");
        department.setLocation(new Long(1700));

        DepartmentDao departmentDao = new DepartmentDao();
        departmentDao.delete(department);

        Assert.assertNull("Record not deleted!", departmentDao.findById(new Long(271)));
    }

}
