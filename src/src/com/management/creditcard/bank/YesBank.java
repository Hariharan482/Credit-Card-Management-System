package com.management.creditcard.bank;

import com.management.creditcard.model.Bank;
import com.management.creditcard.model.CardType;

public class YesBank extends Bank {
    public YesBank() {
        super();
        setBankName("Yes Bank");
        addCardType(new CardType("Gold", 35000));
        addCardType(new CardType("Silver", 55000));
        addCardType(new CardType("Platinum", 70000));
    }
}
