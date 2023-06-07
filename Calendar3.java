//Programer: Bryce Nyswonger
//Class:/CS %141 Face-to-face
//Date:2/21/23
//Assignment: Lab 1
// Reference Materials:https://wcc.instructure.com/courses/2306777/assignments/28253519
//Purpose:Menu to select month and display an accurate calander for the year 2023
import java.util.Scanner;
import java.util.Calendar;
import java.io.*;
public class Calendar3 {// Execute Program

    private static Scanner scanner = new Scanner(System.in);
    
    static String[][] eventArray = new String[][] {
        new String[31], // January
        new String[28], // February
        new String[31], // March
        new String[30], // April
        new String[31], // May
        new String[30], // June
        new String[31], // July
        new String[31], // August
        new String[30], // September
        new String[31], // October
        new String[30], // November
        new String[31]  // December
};

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        
        ascii();
        System.out.println("!2023 Calendar!");
        readEvents();

        boolean display = false; // if no calender
        while (true) {
            System.out.println("Enter command:");
            System.out.println("e=enter date");

            System.out.println("n=next month");
            System.out.println("p=previous month");

            System.out.println("t=today's date");
            System.out.println("ev=add event (MM/DD event_title)");
            System.out.println("q=quit");
            String command = scanner.nextLine(); // Menu

            if (command.equals("n")) {
                if (!display) {
                    System.out.println("Enter a date first!");
                    continue;
                } // returns to menu
                else {
                    month++;
                    if (month > 12) {
                        month = 1;
                        year++;
                    } // wraps back to Jan
                    printMonth(year, month);
                }
            } else if (command.equals("p")) {
                if (!display) {
                    System.out.println("Enter a date first!");
                    continue;
                } // returns to menu
                else {
                    month--;
                    if (month < 1) {
                        month = 12;
                        year--;
                    } // Wraps to Dec
                    printMonth(year, month);
                }
            } else if (command.equals("e")) {
                System.out.println("Enter month (mm): ");
                month = scanner.nextInt();
                scanner.nextLine();
                printMonth(year, month);
                display = true;
            } // ticks boolean to true
            else if (command.equals("t")) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH) + 1;
                printMonth(year, month);
                display = true;
            } 
            else if (command.equals("ev") ){
                addEvent();
            }
            else if (command.equals("fp")){
            System.out.println("Enter month you wish to save (MM): ");
            month = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Name of saved file: ");
            String saveFile = scanner.next();
            saveMonth(year, month, saveFile);}

            
            else if (command.equals("q")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Unknown command. Please enter a valid command.");

            }
        } // end of while
    }// end of main

    public static int printMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        int days;
        if (month == 2) {
            days = 28;
            
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            days = 30;
            
        } else {
            days = 31;
            
        
        } 


        if (month > 12 || month < 1) {
            System.out.println("Enter a REAL month please");
        } else {
            int a = 1;
            int b = a + 6;
            if (month == 2 || month == 3 || month == 11) {// wednesday
                a -= 3;
                b -= 3;
            } else if (month == 4 || month == 7) {// Saturday
                a -= 6;
                b -= 6;
            } else if (month == 5) {// monday
                a -= 1;
                b -= 1;
            } else if (month == 6) {// thurday
                a -= 4;
                b -= 4;
            } else if (month == 8) {// tuesday
                a -= 2;
                b -= 2;
            } else if (month == 9 || month == 12) {// Firday
                a -= 5;
                b -= 5;
            }
            System.out.println("                                                           " + monthName(month));
            System.out.print("        Sun              Mon              Tue");
            System.out.println("              Wed             Thur              Fri               Sat");

            if (month == 4 || month == 7 || month == 12) {
                for (int i = 1; i <= 6; i++) {

                    lineOne();
                    lineTwo(a, b, days, month);
                    lineThree(a, b, days, month);
                    a += 7;
                    b += 7;
                }
                lineOne();
            }

            else {
                for (int i = 1; i <= 5; i++) {

                    lineOne();
                    lineTwo(a, b, days, month);
                    lineThree(a, b,days, month);
                    a += 7;
                    b += 7;
                }
                lineOne();
            }
        }
        return days;

    } // end of printMonth

    public static void lineOne() {
        for (int i = 1; i <= 60; i++) {
            System.out.print(".:");
        }
        System.out.println(".");
    }// end of lineOne

    public static void lineTwo(int a, int b, int days, int month) {
        for (int i = a; i <= b; i++) {

            if (i <= 0) {
                System.out.print("][               ");
            } else if (i < 10) {
                System.out.print("][" + i + "              ");
            } else if (i <= days) {
                System.out.print("][" + i + "             ");
            } else if (i >= days) {
                System.out.print("][               ");
            }

        }
    }

    public static void lineThree(int a, int b, int days, int month) {
        System.out.println("][");
        for (int i = a; i <= b; i++) {
            if (i <= 0 || i > days) {
                System.out.print("][               ");
            } else if (eventArray[month - 1][i - 1] != null) {
                
                String event = eventArray[month - 1][i - 1];
                //creates a "first" part of the event string so long event tittles are spilled into the next line
                //THIS IS THE HARDEST THING IVE EVER DONE IN MY LIFE
                String first = event.length() >15 ? event.substring(0,15):event;
                //Creates "buffer" for event values so framing isnt misaligned
                int bufferSize = 15 - first.length();
                String buffer = "";
                
                for (int j = 0; j < bufferSize; j++) {
                buffer += " ";
                }
                System.out.print("][" + first + buffer);
            } else {
                System.out.print("][               ");
            }
        }
        System.out.println("][");
        //Uses similar buffer logic as before but only for large event titles
        for (int i = a; i <= b; i++) {
            if (i<=0 || i > days){
            System.out.print("][               ");
        }else if(eventArray[month - 1][i - 1] != null) {
            String event = eventArray[month - 1][i - 1];
            if (event.length() > 15) { // in the situation an event is too long this will be used
                String second = event.substring(15);
                int bufferSize = 15 - second.length();
                String buffer = "";
                for (int j = 0; j < bufferSize; j++) {
                    buffer += " ";
                }
                System.out.print("][" + second + buffer);
            } else {
                System.out.print("][               ");
            }
        } else {
            System.out.print("][               ");
        }
     }
     System.out.println("][");
     // for regular empty spaces :)
     for (int i = 1; i <= 7; i++) {
        System.out.print("][               ");}
    
     System.out.println("][");
      } 

    public static void addEvent() {
        System.out.print("Enter event in MM/DD Event_name format: ");
        String input = scanner.nextLine();
        //Used Parsing of substring to assign values to the month and day int.
        int month = Integer.parseInt(input.substring(0, 2).trim()) - 1; 
        int day = Integer.parseInt(input.substring(3, 5).trim()) - 1;
        // takes rest of input and stores it as eventName
        String eventName = input.substring(6);
        // store the event in the event array
        eventArray[month][day] = eventName;
    }//end of addEvent
     
    //reads file calendarevents and places the data into the eventArray
    public static void readEvents() {
        try { File eventsFile = new File("calendarEvents.txt");
        if (eventsFile.exists()) {
             Scanner fileScanner = new Scanner(eventsFile);
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    int month = Integer.parseInt(line.substring(0, 2)) - 1;
                    int day = Integer.parseInt(line.substring(3, 5)) - 1;
                    String eventName = line.substring(6);
                    eventArray[month][day] = eventName;
                }
                fileScanner.close();}
            }catch (FileNotFoundException e){
                System.out.println("calendarEvents.txt not found.");
            }
                
            
            
            }// end of readEvents



