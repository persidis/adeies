/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.dipezak.adeies;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.time.ZoneId;

/**
 *
 * @author user
 */
public class Adeies {

    static CSVReader readCSVFile(String fileName, int skipLines) throws IOException {
        FileReader filereader = new FileReader(fileName, Charset.forName("ISO-8859-7"));
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withIgnoreQuotations(true)
                .build();
        CSVReader csvReader = new CSVReaderBuilder(filereader)
                .withSkipLines(skipLines)
                .withCSVParser(parser)
                .build();
        return csvReader;
    }

    static boolean writeToFile(List<Adeia> adeies, String filename) {
        try {
            String header = "ΑΦΜ;ΕΠΩΝΥΜΟ;ΟΝΟΜΑ;ΕΙΔΟΣ ΑΔΕΙΑΣ;ΕΝΑΡΞΗ;ΛΗΞΗ;ΑΠΟ ΣΥΣΤΗΜΑ\n";
            FileOutputStream fileStream = new FileOutputStream(filename);
            OutputStreamWriter file = new OutputStreamWriter(fileStream, "ISO-8859-7");
            file.write(header);
            for (Adeia a : adeies) {
                file.write(a.toCSVString());
            }
            file.close();
        } catch (IOException e) {
            System.out.println("Couldn't write to file.");
            return false;
        }

        return true;
    }

