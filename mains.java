
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car { // class name we put details in private way
  private String carId;

  private String brand;

  private String model;

  private double basePrice;

  private boolean isAvailable; // if availble TRUE or not FALSE

  // constructor-The constructor is called when an object of a class is created.
  // It can be used to set initial values for object attributes

  // construct form by the SAME CLASS NAME[car] parameter constructor

  public Car(String carId, String brand, String model, double basePrice) {
    // fill the data inside constructor
    this.carId = carId;
    this.brand = brand;
    this.model = model;
    this.basePrice = basePrice;
    this.isAvailable = true; // if available
  }

  public String getCarId() { // encapsulatio GETTER method
    return carId; // basically variables are in PRIVATE so,by using GET the customer can see the
                  // data{GET THE DATA}
  } // when we call GETcarId then it calls carId

  public String getBrand() {
    return brand;
  }

  public String getModel() {
    return model;
  }

  public double calculatePrice(int rentalDays) { // calculating the price from how many days he took for rent
    return basePrice * rentalDays; // 3000[price] * 3[days rent of car]
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void rent() { // if car is went for RENT then their is no car so its FALSE
    isAvailable = false;
  }

  public void returnCar() { // if car is came form rent then it is RETURNED their is car so its TRUE
    isAvailable = true;
  }

}

class Customer {
  public String customerId;

  public String name;

  public Customer(String customerId, String name) {
    this.customerId = customerId;
    this.name = name;
  }

  public String getCustomerId() {
    return customerId;
  }

  public String getName() {
    return name;
  }
}

class Rental { // it is a combination of CUSTOMER & CAR because they rental the car [by which
               // car & the customer name]
  private Car car; // car is object and created a car type

  private Customer customer; // intead of String we use Customer and it is a class name of Customer and
                             // giving object as customer

  private int days; // num of days to rent the car for customer

  public Rental(Car car, Customer customer, int days) {
    this.car = car;
    this.customer = customer;
    this.days = days;
  }

  public Car getCar() {
    return car;
  }

  public Customer getCustomer() {
    return customer;
  }

  public int getDays() {
    return days;
  }
}

class CarRentalSystem{

  private List<Car> cars; //list of cars to store

  private List<Customer> customers;//list of customers to store customer
  
  private List<Rental> rentals;//list of rentals to store rental{customer which car to take rent}
  

  public CarRentalSystem(){
    cars=new ArrayList<>(); //now insert cars in memory because in line 108 we have just declared it so,now insert it in new ArrayList

    customers=new ArrayList<>();
    rentals=new ArrayList<>(); //now this 3 list are ready to take the data
  }
    //IN arrayList we have a methos is[.Add]

    public void addCar(Car car){ //in addCar any car will come, Car type and car (object)
      cars.add(car);
    }

    public void addCustomer(Customer customer){ //in addCustomer any customer will come, Customer type and customer (object)
      customers.add(customer);
    }

 // FOR RENT A CAR

    public void rentCar(Car car,Customer customer,int days){ // combination of both CAR & CUSTOMER data(details of cars and customer)before giving rent check weathet car is Avaiable or not
      if(car.isAvailable()){    
        car.rent();   //if car is Available the rent it and add new (customer name , car details and how manydays of car rent)
        rentals.add(new Rental(car, customer, days));
      } else{
        System.out.println("Car is not available"); // if not avaiable
      }
    }

 // FOR RETURN THE RENTED CAR
    
    public void returnCar(Car car){ //which car is he returning
      car.returnCar(); // calls returnCar method so now this car will be Available
      Rental rentalToRemove=null;
      for(Rental rental :rentals){
        if(rental.getCar()==car){
          rentalToRemove = rental;
        }
      }
      if(rentalToRemove !=null){
        rentals.remove(rentalToRemove);
        System.out.println("Car returned successfully");
      }else{
        System.out.println("Car wa not rented");
      }
    }

     //FOR INTERFACE FORM CUSTOMOR 
   
     public void menu(){
      Scanner scanner=new Scanner(System.in); //took scanner obj for input
        while(true){
          System.out.println("==== Car Rental System ====");
          System.out.println("1.Rent a car");
          System.out.println("2. Return a car");
          System.out.println("3.Exit");
          System.out.println("Enter your choice: ");

          int choice = scanner.nextInt();
          scanner.nextLine(); // for newLine

          //from the customer choice
            if(choice == 1){
              System.out.println("\n== Rent a car ==\n");
              System.out.println("Enter your name");
              String customerName = scanner.nextLine(); //Customer to type his name in next line

              System.out.println("\n Available Cars:"); // list of avaiable cars 
               for(Car car:cars){
                if(car.isAvailable()){
                  System.out.println(car.getCarId()+ " - "+ car.getBrand()+"-"+ car.getModel());
                }
               }

               System.out.println("\nEnter the cars ID you want to rent :");
               String carId=scanner.nextLine();

               System.out.println("Enter the number of days for Rental: ");
               int rentalDays=scanner.nextInt();
               scanner.nextLine();

               Customer newCustomer = new Customer("CUS" + (customers.size() +1) , customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for(Car car: cars){
                   if(car.getCarId().equals(carId) && car.isAvailable()){
                    selectedCar=car;
                    break;
                   }
                }
                if(selectedCar!=null){
                  double totalPrice = selectedCar.calculatePrice(rentalDays);
                  System.out.println("\n== Rental Information ==\n");
                  System.out.println("Customer ID :" + newCustomer.getName());
                  System.out.println("Car: "+ selectedCar.getBrand()+ " "+ selectedCar.getModel());
                  System.out.println("Rental Days: "+rentalDays);
                  System.out.printf("Total price : $%.2f%n", totalPrice);

                  System.out.println("\nConfirm rental (Y/N): ");
                  String confirm = scanner.nextLine();

                  if(confirm.equalsIgnoreCase("Y")){
                    rentCar(selectedCar, newCustomer, rentalDays);
                    System.out.println("\nCar rented successfully");
                  }else{
                    System.out.println("\nRental canceled");
                  }
                }else{
                  System.out.println("\nInvaild car selection or car not available for rent");
                }
            }else if(choice == 2){
              System.out.println("\n== Return a Car ==\n");
              System.out.print("Enter the car ID you want to return: ");
              String carId = scanner.nextLine();
               
              Car carToReturn=null;
              for(Car car : cars){
                if(car.getCarId().equals(carId) && !car.isAvailable()){
                  carToReturn=car;
                  break;
                }
              }

              if(carToReturn !=null){
                Customer customer=null;
                for(Rental rental: rentals){
                  if(rental.getCar() == carToReturn){
                    customer = rental.getCustomer();
                    break;
                  }
                }
                if(customer!=null){
                  returnCar(carToReturn);
                  System.out.println("Car returned successfully by "+ customer.getName());
                }else{
                  System.out.println("Car was not rented or rental information is missing.");
                }
              }else{
                System.out.println("Invalid car ID or car is not rented.");
              }
            }else if(choice == 3){
              break;
            }else{
              System.out.println("Invaild choice. Please enter a valid option.");
            }
        }
        System.out.println("\nThank you for using the Car Rental System!");
     }
}

public class mains {  // MAIN METHOD
  public static void main (String[] args) {
      CarRentalSystem rentalSystem = new CarRentalSystem();
    
      //WE HAVE ONLY 3 cars and the details of it
      Car car1 = new Car( "Z000" , "Toyota" , "Camry" ,  60.0);
      Car car2 = new Car ( "Z002" , "Honda" , "Accord" ,  80.0);
      Car car3 = new Car ( "Z003" , "Mahindra" , "Thar" , 100.0);

      rentalSystem.addCar(car1); // added available cars 
      rentalSystem.addCar(car2); // added available cars
      rentalSystem.addCar(car3); // added available cars

      rentalSystem.menu(); //runs the menu
  }

}