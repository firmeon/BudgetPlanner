package fr.firmeon.budgetplanner.model;

import java.time.LocalDate;

public class Transaction {

    private final int id;
    private final int amount; //Stocked as cents
    private final LocalDate date;
    private final String description;

    public Transaction(int id, int amount, LocalDate date, String description) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
