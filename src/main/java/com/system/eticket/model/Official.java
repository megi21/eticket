package com.system.eticket.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "td_official")
public class Official {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long officialId;
	@Column
	private String name;
	@Column(unique=true,nullable = false)
	private String officialCode;
	@Column
	private String birthDate;
	@OneToMany(mappedBy = "official")
    private List<Ticket> tickets;
	
	public Official(Long officialId) {
        this.officialId = officialId;
    }

}
