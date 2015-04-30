package ro.teamnet.zth.appl;

import org.junit.Assert;
import org.junit.Test;
import ro.teamnet.zth.appl.dao.LocationDao;
import ro.teamnet.zth.appl.domain.Location;

/**
 * Author: Ovidiu
 * Date:   4/30/2015
 */
public class LocationDaoTest {

    @Test
    public void testFindById() {
        Long locationId = new Long(1700);
        Location location = new Location();
        location.setId(locationId);
        location.setStreetAddress("2004 Charade Rd");
        location.setPostalCode("98199");
        location.setCity("Seattle");
        location.setStateProvince("Washington");

        LocationDao locationDao = new LocationDao();
        Assert.assertEquals("Error retrieving department record!", location, locationDao.findById(locationId));
    }

    @Test
    public void testFindAll() {
        int expectedNumberOfLocations = 26;

        LocationDao locationDao = new LocationDao();
        Assert.assertEquals("Number of results doesn't match!", expectedNumberOfLocations, locationDao.findAll().size());
    }

    @Test
    public void testInsert() {
        Location location = new Location();
        location.setStreetAddress("Bd. Castanilor");
        location.setPostalCode("100523");
        location.setCity("Ploiesti");
        location.setStateProvince("Prahova");

        LocationDao locationDao = new LocationDao();
        Assert.assertEquals("Error inserting record!", location.getCity(), locationDao.insert(location).getCity());
    }

    @Test
    public void testUpdate() {
        Location location = new Location();
        location.setId(new Long(4003));
        location.setStreetAddress("Str. Merilor");
        location.setPostalCode("400230");
        location.setCity("Predeal");
        location.setStateProvince("Brasov");

        LocationDao locationDao = new LocationDao();
        Assert.assertEquals("Error updating record!", location, locationDao.update(location));
    }

    @Test
    public void testDelete() {
        Location location = new Location();
        location.setId(new Long(4003));
        location.setStreetAddress("Str. Merilor");
        location.setPostalCode("400230");
        location.setCity("Predeal");
        location.setStateProvince("Brasov");

        LocationDao locationDao = new LocationDao();
        locationDao.delete(location);

        Assert.assertNull("Record not deleted!", locationDao.findById(new Long(4003)));
    }

}
