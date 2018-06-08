package model;

public class Warrior {
	private long userId;
	
	private int damage;
	
	private int maxHealth;
	
	private int health;

	private int rating;
	
	private String name;
	
	public Warrior(long userId, String name, int damage,  int maxHealth,  int health, int rating) {
		super();
		this.name = name;
		this.damage = damage;
		this.health = health;
		this.maxHealth = maxHealth;
		this.rating = rating;
		this.userId = userId;
	}

	public int getDamage() {
		return damage;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public String getName() {
		return name;
	}

	public int getRating() {
		return rating;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public long getUserId() {
		return userId;
	}
	
}
