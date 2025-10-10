package cat.tecnocampus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) {
        checkIfComponentIsAnnotated();

        checkIfServiceIsAnnotated();

        printServiceAnnotation();

        checkServiceMetaAnnotation();

        createComponentObject();

        createServiceAndInjectIntoComponent();
    }

    private static void checkIfComponentIsAnnotated() {
        if (MyComponent.class.isAnnotationPresent(Component.class)) {
            System.out.println("MyComponent is directly annotated with @Component");
        } else {
            System.out.println("MyComponent is NOT directly annotated with @Component");
        }
        System.out.println();
    }

    private static void checkIfServiceIsAnnotated() {
        if (MyService.class.isAnnotationPresent(Component.class)) {
            System.out.println("MyService is directly annotated with @Component");
        } else {
            System.out.println("MyService is NOT directly annotated with @Component");
        }
        System.out.println();
    }

    private static void printServiceAnnotation() {
        Annotation annotation = MyService.class.getAnnotations()[0];
        System.out.println("MyService is annotated with: " + annotation.annotationType().getSimpleName());
        System.out.println();
    }

    private static void checkServiceMetaAnnotation() {
        Annotation annotation = MyService.class.getAnnotations()[0];
        Annotation[] annotations = annotation.annotationType().getAnnotations();
        for (Annotation a : annotations) {
            System.out.println("@Service has annotation: " + a.annotationType().getSimpleName());
            if (a.annotationType().equals(Component.class)) {
                System.out.println("MyService is annotated with @Component because @Service is meta-annotated with @Component");
            }
        }
        System.out.println();
    }

    private static void createComponentObject() {
        if (MyComponent.class.isAnnotationPresent(Component.class)) {
            try {
                Object componentObject = MyComponent.class.getDeclaredConstructor().newInstance();
                MyComponent myComponent = (MyComponent) componentObject;
                System.out.println(myComponent.doSomething());
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println();
    }

    private static void createServiceAndInjectIntoComponent() {
        try {
            Object componentObject = MyComponent.class.getDeclaredConstructor().newInstance();
            MyComponent myComponent = (MyComponent) componentObject;
            Object myObjectService = MyService.class.getDeclaredConstructor().newInstance();
            MyService myService = (MyService) myObjectService;

            Field[] fields = myComponent.getClass().getDeclaredFields();
            if (fields[0].isAnnotationPresent(Autowired.class)) {
                System.out.println("Field " + fields[0].getName() + " is annotated with @Autowired");
                fields[0].setAccessible(true);
                fields[0].set(myComponent, myService);
                fields[0].setAccessible(false);
            }

            System.out.println(myComponent.doSomethingWithService());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }
}
