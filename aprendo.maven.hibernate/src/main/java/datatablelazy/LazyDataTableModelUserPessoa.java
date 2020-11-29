package datatablelazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import aprendo.maven.hibernate.dao.DaoUsuario;
import aprendo.maven.hibernate.model.UsuarioPessoa;

@SuppressWarnings("serial")
public class LazyDataTableModelUserPessoa<T> extends LazyDataModel<UsuarioPessoa> {


	private DaoUsuario<UsuarioPessoa> daoUser = new DaoUsuario<UsuarioPessoa>();
	
	public List<UsuarioPessoa> list = new ArrayList<UsuarioPessoa>();
	
	private String sql = " from UsuarioPessoa";
	
	@Override
	public List<UsuarioPessoa> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		
		list = daoUser.getEntityManager().createQuery(getSql())
				.setFirstResult(first)
				.setMaxResults(pageSize)
				.getResultList();
		
		sql = " from UsuarioPessoa";
		
		setPageSize(pageSize);
		Integer qtdRegistros = Integer.parseInt(daoUser.getEntityManager()
				.createQuery("select count(1) " + getSql()).getSingleResult().toString());
		setRowCount(qtdRegistros);
		
		return list;
		
		//return super.load(first, pageSize, sortField, sortOrder, filters);
	}
	
	public void pesquisar(String campoPesquisar) {
		sql += " where nome like '%"+campoPesquisar+"%'";
	}
	
	
	//Getters and Setters

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}


	public List<UsuarioPessoa> getList() {
		return list;
	}


	public void setList(List<UsuarioPessoa> list) {
		this.list = list;
	}
	
	

}
