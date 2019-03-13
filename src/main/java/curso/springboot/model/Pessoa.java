package curso.springboot.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Pessoa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull(message="Campo Nome é obrigatório!")
	@NotEmpty(message="Campo Nome é obrigatório!")
	private String nome;
	
	@NotNull(message="Campo Sobrenome é obrigatório!")
	@NotEmpty(message="Campo Sobrenome é obrigatório!")
	private String sobrenome;
	
	@Min(value= 18,message ="Idade Inválida")
	private int idade;
	
	@OneToMany(mappedBy="pessoa",cascade = {CascadeType.ALL},orphanRemoval=true)
	private List<Telefone> telefones = new ArrayList<>();
	
	
	public List<Telefone> getTelefones() {
		return telefones;
	}
	
	public void setTelefones(List<Telefone> telefones) {
		this.telefones.clear();
		this.telefones.addAll(telefones);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	
	
	
	
	
}
