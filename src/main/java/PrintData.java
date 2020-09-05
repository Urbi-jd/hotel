import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Scanner;

public class PrintData {

    void printFreeRooms(String arrivalDate, String departureDate) {
        System.out.println("Wolne pokoje w terminie od: " + arrivalDate + " do: " + departureDate);
        try (final Connection connection = getConnection()) {
            printFreeRoomsData(connection, arrivalDate, departureDate);
            System.out.println("--------------------------");
        } catch (SQLException exp) {
            System.out.println("Błąd połączenia: " + exp.getMessage());
        }
    }

    private void printFreeRoomsData(Connection connection, String arrivalDate, String departureDate) throws SQLException {
        String query = "select * from rooms r left join reservation re on re.id_room = r.id where \"" + departureDate + "\" < arrival_date or \"" + arrivalDate + "\" > departure_date  or arrival_date is null";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        printResoult(resultSet, "rooms");
    }

//    private void printResoultfreeRooms(ResultSet resultSet) throws SQLException {
//        while (resultSet.next()) {
//            int id = resultSet.getInt("r.id");
//            int roomNr = resultSet.getInt("room_no");
//            boolean seeView = resultSet.getBoolean("view_on_sea");
//            int area = resultSet.getInt("area");
//            int pricePerDay = resultSet.getInt("price");
//
//            String resoult = "id: " + id + " numer pokoju: " + roomNr + ", widok na morze: " + seeView
//                    + ", powierzchnia pokoju: " + area + ", cena za dzień: " + pricePerDay;
//            System.out.println(resoult);
//        }
//    }



    void insertReservatoin (String arrivalDate, String departureDate, int roomNr, int customerId){
        try (final Connection connection = getConnection()) {
            insertOneReservation(connection, arrivalDate, departureDate, roomNr, customerId);
        } catch (SQLException exp) {
            System.out.println("Błąd połączenia " + exp.getMessage());
        }
    }

    private void insertOneReservation(Connection connection, String arrialDate, String departureDate, int roomNr, int customerId) throws SQLException {
        String query = "insert into reservation values (null, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, arrialDate);
        preparedStatement.setString(2, departureDate);
        preparedStatement.setInt(3, roomNr);
        preparedStatement.setInt(4, customerId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    void insertCustomer (String firstName, String lastName, String email){
        try (final Connection connection = getConnection()) {
            insertOneCustomer(connection, firstName, lastName, email);
        } catch (SQLException exp) {
            System.out.println("Błąd połączenia " + exp.getMessage());
        }
    }

    private void insertOneCustomer(Connection connection, String firstName, String lastName, String email) throws SQLException {
        String query = "insert into customer values (null, ?, ?, ?, null)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, email);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


    void deleteReservation(){
        try (final Connection connection = getConnection()) {
            deleteReservationData(connection);
        } catch (SQLException exp) {
            System.out.println("Błąd połączenia " + exp.getMessage());
        }
    }

    private void deleteReservationData(Connection connection) throws SQLException {
                System.out.println("Enter reservation ID to be deleted");
        Scanner input = new Scanner(System.in);
        int deletedId = input.nextInt();
        String query = "delete from reservation where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, deletedId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    void printReservations() {
        System.out.println("Hotelowe rezerwacje");

        try (final Connection connection = getConnection()) {
            printReservationsData(connection);
            System.out.println("--------------------------");
            // wykorzystaj obiek Connection tutaj
        } catch (SQLException exp) {
            System.out.println("Błąd połączenia: " + exp.getMessage());
            // obsłużenie wyjątku
        }
    }



    private void printReservationsData(Connection connection) throws SQLException {
        String query = "select * from reservation r join rooms ro on r.id_room = ro.id join customer c on c.id = r.id_customer";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        printResoultReservations(resultSet);
    }

    private void printResoultReservations(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int roomNr = resultSet.getInt("room_no");
            String dateOfArrival = resultSet.getString("arrival_date");
            String dateOfDeparture = resultSet.getString("departure_date");
            LocalDate day1 = LocalDate.parse(dateOfArrival);
            LocalDate day2 = LocalDate.parse(dateOfDeparture);
            Duration days = Duration.between(day1.atStartOfDay(), day2.atStartOfDay());
            int days2 = (int) days.toDays();
            int pricePerDay = resultSet.getInt("price");
            int totalPrice = days2 * pricePerDay;
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String resoult = "Client: " + firstName + " " + lastName + ", id: " + id + " room number: " + roomNr + ", arrival date: " + dateOfArrival
                    + " days of residence: " + days2 + ", Total price: " + totalPrice;
            System.out.println(resoult);
        }
    }


    void printworkersWithDepName() {
        try (final Connection connection = getConnection()) {
            printWorkersWithDepNameData(connection);
            // wykorzystaj obiek Connection tutaj
        } catch (SQLException exp) {
            System.out.println("Błąd połączenia: " + exp.getMessage());
            // obsłużenie wyjątku
        }
    }

    private void printWorkersWithDepNameData(Connection connection) throws SQLException {
        String query = "select * from workers w join department d on d.id = w.id_dep";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        printResoultWorkWithDep(resultSet);
    }

    private void printResoultWorkWithDep(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            double salary = resultSet.getDouble("salary");
            String departmentName = resultSet.getString("name");
            String resoult = "imię: " + firstName + ", nazwisko: " + lastName + " wynagrodzenie: " + salary + ", nazwa działu: " + departmentName;
            System.out.println(resoult);

        }
        resultSet.close();
    }

    void printAll(String table) {
        try (final Connection connection = getConnection()) {
            printAllData(connection, table);
            // wykorzystaj obiek Connection tutaj
        } catch (SQLException exp) {
            System.out.println("Błąd połączenia: " + exp.getMessage());
            // obsłużenie wyjątku
        }
    }

    private void printAllData(Connection connection, String table) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select * from " + table;
        statement.execute(query);
        ResultSet resultSet = statement.getResultSet();
        printResoult(resultSet, table);
    }

    private void printResoult(ResultSet resultSet, String table) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            if (table.equals("workers")) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                double salary = resultSet.getDouble("salary");
                String resoult = "imię: " + firstName + ", nazwisko: " + lastName + " wynagrodzenie: " + salary;
                System.out.println(resoult);
            } else if (table.equals("rooms")) {
                int roomNr = resultSet.getInt("room_no");
                int price = resultSet.getInt("price");
                boolean seaView = resultSet.getBoolean("view_on_sea");
                int area = resultSet.getInt("area");
                String seaViewText = seaView(seaView);
                String resoult = "Id: " + id + ", Room nr: " + roomNr + " price per day: " + price + ", view at see: "
                        + seaViewText + " Area in sqr m: " + area;
                System.out.println(resoult);
            } else if (table.equals("customer")){
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String resoult = "customer ID: " + id + ", name: " + first_name + " " + last_name + ", email: " + email;
                System.out.println(resoult);
            }
        }
        resultSet.close();
    }

    private String seaView(boolean seeView){
        String seeViewText;
        if(seeView){
            seeViewText = "yes";
        } else
            seeViewText = "no";
        return seeViewText;
    }


    private Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/hotel?autoReconnect=true&useSSL=false&serverTimezone=UTC";
        return DriverManager.getConnection(jdbcUrl, "scott", "scott");
    }
}



