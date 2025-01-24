package com.java.credit.card.management;

class YesBank extends Bank {

    YesBank() {
        setBankName("Yes Bank");
        addCardType(new CardType("Gold", 35000));
        addCardType(new CardType("Silver", 55000));
        addCardType(new CardType("Platinum", 70000));
    }

}
