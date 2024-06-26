/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dipezak.adeies;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author user
 */
public class Adeia {
    private String afm;
    private String lastName;
    private String firstName;
    private String type; // Αιμοδοτική, ΑΝΑΡΡΩΤΙΚΗ - με Ιατρική Γνωμάτευση, Κανονική, Για ετήσιο γυναικολογικό έλεγχο, Ασθένειας τέκνου ktl
    private Date startDate;
    private Date endDate; 
    private String from;
    
    private String matchType(String type) {
        switch (type) {
            case "Αιμοδοτική" -> {
                        return "ΑΔΕΙΑ ΑΙΜΟΔΟΣΙΑΣ/ΑΙΜΟΛΗΨΙΑΣ";
                    }
            case "Αιμοληψίας (σε εργάσιμη ημέρα)" -> {
                return "ΑΔΕΙΑ ΑΙΜΟΔΟΣΙΑΣ/ΑΙΜΟΛΗΨΙΑΣ";
            }
            case "ΑΝΑΡΡΩΤΙΚΗ - με Ιατρική Γνωμάτευση" -> {
                return "ΑΔΕΙΑ ΑΣΘΕΝΕΙΑΣ";
            }
            case "ΑΝΑΡΡΩΤΙΚΗ - με Υπεύθυνη Δήλωση" -> {
                return "ΑΔΕΙΑ ΑΣΘΕΝΕΙΑΣ";
            }
            case "ΑΝΑΡΡΩΤΙΚΗ - με Γνωμάτευση Νοσοκομείου (ν.3528/2007 άρ.56, παρ.3)" -> {
                return "ΑΔΕΙΑ ΑΣΘΕΝΕΙΑΣ";
            }
            case "Ανατροφής παιδιού (με πλήρεις αποδοχές)" -> {
                return "ΑΔΕΙΑ ΑΝΑΤΡΟΦΗΣ ΤΕΚΝΟΥ ΤΡΙΜΗΝΗ (Ν. 4599/2019)";
            }
            case "Ασθένειας τέκνου" -> {
                return "ΑΔΕΙΑ ΑΣΘΕΝΕΙΑΣ ΤΕΚΝΟΥ";
            }
            case "Γάμου" -> {
                return "ΑΔΕΙΑ ΓΑΜΟΥ/ΣΥΜΦΩΝΟΥ ΣΥΜΒΙΩΣΗΣ";
            }
            case "Για επιμορφωτικούς ή επιστημονικούς λόγους" -> {
                return "ΑΔΕΙΑ ΓΙΑ ΕΠΙΣΤΗΜΟΝΙΚΟΥΣ ΚΑΙ ΕΠΙΜΟΡΦΩΤΙΚΟΥΣ ΛΟΓΟΥΣ";
            }
            case "Για ετήσιο γυναικολογικό έλεγχο" -> {
                return "ΑΔΕΙΑ ΕΤΗΣΙΟΥ ΓΥΝΑΙΚΟΛΟΓΙΚΟΥ ΕΛΕΓΧΟΥ";
            }
            case "Ειδική λόγω Αναπηρίας" -> {
                return "ΑΔΕΙΑ ΑΝΑΠΗΡΙΑΣ ΕΙΔΙΚΗ";
            }
            case "Εκλογική" -> {
                return "ΑΔΕΙΑ ΕΚΛΟΓΙΚΗ ΕΙΔΙΚΗ";
            }
            case "Εξετάσεων" -> {
                return "ΑΔΕΙΑ ΕΞΕΤΑΣΕΩΝ (μαθητές, σπουδαστές ή φοιτητές)";
            }
            case "Θανάτου (συζύγου ή συγγενούς έως και β βαθμού)" -> {
                return "ΑΔΕΙΑ ΘΑΝΑΤΟΥ ΣΥΓΓΕΝΟΥΣ";
            }
            case "Κανονική" -> {
                return "ΑΔΕΙΑ ΚΑΝΟΝΙΚΗ";
            }
            case "ΜΗΤΡΟΤΗΤΑΣ - Κύησης" -> {
                return "ΑΔΕΙΑ ΚΥΗΣΗΣ";
            }
            case "ΜΗΤΡΟΤΗΤΑΣ - Λοχείας" -> {
                return "ΑΔΕΙΑ ΛΟΧΕΙΑΣ";
            }
            case "ΜΗΤΡΟΤΗΤΑΣ - Προγεννητικού Ελέγχου" -> {
                return "ΑΔΕΙΑ ΠΡΟΓΕΝΝΗΤΙΚΩΝ ΕΞΕΤΑΣΕΩΝ";
            }
            case "Παρακολούθησης σχολικής επίδοσης τέκνου" -> {
                return "ΑΔΕΙΑ ΠΑΡΑΚΟΛΟΥΘΗΣΗΣ ΣΧΟΛΙΚΗΣ ΕΠΙΔΟΣΗΣ ΤΕΚΝΩΝ";
            }
            case "Πατρότητας" -> {
                return "ΑΔΕΙΑ ΠΑΤΡΟΤΗΤΑΣ";
            }
            default -> {
                return type;
            }
        }
    }

    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = matchType(type);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) throws ParseException {
         this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) throws ParseException {
        this.endDate = endDate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
    
    

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "Adeia{" + "afm=" + afm + ", lastName=" + lastName + ", firstName=" + firstName + 
                ", type=" + type + ", startDate=" + dateFormat.format(startDate) + ", endDate=" + dateFormat.format(endDate) + '}';
    }   
 
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Adeia that = (Adeia) o;
        return afm.equals(that.afm)
                && lastName.equals(that.lastName)
                && firstName.equals(that.firstName)
                && type.equals(that.type)
                && startDate.equals(that.startDate)
                && endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.afm);
        hash = 13 * hash + Objects.hashCode(this.lastName);
        hash = 13 * hash + Objects.hashCode(this.firstName);
        hash = 13 * hash + Objects.hashCode(this.type);
        hash = 13 * hash + Objects.hashCode(this.startDate);
        hash = 13 * hash + Objects.hashCode(this.endDate);
        return hash;
    }

    public String toCSVString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "\'" + afm + "\';" + lastName + ";" + firstName + ";" + type + ";" + 
                dateFormat.format(startDate) + ";" + dateFormat.format(endDate) +
                ";" + from + '\n';
    }
    
    public Adeia() {
        this.afm = null;
        this.lastName = null;
        this.firstName = null;
        this.type = null;
        this.startDate = null;
        this.endDate = null;
        this.from = null;
    }
    
    public Adeia(Adeia a) {
        this.afm = a.getAfm();
        this.lastName = a.getLastName();
        this.firstName = a.getFirstName();
        this.type = a.getType();
        this.startDate = a.getStartDate();
        this.endDate = a.getEndDate();
        this.from = a.getFrom();
    }
    
    public Adeia(String afm, String lastName, String firstName, String type, String status, Date startDate, Date endDate, String from) {
        this.afm = afm;
        this.lastName = lastName;
        this.firstName = firstName;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.from = from;
    }    
}