# Compilar y ejecutar
La clave es ejecutar los test no como el main
```bash
cd src
javac -cp "../Test/lib-alone/junit-platform-console-standalone-1.13.4.jar:../Test/lib/*" -d ../bin $(find . -name "*.java")

cd ..
```

```bash
java -jar ../Test/lib-alone/junit-platform-console-standalone-1.13.4.jar -cp ../bin --scan-classpath
```

# Objetivos generales

1) Develop an implementation of interfaces for a simple vending machine (`interfaces` package)

Vending machine
- Chips A1
- Cacaolat A2
- Chocoboom A3
- McVites B1 
- ...

2) Create Unit test 
- Implement all the clases in `impl`package 
- Write suitable test in `test` package

3) Only write code when it says "TODO"
- Methods should be short
- Implement one thing only
- Have many test for every possible behaviour
- You can provide any number of **private** methods for the implementation but is important NOT to change the interface

4) Considerations when writing the implementation:
- What attributes and **private** helper methods you should implement
- What constructors you should provide
- What data structures you want to use
- Test: Normal cases + Edge cases (empty collections, limits, ...) + Exception cases (nulls, duplicate lanes code, products unavailable, )
- DO NOT ADD **exceptions** from the java.lang.exception as it would be changing the interfaces

5) Derivables:
- Assignment directory (all the source code and tests)
- Report describing:
    + Implementation
    + Justifying design and implementation decisions
    + How you chose thos test cases and how the have influenced the implementation
- Template at Studres (0-General/)

# Implementación
Factory Pattern
una clase llamada **Factory** que sirve para crear instancias de los objetos principales (como VendingMachineProduct, ProductRecord, y VendingMachine) sin que el resto del código tenga que saber cómo se construyen internamente.