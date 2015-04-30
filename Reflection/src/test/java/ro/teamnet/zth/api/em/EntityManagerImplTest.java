package ro.teamnet.zth.api.em;

import org.junit.Assert;
import org.junit.Test;
import ro.teamnet.zth.appl.domain.Employee;
import ro.teamnet.zth.appl.domain.Location;

import java.util.List;

/**
 * Author: Ovidiu
 * Date:   4/30/2015
 */
public class EntityManagerImplTest {

    @Test
    public void testFindById() {
        String expectedFirstName = "Steven";
        String expectedLastName = "King";
        Long employeeId = new Long(100);

        EntityManager entityManager = new EntityManagerImpl();
        Employee employee = entityManager.findById(Employee.class, employeeId);

        Assert.assertEquals("First name doesn't match!", expectedFirstName, employee.getFirstName());
        Assert.assertEquals("Last name doesn't match!", expectedLastName, employee.getLastName());
    }

    @Test
    public void testFindAll() {
        int expectedNumberOfEmployees = 107;

        EntityManager entityManager = new EntityManagerImpl();
        List<Employee> employees = entityManager.findAll(Employee.class);

        Assert.assertEquals("Number of results doesn't match!", expectedNumberOfEmployees, employees.size());
    }

    @Test
    public void testInsert() {
        Location location = new Location();
        location.setStreetAddress("Bd. Timisoara");
        location.setPostalCode("501471");
        location.setCity("Bucuresti");
        location.setStateProvince("Bucuresti");

        EntityManager entityManager = new EntityManagerImpl();
        Assert.assertEquals("Error inserting record!", location.getPostalCode(), entityManager.insert(location).getPostalCode());
    }

    @Test
    public void testUpdate() {
        Location location = new Location();
        location.setId(new Long(4001));
        location.setStreetAddress("Bd. Castanilor");
        location.setPostalCode("143901");
        location.setCity("Ploiesti");
        location.setStateProvince("Prahova");

        EntityManager entityManager = new EntityManagerImpl();
        Assert.assertEquals("Error updating record!", location, entityManager.update(location));
    }

    @Test
    public void testDelete() {
        Location location = new Location();
        location.setId(new Long(4001));
        location.setStreetAddress("Bd. Castanilor");
        location.setPostalCode("143901");
        location.setCity("Ploiesti");
        location.setStateProvince("Prahova");

        EntityManager entityManager = new EntityManagerImpl();
        entityManager.delete(location);

        Assert.assertNull("Record not deleted!", entityManager.findById(Location.class, new Long(4001)));
    }

}
