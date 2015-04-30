package ro.teamnet.zth.appl.dao;

import ro.teamnet.zth.api.em.EntityManager;
import ro.teamnet.zth.api.em.EntityManagerImpl;
import ro.teamnet.zth.appl.domain.Location;

import java.util.List;

/**
 * Author: Ovidiu
 * Date:   4/30/2015
 */
public class LocationDao {

    private EntityManager entityManager;

    public LocationDao() {
        entityManager = new EntityManagerImpl();
    }

    public Location findById(Long id) {
        return entityManager.findById(Location.class, id);
    }

    public List<Location> findAll() {
        return entityManager.findAll(Location.class);
    }

    public Location insert(Location location) {
        return entityManager.insert(location);
    }

    public Location update(Location location) {
        return entityManager.update(location);
    }

    public void delete(Location location) {
        entityManager.delete(location);
    }

}
