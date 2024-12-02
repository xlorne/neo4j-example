package com.codingapi.neo4j.example.repository;

import com.codingapi.neo4j.example.em.Gender;
import com.codingapi.neo4j.example.entity.Depart;
import com.codingapi.neo4j.example.entity.Employee;
import com.codingapi.neo4j.example.entity.Unit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Rollback(value = false)
class Neo4jRepositoryTest {

    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private DepartRepository departRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Order(0)
    @Test
    void beforeTest() {
        unitRepository.deleteAll();
        departRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Order(1)
    @ParameterizedTest
    @CsvFileSource(resources = "/units.csv", numLinesToSkip = 1)
    void importUnit(long id, String name, String address) {
        log.info("id:{},name:{},address:{}", id, name, address);
        Unit unit = new Unit();
        unit.setId(id);
        unit.setName(name);
        unit.setAddress(address);
        unitRepository.save(unit);
    }

    @Order(2)
    @ParameterizedTest
    @CsvFileSource(resources = "/departs.csv", numLinesToSkip = 1)
    void importDepart(long id, long unitId, String departName) {
        log.info("id:{},unitId:{},departName:{}", id, unitId, departName);

        Unit unit = unitRepository.findById(unitId).orElse(null);
        assertNotNull(unit);

        Depart depart = new Depart();
        depart.setName(departName);
        depart.setId(id);
        depart.setUnit(unit);
        departRepository.save(depart);
    }

    @Order(3)
    @ParameterizedTest
    @CsvFileSource(resources = "/users.csv", numLinesToSkip = 1)
    void importUsers(long id, long departId, String name, String birthDate, String gender, String phone, String address) throws ParseException {
        log.info("id:{},departId:{},name:{},birthDate:{},gender:{},phone:{},address:{}", id, departId, name, birthDate, gender, phone, address);

        Depart depart = departRepository.findById(departId).orElse(null);
        assertNotNull(depart);

        Employee employee = new Employee();
        employee.setId(id);
        employee.setPhone(phone);
        employee.setName(name);
        employee.setBirthday(format.parse(birthDate));
        employee.setDepart(depart);
        employee.setAddress(address);
        employee.setGender(Gender.parser(gender));

        employeeRepository.save(employee);
    }


    @Order(4)
    @ParameterizedTest
    @CsvFileSource(resources = "/friends.csv", numLinesToSkip = 1)
    void importFriend(long sourceId, long targetId)  {
        log.info("sourceId:{},targetId:{}", sourceId, targetId);

        Employee sourceEmployee = employeeRepository.findById(sourceId).orElse(null);
        assertNotNull(sourceEmployee);

        Employee targetEmployee = employeeRepository.findById(targetId).orElse(null);
        assertNotNull(targetEmployee);

        sourceEmployee.setFriend(targetEmployee);
        employeeRepository.save(sourceEmployee);
    }


    @Order(100)
    @Test
    void afterTest() {
        assertEquals(unitRepository.findAll().size(), 4);
        assertEquals(departRepository.findAll().size(), 8);
        assertEquals(employeeRepository.findAll().size(), 16);
    }
}
