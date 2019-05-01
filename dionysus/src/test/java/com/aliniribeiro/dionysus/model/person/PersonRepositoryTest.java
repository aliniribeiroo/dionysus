package com.aliniribeiro.dionysus.model.person;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PersonRepository personRepository;

    @After
    public void cleanUp() {
        personRepository.deleteAll();
    }


    @Test
    public void getByOriginalId_ShouldFindPerson() {
        PersonEntity person = new PersonEntity();
        person.setCpf("12345");
        person.setAddress("xanana");
        person.setLastAdrdessUpdate(LocalDate.now());
        entityManager.persistAndFlush(person);

        PersonEntity person2 = new PersonEntity();
        person2.setCpf("1234455");
        person2.setAddress("xanana");
        person2.setLastAdrdessUpdate(LocalDate.now());
        entityManager.persistAndFlush(person2);

        PersonEntity person3 = new PersonEntity();
        person3.setCpf("12345125");
        person3.setAddress("xanana");
        person3.setLastAdrdessUpdate(LocalDate.now());
        entityManager.persistAndFlush(person3);

        PersonEntity output = personRepository.findBycpf(person.getCpf());
        Assertions.assertThat(output).isEqualTo(person);
    }

    @Test
    public void getByOriginalId_ShouldNotFindPerson() {
        PersonEntity person = new PersonEntity();
        person.setCpf("1232245");
        person.setAddress("xanana");
        person.setLastAdrdessUpdate(LocalDate.now());
        entityManager.persistAndFlush(person);

        PersonEntity person2 = new PersonEntity();
        person2.setCpf("12345");
        person2.setAddress("xanana");
        person2.setLastAdrdessUpdate(LocalDate.now());
        entityManager.persistAndFlush(person2);

        PersonEntity person3 = new PersonEntity();
        person3.setCpf("12345125");
        person3.setAddress("xanana");
        person3.setLastAdrdessUpdate(LocalDate.now());
        entityManager.persistAndFlush(person3);

        PersonEntity output = personRepository.findBycpf("1235");
        Assertions.assertThat(output).isNull();
    }

}
