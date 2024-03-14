package domain;

import domain.participant.Hands;
import java.util.Arrays;
import java.util.function.BiPredicate;

public enum Result {

    WIN("승", Result::winningCondition),
    WIN_BLACKJACK("승", Result::winningBlackJackCondition),
    TIE("무", Result::tieCondition),
    LOSE("패", Result::loseCondition);

    private final String value;
    private final BiPredicate<Hands, Hands> condition;

    Result(final String value, final BiPredicate<Hands, Hands> condition) {
        this.value = value;
        this.condition = condition;
    }

    public Result reverse() {
        if (Result.WIN.equals(this) || Result.WIN_BLACKJACK.equals(this)) {
            return LOSE;
        }

        if (Result.LOSE.equals(this)) {
            return WIN;
        }

        return TIE;
    }

    public static Result calculate(final Hands hands, final Hands target) {
        return Arrays.stream(Result.values())
                .filter(result -> result.condition.test(hands, target))
                .findFirst()
                .orElseThrow();
    }

    private static boolean winningCondition(final Hands hands, final Hands target) {
        return (!hands.isBust() && target.isBust())
                || (!hands.isBust() && hands.sum() > target.sum() && !hands.isBlackJack())
                || (!hands.isBust() && hands.sum() == target.sum() && !hands.isBlackJack()
                && hands.size() < target.size());
    }

    private static boolean winningBlackJackCondition(final Hands hands, final Hands target) {
        return hands.sum() > target.sum() && hands.isBlackJack();
    }

    private static boolean tieCondition(final Hands hands, final Hands target) {
        return hands.sum() == target.sum() && hands.size() == target.size() && !hands.isBust();
    }

    private static boolean loseCondition(final Hands hands, final Hands target) {
        return hands.isBust() || hands.sum() < target.sum() || !target.isBust();
    }

    public String getValue() {
        return value;
    }
}