package blackjack.domain;

import blackjack.domain.participant.ParticipantName;

public class PlayerBetting {
    private final ParticipantName name;
    private final int betting;

    public PlayerBetting(final String name, final int betting) {
        this.name = new ParticipantName(name);
        this.betting = betting;
    }

    public boolean isName(final ParticipantName otherName) {
        return this.name.equals(otherName);
    }

    public ParticipantName getName() {
        return name;
    }

    public int applyWinstatus(final double betMultiplier) {
        return (int) (betting * betMultiplier);
    }
}