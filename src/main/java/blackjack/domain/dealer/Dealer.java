package blackjack.domain.dealer;

import blackjack.domain.card.Card;
import blackjack.domain.card.Hands;
import blackjack.domain.participant.Participant;
import blackjack.domain.result.Score;
import java.util.ArrayList;
import java.util.List;

public class Dealer extends Participant {
    private static final Score NEED_CARD_CRITERION = new Score(17);
    public static final String DEALER_NAME = "딜러";

    private final Deck deck;

    public Dealer(final Deck deck) {
        super(DEALER_NAME);
        this.deck = deck;
    }

    public boolean needMoreCard() {
        return NEED_CARD_CRITERION.isBiggerThan(calculate());
    }

    public void addStartCard() {
        final List<Card> cards = drawCards(2);
        cards.forEach(this::addCard);
    }

    public void addCard() {
        hands.add(drawCard());
    }

    public void shuffleDeck() {
        deck.shuffle();
    }

    public Card drawCard() {
        return deck.pick();
    }

    public List<Card> drawCards(final int count) {
        return deck.pick(count);
    }

    public Hands getOpenedCards() {
        List<Card> openedCards = new ArrayList<>();
        openedCards.add(hands.getFirstCard());

        return new Hands(openedCards);
    }
}