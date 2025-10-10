package cat.tecnocampus;

@Component
public class MyComponent {
    @Autowired
    private MyService myService;

    public String doSomething() {
        return "Component is running doSomething()";
    }

    public String doSomethingWithService() {
        if (myService != null) {
            return "Component is running doSomethingWithService() :" + myService.serve();
        } else {
            return "Component has no service to do something with";
        }
    }
}