    static void order(List<Adeia> adeies) { // LastName + FirstName + startDate
        Collections.sort(adeies, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {

                String x1 = ((Adeia) o1).getLastName();
                String x2 = ((Adeia) o2).getLastName();
                int sComp = x1.compareTo(x2);
                if (sComp != 0) {
                    return sComp;
                }

                x1 = ((Adeia) o1).getFirstName();
                x2 = ((Adeia) o2).getFirstName();
                sComp = x1.compareTo(x2);
                if (sComp != 0) {
                    return sComp;
                }

                Date d1 = ((Adeia) o1).getStartDate();
                Date d2 = ((Adeia) o2).getStartDate();
                return d1.compareTo(d2);
            }
        });
    }

    static int weekendCount(Date startDate, Date endDate) {
        LocalDate startDateLocalDate = startDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate endDateLocalDate = endDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long diffInDays = ChronoUnit.DAYS.between(startDateLocalDate, endDateLocalDate);
        int cnt = 0;
        for (int i = 1; i <= diffInDays; i++) {
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            /*if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
                cnt++;
            }*/
            if (dayOfWeek == Calendar.SATURDAY) { // Το ΣΚ πάει πακέτο!
                cnt++;
                cnt++;
            }
            calendar.add(Calendar.DATE, 1);
        }
        return cnt;
    }

    static int weekendCount(Date startDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        int cnt = 0;
        for (int i = 1; i <= days; i++) {
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY) { // Το ΣΚ πάει πακέτο!
                cnt++;
                cnt++;
            }
            calendar.add(Calendar.DATE, 1);
        }
        return cnt;
    }

    static void removeDuplicateAdeies(List<Adeia> adeies) {
        int i = 0;
        // Αφαίρεση ίδιων αδειών από Bglossa και MySchool
        while (i < adeies.size() - 1) {
            Adeia current = adeies.get(i);
            Adeia next = adeies.get(i + 1);
            if (current.getAfm().equals(next.getAfm())
                    && (current.getType().equals(next.getType()))
                    && (current.getStartDate().equals(next.getStartDate()))
                    && (current.getEndDate().equals(next.getEndDate()))) {
                adeies.remove(i);
                adeies.remove(i);
            }
            i++;
        }
        // Αφαίρεση αδειών ίδιου τύπου που ξεκινούν από την ίδια ημερομηνία
        // και τελείωνουν στην ίδια ημερομηνία αλλά μεσολαβούν και άλλες ενδιάμεσα
        i = 0;
        while (i < adeies.size() - 3) { // Εξέταση ανά 4αδες
            Adeia current = adeies.get(i);
            Adeia next1 = adeies.get(i + 1);
            Adeia next2 = adeies.get(i + 2);
            Adeia next3 = adeies.get(i + 3);
            if (current.getAfm().equals(next1.getAfm())
                    && (current.getType().equals(next1.getType()))
                    && (current.getStartDate().equals(next1.getStartDate()))
                    && (next2.getAfm().equals(next3.getAfm()))
                    && (next2.getType().equals(next3.getType()))
                    && (next2.getEndDate().equals(next3.getEndDate()))) {
                adeies.remove(i);
                adeies.remove(i);
                adeies.remove(i);
                adeies.remove(i);
                i--;
            }
            i++;
        }
        i = 0;
        while (i < adeies.size() - 2) { // Εξέταση ανά 3αδες
            Adeia current = adeies.get(i);
            Adeia next1 = adeies.get(i + 1);
            Adeia next2 = adeies.get(i + 2);
            if (current.getAfm().equals(next1.getAfm())
                    && (current.getType().equals(next1.getType()))
                    && (current.getStartDate().equals(next1.getStartDate()))
                    && (current.getAfm().equals(next2.getAfm()))
                    && (current.getType().equals(next2.getType()))
                    && (current.getEndDate().equals(next2.getEndDate()))) {
                adeies.remove(i);
                adeies.remove(i);
                adeies.remove(i);
                i--;
            }
            i++;
        }
    }

    // Ελέγχει αν υπάρχουν συνεχόμενες άδειες χωρίς τα ΣΚ 
    static void extraMySchoolCheck(List<Adeia> diffList) throws ParseException {
        int i = 0;

        // Συνένωση αδειών ασθενείας με συνεχόμενες ημερομηνίες στο MySchool
        while (i < diffList.size() - 1) {
            Adeia current = diffList.get(i);
            Adeia next = diffList.get(i + 1);
            long diff = current.getEndDate().getTime() - next.getStartDate().getTime();
            long diffDays = diff / (1000 * 60 * 60 * 24);
            if (current.getAfm().equals(next.getAfm())) {
                if (current.getType().equals(next.getType())) {
                    if (diffDays == -1) {
                        Adeia replacement = new Adeia(current);
                        replacement.setEndDate(next.getEndDate());
                        diffList.remove(i);
                        diffList.set(i, replacement);
                    }
                }
            }
            i++;
        }

        // MySchool - Αν η κανονική άδεια περιλαμβάνει ΣΚ σπάσιμο στα 2
        i = 0;

        while (i < diffList.size()) {
            if (diffList.get(i).getType().equals("ΑΔΕΙΑ ΚΑΝΟΝΙΚΗ")) {
                if (weekendCount(diffList.get(i).getStartDate(), diffList.get(i).getEndDate()) > 0) {
                    LocalDate startDateLocalDate = diffList.get(i).getStartDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    LocalDate endDateLocalDate = diffList.get(i).getEndDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    long diffInDays = ChronoUnit.DAYS.between(startDateLocalDate, endDateLocalDate);
                    Adeia ending = new Adeia(diffList.get(i));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(diffList.get(i).getStartDate());

                    for (int j = 1; j < diffInDays; j++) {
                        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                        if (dayOfWeek == Calendar.FRIDAY) {
                            diffList.get(i).setEndDate(calendar.getTime());
                            calendar.add(Calendar.DATE, 3);
                            ending.setStartDate(calendar.getTime());
                            diffList.add(i + 1, ending);
                            j = j + 3;
                        }
                        calendar.add(Calendar.DATE, 1);
                    }
                }
            }
            i++;
        }
    }

    static void populateMySchool(List<Adeia> adeiesMySchool, CSVReader csvReaderMySchool) throws IOException, CsvValidationException, ParseException {
        String[] nextRecord;
        int column;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = new Date();
        Date endDate;
        Date schoolYearStartDate;
        schoolYearStartDate = dateFormat.parse("01/09/2023");
        Calendar cal = Calendar.getInstance();
        boolean flag = false;

        while ((nextRecord = csvReaderMySchool.readNext()) != null) {
            Adeia tempAdeia = new Adeia();
            column = 1;
            for (String cell : nextRecord) {
                if (flag) {
                    flag = false;
                    break;
                }
                switch (column) {
                    case 1 -> {
                        if (!"".equals(cell)) {  // Αν το ΑΜ δεν είναι κενό
                            flag = true;
                            break;
                        }
                    }
                    case 2 -> {
                        String digits = cell.replaceAll("[^0-9.]", "");
                        tempAdeia.setAfm(digits);
                    }
                    case 4 -> {

                        int idxFirst = cell.indexOf('-');
                        if (idxFirst > 0) {
                            String lastName = cell.substring(0, idxFirst);
                            tempAdeia.setLastName(lastName);
                        } else {
                            tempAdeia.setLastName(cell);
                        }
                    }
                    case 5 -> {
                        int idxLast = cell.lastIndexOf('-');
                        if (idxLast > 0) {
                            String firstName = cell.substring(idxLast + 1);
                            tempAdeia.setFirstName(firstName);
                        } else {
                            tempAdeia.setFirstName(cell);
                        }
                    }
                    case 11 -> {
                        if ("".equals(cell)) {
                            flag = true;
                            break;
                        }
                    }
                    case 15 -> {
                        if ("5-Ανακλήθηκε".equals(cell)) {
                            flag = true;
                            break;
                        }
                        // tempAdeia.setStatus(cell);
                    }
                    case 16 -> {
                        tempAdeia.setType(cell);
                    }
                    case 17 -> {
                        startDate = dateFormat.parse(cell);
                        if (startDate.before(schoolYearStartDate)) {
                            flag = true;
                            break;
                        } else {
                            tempAdeia.setStartDate(startDate);
                        }
                    }
                    case 26 -> {
                        cal.setTime(startDate);
                        if ("ΑΔΕΙΑ ΠΑΤΡΟΤΗΤΑΣ".equals(tempAdeia.getType()) || ("ΑΔΕΙΑ ΚΑΝΟΝΙΚΗ".equals(tempAdeia.getType()))) {
                            cal.add(Calendar.DATE, Integer.parseInt(cell) - 1 + weekendCount(tempAdeia.getStartDate(), Integer.parseInt(cell)));
                        } else {
                            cal.add(Calendar.DATE, Integer.parseInt(cell) - 1);
                        }
                        endDate = cal.getTime();
                        tempAdeia.setEndDate(endDate);
                    }
                    default -> {
                    }
                }
                column++;
                //out.print(cell + "\t");
            }
            // Αν είναι αναπληρωτής + δεν ανακλήθηκε + δεν είναι πριν από αρχή σχολ. χρονιάς ++
            //      if ((tempAdeia.getAfm() != null) && (tempAdeia.getStatus() != null) &&  (!"5-Ανακλήθηκε".equals(tempAdeia.getStatus() )) && (tempAdeia.getStartDate() != null)) {
            if (tempAdeia.getStartDate() != null) {
                tempAdeia.setFrom("MySchool");
                adeiesMySchool.add(tempAdeia);
            }
        } // End while
    }

    private static void populateBGlossa(List<Adeia> adeiesBGlossa, CSVReader csvReaderBGlossa) throws IOException, CsvValidationException, ParseException {
        String[] nextRecord;
        int column;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate;
        Date endDate;
        boolean flag = false;

        while ((nextRecord = csvReaderBGlossa.readNext()) != null) {
            Adeia tempAdeia = new Adeia();
            column = 1;
            for (String cell : nextRecord) {
                if (flag) {
                    flag = false;
                    break;
                }
                switch (column) {
                    case 2 -> {
                        int idxLast = cell.lastIndexOf(' ');
                        int idxFirst = cell.indexOf(' ');
                        String lastName = cell.substring(0, idxFirst);
                        String firstName = cell.substring(idxLast + 1);
                        // Αν περιέχουν - τότε κρατάμε το πρώτο επίθετο και το δεύτερο όνομα
                        if (lastName.indexOf('-') > 0) {
                            tempAdeia.setLastName(lastName.substring(0, lastName.indexOf('-')));
                        } else {
                            tempAdeia.setLastName(lastName);
                        }
                        if (firstName.contains("-")) {
                            tempAdeia.setFirstName(firstName.substring(firstName.indexOf('-') + 1));
                        } else {
                            tempAdeia.setFirstName(firstName);
                        }
                    }
                    case 3 -> {
                        String digits = cell.replaceAll("[^0-9.]", "");
                        tempAdeia.setAfm(digits);
                    }
                    case 5 -> {
                        if ("ΑΠΕΡΓΙΑ".equals(cell)
                                || ("ΕΠΙΔΟΤΗΣΗ ΕΙΣΦΟΡΩΝ ΜΗΤΕΡΑΣ".equals(cell))) {
                            flag = true;
                            break;
                        }
                        tempAdeia.setType(cell);
                    }
                    case 6 -> {
                        startDate = dateFormat.parse(cell);
                        tempAdeia.setStartDate(startDate);
                    }
                    case 7 -> {
                        endDate = dateFormat.parse(cell);
                        tempAdeia.setEndDate(endDate);
                    }
                    default -> {
                    }
                }
                column++;
            }
            if (tempAdeia.getStartDate() != null) {
                tempAdeia.setFrom("Bglossa");
                adeiesBGlossa.add(tempAdeia);
            }
        } // End while   
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException, CsvValidationException, ParseException {
        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        List<Adeia> adeiesMySchool = new ArrayList<>();
        List<Adeia> adeiesBGlossa = new ArrayList<>();
        CSVReader csvReaderMySchool = readCSVFile("fromMySchool.csv", 1);
        CSVReader csvReaderBGlossa = readCSVFile("CsvMassForLeave.csv", 2);

        populateMySchool(adeiesMySchool, csvReaderMySchool);
        populateBGlossa(adeiesBGlossa, csvReaderBGlossa);

        order(adeiesBGlossa);
        order(adeiesMySchool);
        extraMySchoolCheck(adeiesMySchool);
        List<Adeia> differences1 = new ArrayList<>(adeiesBGlossa);
        differences1.removeAll(adeiesMySchool);
        List<Adeia> differences2 = new ArrayList<>(adeiesMySchool);
        differences2.removeAll(adeiesBGlossa);
        differences2.addAll(differences1);
        order(differences2);
        removeDuplicateAdeies(differences2);

        if (writeToFile(differences2, "diff.csv")) {
            out.println("Επιτυχής εγγραφή αρχείου CSV");
        } else {
            out.println("Αποτυχία εγγραφή αρχείου CSV");
        }
        if (writeToFile(adeiesMySchool, "MySchool.csv")) {
            out.println("Επιτυχής εγγραφή αρχείου CSV");
        } else {
            out.println("Αποτυχία εγγραφή αρχείου CSV");
        }
        if (writeToFile(adeiesBGlossa, "bglossa.csv")) {
            out.println("Επιτυχής εγγραφή αρχείου CSV");
        } else {
            out.println("Αποτυχία εγγραφή αρχείου CSV");
        }
    } // main end
} // Class end
