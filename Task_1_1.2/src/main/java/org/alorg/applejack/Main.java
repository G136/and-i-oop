package org.alorg.applejack;

public class Main {
    private static char cardToSuit(String card) {
        return switch (card.charAt(1) & 0xF0) {
            case 0xA0 -> '♠';
            case 0xB0 -> '♥';
            case 0xC0 -> '♦';
            case 0xD0 -> '♣';
            default   -> '?';
        };
    }

    private static int cardToRank(String card) {
        return card.charAt(1) & 0xF;
    }

    public static void main(String[] args) {
        String myCard = "🂭";
        System.out.println(myCard + ": " + cardToSuit(myCard) + cardToRank(myCard));

        // printing all cards demo
        for (int suit = 0xA0; suit <= 0xD0; suit += 0x10) {
            for (int rank = 0x1; rank < 0xF; rank++) {
                System.out.print("\uD83C" + (char) (0xDC00 + suit + rank));
            }
            System.out.print('\n');
        }
    }
}