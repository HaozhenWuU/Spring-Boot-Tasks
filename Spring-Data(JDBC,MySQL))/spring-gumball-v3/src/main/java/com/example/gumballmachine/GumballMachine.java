package com.example.gumballmachine ;


public class GumballMachine {
 
  State soldOutState;
  State noQuarterState;
  State hasQuarterState;
  State soldState;
  private String modelNumber;
  private String serialNumber;

  State state = noQuarterState ;

 
  public GumballMachine() {
    soldOutState = new SoldOutState(this);
    noQuarterState = new NoQuarterState(this);
    hasQuarterState = new HasQuarterState(this);
    soldState = new SoldState(this);
    state = noQuarterState ;
  }

  public void setModelNumber(String modelNumber){
    this.modelNumber = modelNumber;
  }

  public String getModelNumber(){
    return modelNumber;
  }

  public void setSerialNumber(String serialNumber){
    this.serialNumber = serialNumber;
  }

  public String getSerialNumber(){
    return serialNumber;
  }
 
  public void insertQuarter() {
    state.insertQuarter();
  }
 
  public void ejectQuarter() {
    state.ejectQuarter();
  }
 
  public void turnCrank() {
    state.turnCrank();
    state.dispense();
  }

  void setState(State state) {
    this.state = state;
  }
 
  void releaseBall() {
    System.out.println("A gumball comes rolling out the slot...");
  }
 
  void refill(int count) {
    state = noQuarterState;
  }

  public State getState() {
    return state;
  }


  public void setState(String state){

    switch(state){
      case "com.example.gumballmachine.HasQuarterState":
        this.state = hasQuarterState;
        break;
      case "com.example.gumballmachine.NoQuarterState":
        this.state = noQuarterState;
        break;
      case "com.example.gumballmachin.SoldOutState":
        this.state = soldOutState;
        break;
      case "com.example.gumballmachin.SoldState":
        this.state = soldState;
        break;
    }

  }

  public State getSoldOutState() {
    return soldOutState;
  }

  public State getNoQuarterState() {
    return noQuarterState;
  }

  public State getHasQuarterState() {
    return hasQuarterState;
  }

  public State getSoldState() {
    return soldState;
  }
 
  public String toString() {
    StringBuffer result = new StringBuffer();
    result.append("Mighty Gumball, Inc.");
    result.append("\nSpring Boot Standing Gumball");
    result.append("\n");
    result.append("\n");
    result.append("\nMachine is " + state);
    result.append("\nSerial Number #" + serialNumber + "Model Number #" + modelNumber + "\n");
    return result.toString();
  }
}