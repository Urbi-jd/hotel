import java.time.LocalDate;
import java.util.Scanner;

public class Aplication {
    public static void main(String[] args) {
        PrintData printData = new PrintData();
        Scanner input = new Scanner(System.in);
        String arrivalDate = "2020-09-03";
        String departureDate = "2020-09-08";


//        printData.printAll("customer");
//        System.out.println();
//        printData.printFreeRooms(arrivalDate, departureDate);
//        printData.printworkersWithDepName();
//        System.out.println("------------------");
//        printData.printReservations();


//        printData.insertReservatoin(arrivalDate, departureDate, 2, 2);
//
//        printData.printReservations();
//        printData.deleteReservation();
//        printData.printReservations();

        int option;
        do {
            System.out.println("What would you like to do?");
            System.out.println("1: show all rooms");
            System.out.println("2: show available rooms in selected period");
            System.out.println("3. make reservation in selected period");
            System.out.println("4. cancel reservation");
            System.out.println("5. show reservations");
            System.out.println("0. exit program");
            option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1:
                    printData.printAll("rooms");
                    System.out.println();
                    break;

                case 2:
                    System.out.println("enter arrival date in format: YYYY-MM-DD");
                    arrivalDate = input.nextLine();
                    System.out.println("enter departure date in format: YYYY-MM-DD");
                    departureDate = input.nextLine();
                    printData.printFreeRooms(arrivalDate, departureDate);
                    break;

                case 3:
                    printData.printAll("customer");
                    System.out.println("is customer on the list ?");
                    System.out.println("1: YES, 2: NO");
                    int option2 = input.nextInt();
                    input.nextLine();


                    switch (option2) {
                        case 1:
                            System.out.println("enter customer ID");
                            int custId = input.nextInt();
                            System.out.println("enter room number");
                            int roomNr = input.nextInt();
                            input.nextLine();
                            printData.insertReservatoin(arrivalDate, departureDate, roomNr, custId);
                            break;
                        case 2:
                            System.out.println("Add new customer first:");
                            System.out.println("Enter first name:");
                            String firstName = input.nextLine();
                            System.out.println("Enter last name");
                            String lastName = input.nextLine();
                            System.out.println("Enter email");
                            String email = input.nextLine();
                            printData.insertCustomer(firstName, lastName, email);
                    }
                    break;

                case 4:
                    printData.printReservations();
                    printData.deleteReservation();
                    break;

                case 5:
                    printData.printReservations();
                    break;

                case 0:
                    System.out.println("Good bye");
                    break;

                default:
                    System.out.println("Try again");
                    System.out.println();
            }

        } while (option != 0);


    }
}
