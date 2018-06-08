package model.dto;

public class BattleDto {

	private String opponentName;
	private int opponentDamage;
	private int opponentHealth;
	private int myDamage;
	private int myHealth;
	private String myName;
	private int remainSeconds;
	private int opponentHealthPercent;
	public BattleDto(String opponentName, int opponentDamage,
			int opponentHealth, String myName, int myDamage, int myHealth,
			int opponentHealthPercent, int remainSeconds) {
		super();
		this.opponentName = opponentName;
		this.opponentDamage = opponentDamage;
		this.opponentHealth = opponentHealth;
		this.myDamage = myDamage;
		this.myHealth = myHealth;
		this.myName = myName;
		this.remainSeconds = remainSeconds;
		this.opponentHealthPercent = opponentHealthPercent;
	}
	public String getMyName() {
		return myName;
	}
	public String getOpponentName() {
		return opponentName;
	}
	public int getOpponentDamage() {
		return opponentDamage;
	}
	public int getOpponentHealth() {
		return opponentHealth;
	}
	public int getMyDamage() {
		return myDamage;
	}
	public int getMyHealth() {
		return myHealth;
	}
	public int getRemainSeconds() {
		return remainSeconds;
	}
	public int getOpponentHealthPercent() {
		return opponentHealthPercent;
	}
	
}