// This just does not work at all. I really gave it my best effort,
//But it is 4:46AM 3/21 and I am out of time :(
  public static void saveMonth(int year, int month, String saveFile) {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    // Redirects all System.out.print outputs to stringWriter
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(new FilterOutputStream(new ByteArrayOutputStream()) {
        
        public void write(int b) {
        }
    }));

    printMonth(year, month);
    System.setOut(originalOut);

    
    try {
        PrintWriter fileWriter = new PrintWriter(saveFile);
        fileWriter.print(stringWriter.toString());
        fileWriter.close();
        System.out.println("File saved as " + saveFile);
    } catch (FileNotFoundException a) {
        System.out.println("Unable to save file, Sorry :(");
    }
}




    //hold over from Cal 2 when I thought we couldn't use Day_Of_Month
    public static String monthName(int month) {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "";
        }
    }//end of monthName

    public static void ascii() {
        for (int i = 0; i < 10; i++) {
            if (i == 0 || i == 4) {
                System.out.println("        @@        @@        ");
            } else if (i == 1 || i == 2 || i == 3 || i == 8) {
                System.out.println("      @@@@@@    @@@@@@     ");
            } else if (i == 5) {
                System.out.println("   @                    @   ");
            } else if (i == 6) {
                System.out.println("   @@                  @@   ");
            } else if (i == 7) {
                System.out.println("    @@                @@    ");
            } else {
                System.out.println("         @@@@@@@@@@     ");
            }
        }
    }

}// end of class
