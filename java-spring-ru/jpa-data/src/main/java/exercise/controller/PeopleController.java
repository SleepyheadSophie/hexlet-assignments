package exercise.controller;

import exercise.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import exercise.model.Person;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/{id}")
    public Person show(@PathVariable long id) {
        return personRepository.findById(id).get();
    }

    // BEGIN
    @GetMapping(path = "")
    public List<Person> persons() {
        return personRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public Person persons(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @DeleteMapping(path = "/{id}") // удаление поста
    public void destroy(@PathVariable long id) {
        personRepository.deleteById(id);
    }
    // END
}
