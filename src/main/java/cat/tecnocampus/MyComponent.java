package cat.tecnocampus;

@Component
public class MyComponent {
    private final MyService myService;

    public MyComponent(MyService myService) {
        this.myService = myService;
    }

    public String doSomething() {
        return "Component is running doSomething()";
    }

    public String doSomethingWithService() {
        return "Component is running doSomethingWithService(): " + myService.serve();
    }
}
