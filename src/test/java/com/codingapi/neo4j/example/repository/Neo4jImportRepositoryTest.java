package com.codingapi.neo4j.example.repository;

import com.codingapi.neo4j.example.em.Gender;
import com.codingapi.neo4j.example.entity.Depart;
import com.codingapi.neo4j.example.entity.Employee;
import com.codingapi.neo4j.example.entity.Unit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Rollback(value = false)
class Neo4jImportRepositoryTest {

    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private DepartRepository departRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    private static final int UNIT_COUNT = 1000;
    private static final int DEPART_COUNT = 10000;
    private static final int EMPLOYEE_COUNT = 10_0000;



    @Order(0)
    @Test
    void beforeTest() {
        unitRepository.deleteAll();
        departRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Order(1)
    @Test
    void importUnit() {
        List<Unit> units = new ArrayList<>();
        for (int i = 0; i < UNIT_COUNT; i++) {
            long index = i + 1;
            Unit unit = new Unit();
            unit.setId(index);
            unit.setName("u" + index);
            unit.setAddress("address" + i);

            units.add(unit);
        }

        unitRepository.saveAll(units);
    }

    @Order(2)
    @Test
    void importDepart() {
        ExecutorService executors = Executors.newFixedThreadPool(80);
        final Random random = new Random();
        for (int i = 0; i < DEPART_COUNT; i++) {
            final long index = i + 1;

            executors.execute(() -> {
                long unitId = random.nextLong(UNIT_COUNT) + 1;
                Unit unit = unitRepository.findById(unitId).orElse(null);
                assertNotNull(unit);

                Depart depart = new Depart();
                depart.setId(index);
                depart.setName("d" + index);
                depart.setUnit(unit);
                departRepository.save(depart);
            });
        }

        executors.shutdown();
        while (!executors.isTerminated()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    @Order(3)
    @Test
    void importUsers() {
        final Random random = new Random();
        ExecutorService executors = Executors.newFixedThreadPool(80);
        for (int i = 0; i < EMPLOYEE_COUNT; i++) {
            long index = i + 1;
            long departId = random.nextLong(DEPART_COUNT) + 1;
            Depart depart = departRepository.findById(departId).orElse(null);
            assertNotNull(depart);

            Employee employee = new Employee();
            employee.setId(index);
            employee.setPhone(generatePhone());
            employee.setName(generateName());
            employee.setBirthday(generateDate());
            employee.setDepart(depart);
            employee.setAddress("a" + index);
            employee.setGender(generateGender());

            executors.execute(() -> employeeRepository.save(employee));
        }

        executors.shutdown();
        while (!executors.isTerminated()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    @Order(4)
    @Test
    void importFriend() {
        final Random random = new Random();
        ExecutorService executors = Executors.newFixedThreadPool(80);
        for (int i = 0; i < EMPLOYEE_COUNT; i++) {
            executors.execute(() -> {
                long sourceId = random.nextLong(EMPLOYEE_COUNT) + 1;
                long targetId = random.nextLong(EMPLOYEE_COUNT) + 1;

                Employee sourceEmployee = employeeRepository.findById(sourceId).orElse(null);
                assertNotNull(sourceEmployee);

                Employee targetEmployee = employeeRepository.findById(targetId).orElse(null);
                assertNotNull(targetEmployee);

                sourceEmployee.setFriend(targetEmployee);
                employeeRepository.save(sourceEmployee);
            });
        }

        executors.shutdown();
        while (!executors.isTerminated()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    @Order(100)
    @Test
    void afterTest() {
        assertEquals(unitRepository.findAll().size(), UNIT_COUNT);
        assertEquals(departRepository.findAll().size(), DEPART_COUNT);
        assertEquals(employeeRepository.findAll().size(), EMPLOYEE_COUNT);
    }



    private String generatePhone() {
        StringBuilder sb = new StringBuilder();
        sb.append("1");
        for (int i = 0; i < 10; i++) {
            sb.append(new Random().nextInt(10));
        }
        return sb.toString();
    }

    private Gender generateGender() {
        return new Random().nextBoolean() ? Gender.MAN : Gender.WOMAN;
    }

    private String generateName() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append((char) (new Random().nextInt(26) + 65));
        }
        return sb.toString();
    }

    private Date generateDate() {
        long time = System.currentTimeMillis() - new Random().nextInt(1000 * 60 * 60 * 24 * 365 * 30);
        return new Date(time);
    }
}
