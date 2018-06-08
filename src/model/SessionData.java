package model;

import java.util.Date;

public class SessionData {
	private long userId;
	
	private Warrior warrior;
	
	private boolean readyForBattle;
	
	private Warrior opponent;
	
	private Date opponentFoundAt;
	
	private boolean expired = false;
	
	private double queriesExecutionTime;
	private int queriesCount;

	public SessionData(long userId, Warrior warrior, boolean readyForBattle,
			Warrior opponent, Date opponentFoundAt) {
		super();
		this.userId = userId;
		this.warrior = warrior;
		this.readyForBattle = readyForBattle;
		this.opponent = opponent;
		this.opponentFoundAt = opponentFoundAt;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Warrior getWarrior() {
		return warrior;
	}

	public void setWarrior(Warrior warrior) {
		this.warrior = warrior;
	}

	public boolean isReadyForBattle() {
		return readyForBattle;
	}

	public void setReadyForBattle(boolean readyForBattle) {
		this.readyForBattle = readyForBattle;
	}

	public Warrior getOpponent() {
		return opponent;
	}

	public void setOpponent(Warrior opponent) {
		this.opponent = opponent;
	}

	public Date getOpponentFoundAt() {
		return opponentFoundAt;
	}

	public void setOpponentFoundAt(Date opponentFoundAt) {
		this.opponentFoundAt = opponentFoundAt;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public boolean isExpired() {
		return expired;
	}

	public double getQueriesExecutionTime() {
		return queriesExecutionTime;
	}

	public void setQueriesExecutionTime(double queriesExecutionTime) {
		this.queriesExecutionTime = queriesExecutionTime;
	}

	public int getQueriesCount() {
		return queriesCount;
	}

	public void setQueriesCount(int queriesCount) {
		this.queriesCount = queriesCount;
	}
	
}
