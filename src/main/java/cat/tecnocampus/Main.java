package cat.tecnocampus;

import java.lang.annotation.Annotation;

public class Main {
    public static void main(String[] args) {
        isMyComponentDirectlyAnnotatedWithComponent();

        isMyServiceDirectlyAnnotatedWithComponent();

        isMyServiceMetaAnnotatedWithComponent();

        printMyServiceClassAnnotations();

        // create an instance of MyService and call its serve() method
        MyService myService = createMyService();
        System.out.println(myService.serve());
        System.out.println();

        // create an instance of MyComponent and inject MyService in its constructor, then call its methods
        MyComponent myComponent = createComponentAndInjectServiceInConstructor(myService);
        System.out.println(myComponent.doSomething());
        System.out.println(myComponent.doSomethingWithService());
    }

    private static void isMyServiceMetaAnnotatedWithComponent() {
        boolean isComponent;
        // is MyService meta-annotated with @Component?
        isComponent = checkIfClassIsMetaAnnotatedWithComponent(MyService.class);
        if (isComponent) {
            System.out.println("MyService is annotated with @Component because @Service is meta-annotated with @Component");
        }
        else {
            System.out.println("MyService is NOT annotated with @Component. Neither directly nor though meta annotated with @Component");
        }
        System.out.println();
    }

    private static void isMyServiceDirectlyAnnotatedWithComponent() {
        boolean isComponent;
        isComponent = checkIfClassIsAnnotatedDirectlyWithComponent(MyService.class);
        if (isComponent) {
            System.out.println("MyService is directly annotated with @Component");
        } else {
            System.out.println("MyService is NOT directly annotated with @Component");
        }
        System.out.println();
    }

    private static void isMyComponentDirectlyAnnotatedWithComponent() {
        boolean isComponent;
        isComponent = checkIfClassIsAnnotatedDirectlyWithComponent(MyComponent.class);
        if (isComponent) {
            System.out.println("MyComponent class is directly annotated with @Component");
        } else {
            System.out.println("MyComponent class is NOT directly annotated with @Component");
        }
        System.out.println();
    }

    private static boolean checkIfClassIsAnnotatedDirectlyWithComponent(Class clazz) {
        return clazz.isAnnotationPresent(Component.class);
    }

    private static void printMyServiceClassAnnotations() {
        Annotation annotation = MyService.class.getAnnotations()[0];
        System.out.println("MyService is annotated with: " + annotation.annotationType().getSimpleName());
        Annotation[] annotations = annotation.annotationType().getAnnotations();
        for (Annotation a : annotations) {
            System.out.println("@Service has annotation: " + a.annotationType().getSimpleName());
        }
        System.out.println();
    }

    private static boolean checkIfClassIsMetaAnnotatedWithComponent(Class clazz) {
        Annotation annotation = clazz.getAnnotations()[0];
        Annotation[] annotations = annotation.annotationType().getAnnotations();
        for (Annotation a : annotations) {
            if (a.annotationType().equals(Component.class)) {
                return true;
            }
        }
        return false;
    }

    private static MyService createMyService() {
        try {
            return MyService.class.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static MyComponent createComponentAndInjectServiceInConstructor(MyService myService) {
        try {
            MyComponent myComponent = MyComponent.class.getDeclaredConstructor(MyService.class).newInstance(myService);
            return myComponent;

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
