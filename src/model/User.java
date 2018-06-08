package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "users", indexes = {@Index(name = "users_id_idx",  columnList="id", unique = true), 
								  @Index(name = "users_usernick_idx", columnList="usernick", unique = true)})
public class User {
	
	public enum BattleResult{win, loose}
	
	@Id 
    @Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "usernick", nullable = false)
	private String usernick;
	
	@Column(name = "rating")
	private int rating;
	
	@Column(name = "damage")
	private int damage;
	
	@Column(name = "health")
	private int health;
	
	@Column(name="battle_result", nullable = true) 
	@Enumerated(EnumType.STRING)
	private BattleResult battleResult;
	
	public User(){}

	public String getPassword() {
		return password;
	}

	public String getUsernick() {
		return usernick;
	}

	public int getRating() {
		return rating;
	}

	public int getDamage() {
		return damage;
	}

	public int getHealth() {
		return health;
	}

	public long getId() {
		return id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsernick(String usernick) {
		this.usernick = usernick;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public BattleResult getBattleResult() {
		return battleResult;
	}

	public void setBattleResult(BattleResult battleResult) {
		this.battleResult = battleResult;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
