# square-banking-api

## Overview

This application provides a simple bank API to perform withdrawal, deposit and inquiry on certain bank account.

## Prerequisites

### IDE Prerequisites

* Java 11
* Maven 3.8.4

## Building the Application

### From IDE (Eclipse, Intellij)

Open as a Maven project and compile.

### From Command Line

    mvn clean install

## Testing

See: [Testing.md](/TESTING.md)

## Running the Application

### Using Maven

Run or debug the app with the ```Starter``` main class at the root of your Java package hierarchy

    'bash run.sh' -OR- 'mvn spring-boot:run' -OR- 'mvn exec:exec'

You must add the following VM options in order for logging to work properly:

    -Dapplication.home=. -Dproject.name=square-banking-api -Dspring.profiles.active=test -Dapplication.environment=test

## Technologies
Run ```dependency:tree``` to get a detailed list of used dependencies.
 - Java 11
 - Maven
 - spring-boot
 - PMD plugin
 - Apache commons
 - ICU4j
 - LOG4j
 - Jococo plugin
 - spring-boot test

## Architecture & How to Navigate
The solution is divided into 6 main parts each is covered and well tested under our [Test Source](TESTING.md)

- model: data-centric classes which encapsulate closely related items in our case ```Account, Customer, Transaction, Address and Bank```
- service: a layer/module that hosts our services API & Impl components ```Depositor, Inquirer and Withdrawal``` services
- logging: a layer/module that holds our ```SystemEvents and WAL implementor```
- exceptions: a package that holds our custom Exceptions
- util: a package that provides helper classes

-------------------
private final static TreeSet<Employee> HIERARCHY = new TreeSet<>();

    @Override
    public TreeSet<Employee> buildHierarchyTree(final Payload payload, final AtomicInteger level) {
        //TODO: if hierarchy for the same payload exists within our cache then return it here when you scale the service to support cache layer
        // TODO: add a task queue support to write behind if payload has minor changes but some entry matches previous ones
        // TODO: add asynchronous support and reactive components to better facilitate non-blocking IO and scale the service
        if(payload.isEmpty()){
            return HIERARCHY;
        }
        for(final Map.Entry<String, String> entry : payload.entrySet()){
            if(!payload.containsKey(entry.getValue())){
                final String employeeName = entry.getKey();
                final EmployeeBuilder currentEmployee = new EmployeeBuilder(employeeName);
                final String manager = payload.remove(entry.getKey());
                if(HIERARCHY.isEmpty()){
                    HIERARCHY.add(new EmployeeBuilder(manager).withLevel(level.getAndIncrement()).build());
                } else if(!HIERARCHY.isEmpty() && !isDirectOfAnAlreadyExistingManager(manager, HIERARCHY)){
                    //this means that we didn't add the report under a manager, and we're adding it for the first time
                    // other reports under the same manager should have the same level
                    level.incrementAndGet();
                }
                HIERARCHY.add(currentEmployee.withLevel(level.get()).withSupervisor(manager).build());
            }
        }
        return buildHierarchyTree(payload, level);
    }

    private boolean isDirectOfAnAlreadyExistingManager(final String manager, final TreeSet<Employee> hierarchy) {
        //skip root
        return hierarchy.stream().skip(1).anyMatch(employee -> employee.getSupervisor().equals(manager));
    }
