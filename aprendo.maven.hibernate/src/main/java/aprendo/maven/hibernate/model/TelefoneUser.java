package aprendo.maven.hibernate.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

@Entity
public class TelefoneUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String numero;
	
	@Column(nullable = false)
	private String tipo;
	
	@Column(name = "numero_fixo")
	private String numerofixo;
	
	@Column(name = "tipo_fixo")
	private String tipofixo;
	
	//Ligando um usuário aos telefones
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private UsuarioPessoa usuariopessoa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public UsuarioPessoa getUsuariopessoa() {
		return usuariopessoa;
	}

	public void setUsuariopessoa(UsuarioPessoa usuariopessoa) {
		this.usuariopessoa = usuariopessoa;
	}
	
	public String getNumerofixo() {
		return numerofixo;
	}

	public void setNumerofixo(String numerofixo) {
		this.numerofixo = numerofixo;
	}
	
	
	public String getTipofixo() {
		return tipofixo;
	}

	public void setTipofixo(String tipofixo) {
		this.tipofixo = tipofixo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TelefoneUser other = (TelefoneUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
			

}
