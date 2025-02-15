package com.management.creditcard.bank;

import com.management.creditcard.model.Bank;
import com.management.creditcard.model.CardType;

public class IOBank extends Bank {
    public IOBank(){
        setBankName("IOB Bank");
        addCardType(new CardType("Infinity", 50000));
        addCardType(new CardType("Prestige", 100000));
        addCardType(new CardType("Wanderer", 150000));
    }
}
