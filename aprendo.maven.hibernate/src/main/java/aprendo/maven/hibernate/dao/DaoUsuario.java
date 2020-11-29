package aprendo.maven.hibernate.dao;

import java.util.List;

import javax.persistence.Query;

import aprendo.maven.hibernate.model.UsuarioPessoa;

public class DaoUsuario<E> extends DaoGenerico<UsuarioPessoa> {
	
	public void removerUsuario(UsuarioPessoa pessoa) throws Exception {
		getEntityManager().getTransaction().begin();
		
		getEntityManager().remove(pessoa);
		
		getEntityManager().getTransaction().commit();
		
		
	}

	public List<UsuarioPessoa> pesquisar(String campoPesquisar) {
		Query query = super.getEntityManager().createQuery("from UsuarioPessoa where nome like '%" + campoPesquisar + "%'" );
		
		
		return query.getResultList();
	}

}
