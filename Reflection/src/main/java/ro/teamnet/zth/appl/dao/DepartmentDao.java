package ro.teamnet.zth.appl.dao;

import ro.teamnet.zth.api.em.EntityManager;
import ro.teamnet.zth.api.em.EntityManagerImpl;
import ro.teamnet.zth.appl.domain.Department;

import java.util.List;

/**
 * Author: Ovidiu
 * Date:   4/30/2015
 */
public class DepartmentDao {

    private EntityManager entityManager;

    public DepartmentDao() {
        entityManager = new EntityManagerImpl();
    }

    public Department findById(Long id) {
        return entityManager.findById(Department.class, id);
    }

    public List<Department> findAll() {
        return entityManager.findAll(Department.class);
    }

    public Department insert(Department department) {
        return entityManager.insert(department);
    }

    public Department update(Department department) {
        return entityManager.update(department);
    }

    public void delete(Department department) {
        entityManager.delete(department);
    }

}
