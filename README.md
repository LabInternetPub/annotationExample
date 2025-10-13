# Java Annotations and Reflection in This Project

## What Are Annotations?
Annotations in Java are a way to add metadata to your code. They look like `@Something` above classes, methods, or fields. This metadata can be used by tools or frameworks to add extra behavior or information without changing the actual code logic.

In Java, annotations are a special kind of interface. When you define an annotation (using `@interface`), Java creates an interface behind the scenes. 
However, annotations are not regular interfaces—they are handled specially by the compiler and JVM for metadata purposes. You cannot implement an annotation like a normal interface.

Annotations can be annotated with other annotations to create a hierarchy of annotations. See for example `@Component` and `@Service` in this project

## What Is Reflection?
Reflection is a Java feature that lets you inspect and interact with classes, methods, fields, and annotations at runtime. With reflection, you can create objects, call methods, or read annotations even if you don't know their details at compile time.

## How Are They Used in This Project?
- Custom annotations like `@Component`, `@Service`, and `@Autowired` are used to mark classes and fields.
- The code uses reflection to check if classes or fields have these annotations (`isAnnotationPresent`), to read annotation details, and to create objects dynamically (`getDeclaredConstructor().newInstance()`).
- It also uses reflection to inject dependencies: for example, it finds a field marked with `@Autowired` and sets its value to a new service object.

This approach is similar to how frameworks like Spring manage components and dependency injection, but here it's done manually using Java's built-in annotation and reflection features.

## Defining New Annotations: `@Retention` and `@Target`
When you define a new annotation in Java, you often use two meta-annotations:

- `@Retention` specifies how long the annotation should be kept. The most common values are:
  - `RetentionPolicy.SOURCE`: The annotation is only in the source code and discarded by the compiler.
  - `RetentionPolicy.CLASS`: The annotation is in the `.class` file but not available at runtime.
  - `RetentionPolicy.RUNTIME`: The annotation is available at runtime, so you can access it using reflection.

- `@Target` specifies where the annotation can be applied. Common values include:
  - `ElementType.TYPE`: Classes, interfaces, or enums.
  - `ElementType.FIELD`: Fields (member variables).
  - `ElementType.METHOD`: Methods.
  - `ElementType.PARAMETER`: Method parameters.

For example, `@Retention(RetentionPolicy.RUNTIME)` means the annotation is available at runtime, and `@Target(ElementType.FIELD)` means it can only be used on fields. This is important for frameworks or code that use reflection to process annotations during program execution.

## In this project: 
Custom annotations like `@Component`, `@Service`, and `@Autowired` are used to mark classes and fields. The code uses 
reflection to check if classes or fields have these annotations (`isAnnotationPresent`), to read annotation details, 
and to create objects dynamically (`getDeclaredConstructor().newInstance()`). It also uses reflection to inject dependencies: 
for example, it finds a field marked with `@Autowired` and sets its value to a new service object. 

This approach is similar to how frameworks like Spring manage components and dependency injection, but here it's done 
manually using Java's built-in annotation and reflection features.
