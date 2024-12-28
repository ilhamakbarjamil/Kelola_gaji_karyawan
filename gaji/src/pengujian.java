import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class MainTest {

    Main mainApp;
    ArrayList<Employee> employeeList;

    @BeforeEach
    void setUp() {
        mainApp = new Main();
        employeeList = new ArrayList<>();
        employeeList.add(new Employee("Alice", 25, 5000.0, "Manager"));
        employeeList.add(new Employee("Bob", 30, 4000.0, "Developer"));
        mainApp.employeeList = employeeList; 
    }

    @Test
    void testAddEmployee() {
        Employee newEmployee = new Employee("Charlie", 28, 4500.0, "Designer");
        mainApp.employeeList.add(newEmployee); 

        assertEquals(3, mainApp.employeeList.size());
        assertEquals("Charlie", mainApp.employeeList.get(2).nama);
        assertEquals(4500.0, mainApp.employeeList.get(2).gaji);
    }

    @Test
    void testSortEmployeeBySalary() {
        mainApp.employeeList.sort((e1, e2) -> Double.compare(e1.gaji, e2.gaji));

        assertEquals(4000.0, mainApp.employeeList.get(0).gaji);
        assertEquals(5000.0, mainApp.employeeList.get(1).gaji);
    }

    @Test
    void testDeleteEmployee() {
        mainApp.employeeList.remove(0); 

        assertEquals(1, mainApp.employeeList.size());
        assertEquals("Bob", mainApp.employeeList.get(0).nama);
    }

    @Test
    void testUpdateSalary() {
        Employee employee = mainApp.employeeList.get(0); 
        employee.gaji = 6000.0;

        assertEquals(6000.0, employee.gaji);
    }
}
