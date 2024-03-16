package ru.gb;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Department> departments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            departments.add(new Department("Department #" + i + "\n"));
        }
        System.out.println("Список департаментов: " + "\n" + departments);

        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            persons.add(new Person(
                    "Person #" + i,
                    ThreadLocalRandom.current().nextInt(20, 61),
                    ThreadLocalRandom.current().nextInt(20_000, 100_000) * 1.0,
                    departments.get(ThreadLocalRandom.current().nextInt(departments.size()))
            ));
        }

        System.out.println("Список работников: " + "\n" + persons);

        System.out.println("------------------------");
       Main.printNamesOrdered(persons);

        System.out.println("------------------------");

        Map<Department, Person> departmentMap = printDepartmentOldestPerson(persons);
        departmentMap.forEach((d,p) -> System.out.println(d.getName() + p.getName()));

        System.out.println("------------------------");
        Department topDepartment = findTopDepartment(persons).orElse(null);
        System.out.println("Депаратмент, чья суммарная зарплата всех сотрудников максимальна " + topDepartment);

        System.out.println("------------------------");

        List<Person> findMap =  findFirstPersons(persons);
        findMap.forEach((p) -> System.out.println("Сотрудник младше 30 лет  "+ p.getName()));
    }
    /**
     * Вывести на консоль отсортированные (по алфавиту) имена персонов
     */
        public static void printNamesOrdered(List<Person> persons) {

            persons.stream().sorted(Comparator.comparing(Person::getName)).forEach(System.out::println);
        }

        /**
         * В каждом департаменте найти самого взрослого сотрудника.
         * Вывести на консоль мапипнг department -> personName
         * Map<Department, Person>
         */
        public static Map<Department, Person> printDepartmentOldestPerson(List<Person> persons) {
            return persons.stream()
                    .collect(Collectors.groupingBy(Person::getDepartment,
                            Collectors.collectingAndThen(
                                    Collectors.maxBy(Comparator.comparingInt(Person::getAge)),
                                    Optional::get)));
        }

        /**
         * Найти 10 первых сотрудников, младше 30 лет, у которых зарплата выше 50_000
         */
        public static List<Person> findFirstPersons(List<Person> persons) {
            return persons.stream().filter(person -> person.getAge() < 30).filter(person -> person.getSalary() > 50_000).
                    limit(10).collect(Collectors.toList());
        }

        /**
         * Найти депаратмент, чья суммарная зарплата всех сотрудников максимальна
         */
        public static Optional<Department> findTopDepartment(List<Person> persons) {
            return persons.stream().collect(Collectors.groupingBy(Person::getDepartment, Collectors.summingDouble(Person::getSalary))).
                    entrySet().stream().max(Comparator.comparingDouble(Map.Entry::getValue)).map(Map.Entry::getKey);
        }
}