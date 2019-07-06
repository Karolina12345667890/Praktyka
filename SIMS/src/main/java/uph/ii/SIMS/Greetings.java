package uph.ii.SIMS;


public class Greetings {
    private String greeting;
    private String greeting2;
    
    public Greetings(String greeting) {
        this.greeting = greeting;
        this.greeting2 = greeting + "2";
    }
    
    public String getGreeting() {
        return greeting;
    }
    
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
    
    public String getGreeting2() {
        return greeting2;
    }
    
    public void setGreeting2(String greeting2) {
        this.greeting2 = greeting2;
    }
    
    @Override
    public String toString() {
        return "Greetings{" +
            "greeting='" + greeting + '\'' +
            ", greeting2='" + greeting2 + '\'' +
            '}';
    }
}
