package blackjack.domain;

import blackjack.domain.dealer.Dealer;
import blackjack.domain.participant.ParticipantName;
import blackjack.domain.result.WinningResult;
import java.util.List;

public class BettingResults {
    private final List<BettingResult> bettingResults;

    public BettingResults(final List<BettingResult> bettingResults) {
        this.bettingResults = bettingResults;
    }

    public static BettingResults of(final PlayerBettings playerBettings, final WinningResult winningResult) {
        List<BettingResult> bettingResults = winningResult.getParticipantsResult().entrySet().stream()
                .flatMap(entry -> playerBettings.getPlayerBettings().stream()
                        .filter(playerBetting -> playerBetting.isName(entry.getKey()))
                        .map(playerBetting -> BettingResult.of(entry.getValue(), playerBetting)))
                .toList();
        return new BettingResults(bettingResults);
    }

    public BettingResult getDealerResult() {
        int dealerProfit = bettingResults.stream()
                .mapToInt(BettingResult::getBetting)
                .sum();
        return new BettingResult(new ParticipantName(Dealer.DEALER_NAME), -dealerProfit);
    }

    public List<BettingResult> getBettingResults() {
        return bettingResults;
    }
}
