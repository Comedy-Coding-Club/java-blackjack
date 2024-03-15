package domain.participant;

import static domain.participant.Hands.BLACK_JACK;

import domain.Result;

public class Player extends Participant {

    private final BetAmount betAmount;

    public Player(final Name name, final Hands hands) {
        super(name, hands);
        validateName(name);
        this.betAmount = BetAmount.from(100);
    }

    public Player(final Name name, final Hands hands, final BetAmount betAmount) {
        super(name, hands);
        validateName(name);
        this.betAmount = betAmount;
    }

    public BetAmount calculateProfitBy(final Dealer dealer) {
        return Result.calculate(super.getHands(), dealer.getHands(), betAmount);
    }

    @Override
    public boolean canDeal() {
        return handsSum() < BLACK_JACK;
    }

    private void validateName(final Name name) {
        if (Dealer.NAME.equals(name.getValue())) {
            throw new IllegalArgumentException("[ERROR] 사용할 수 없는 이름입니다.");
        }
    }
}
