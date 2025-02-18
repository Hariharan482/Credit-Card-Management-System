package com.management.creditcard.model;

public class CardType {
    private String cardTypeName;
    private long limit;

    public CardType(String cardTypeName, long limit) {
        this.cardTypeName = cardTypeName;
        this.limit = limit;
    }

    protected void setLimit(int limit) {
        this.limit = limit;
    }

    protected void changeCardType(String name) {
        this.cardTypeName = name;
    }

    public String getCardTypeName() {
        return this.cardTypeName;
    }

    protected long getCardLimit() {
        return this.limit;
    }
}
