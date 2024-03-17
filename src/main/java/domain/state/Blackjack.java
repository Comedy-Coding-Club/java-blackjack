package domain.state;

import domain.Card;
import domain.Hand;

public class Blackjack extends Finished {
    public Blackjack(final Hand hand) {
        super(hand);
    }

    @Override
    public State draw(final Card card) {
        throw new UnsupportedOperationException("블랙잭 상태의 플레이어는 카드를 뽑을 수 없습니다.");
    }

    @Override
    public double profitRate(final State state) {
        if (state instanceof Blackjack) {
            return 0;
        }
        return 1.5;
    }

    @Override
    public boolean isBlackjack() {
        return true;
    }

    @Override
    public State stand() {
        return this;
    }
}