package domain.participant;

import static java.util.stream.Collectors.toList;

import controller.dto.request.PlayerBettingMoney;
import controller.dto.response.PlayerOutcome;
import domain.game.deck.PlayerOutcomeFunction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Participants {
    public static final Dealer CACHED_DEALER = new Dealer();
    private static final List<Player> CACHED_PLAYERS = new ArrayList<>();

    private final List<Participant> participants;

    public Participants(final List<Participant> participants) {
        this.participants = participants;
    }

    public static Participants from(final List<PlayerBettingMoney> requests) {
        CACHED_DEALER.clear();
        CACHED_PLAYERS.clear();
        
        return Stream.concat(
                generatePlayers(requests),
                Stream.of(CACHED_DEALER)
        ).collect(Collectors.collectingAndThen(toList(), Participants::new));
    }

    private static Stream<Player> generatePlayers(final List<PlayerBettingMoney> requests) {
        return requests.stream()
                .map(request -> {
                    Player player = new Player(request.name(), request.bettingAmount());
                    CACHED_PLAYERS.add(player);
                    return player;
                });
    }

    public List<PlayerOutcome> getPlayersOutcomeIf(final PlayerOutcomeFunction function) {
        return getPlayers().stream()
                .map(player -> new PlayerOutcome(player, function.apply(player)))
                .toList();
    }

    public List<Participant> getParticipants() {
        return Collections.unmodifiableList(participants);
    }

    public List<Participant> getParticipantsStartsWithDealer() {
        return Stream.concat(
                Stream.of(CACHED_DEALER),
                getPlayers().stream()
        ).toList();
    }

    public List<Player> getPlayers() {
        if (CACHED_PLAYERS.isEmpty()) {
            return participants.stream()
                    .filter(Player.class::isInstance)
                    .map(Player.class::cast)
                    .toList();
        }
        return CACHED_PLAYERS;
    }
}
