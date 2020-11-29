package aprendo.maven.hibernate.manageBean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import aprendo.maven.hibernate.dao.DaoTelefones;
import aprendo.maven.hibernate.dao.DaoUsuario;
import aprendo.maven.hibernate.model.TelefoneUser;
import aprendo.maven.hibernate.model.UsuarioPessoa;

@ManagedBean(name = "telefonePessoaBean")
@ViewScoped
public class TelefonePessoaBean {
	
	
	private UsuarioPessoa user = new UsuarioPessoa();
	private DaoUsuario<UsuarioPessoa> daoUser = new DaoUsuario<UsuarioPessoa>();
	private DaoTelefones<TelefoneUser> daoTelefone = new DaoTelefones<TelefoneUser>();
	private TelefoneUser telefoneUser = new TelefoneUser();
	
	
	
	
	public UsuarioPessoa getUser() {
		return user;
	}


	public void setUser(UsuarioPessoa user) {
		this.user = user;
	}
	
	public DaoTelefones<TelefoneUser> getDaoTelefone() {
		return daoTelefone;
	}
	
	
	public void setDaoTelefone(DaoTelefones<TelefoneUser> daoTelefone) {
		this.daoTelefone = daoTelefone;
	}
	
	public TelefoneUser getTelefoneUser() {
		return telefoneUser;
	}
	
	
	public void setTelefoneUser(TelefoneUser telefoneUser) {
		this.telefoneUser = telefoneUser;
	}
	
	


// Métodos é aqui abaixo //


	@PostConstruct
	public void init() {
		
		String codUser = FacesContext.getCurrentInstance().getExternalContext().
				getRequestParameterMap().get("codigoUsers");
		
		user = daoUser.pesquisar2(Long.parseLong(codUser), UsuarioPessoa.class);

		
	}
	
	public String salvar() {
		telefoneUser.setUsuariopessoa(user);
		daoTelefone.salvar(telefoneUser);
		telefoneUser = new TelefoneUser();
		user = daoUser.pesquisar2(user.getId(), UsuarioPessoa.class);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Salvo com sucesso!!"));
		
		return "";
	}
	
	public String removerTelefone() throws Exception {
		
		daoTelefone.deletePorId(telefoneUser);
		user = daoUser.pesquisar2(user.getId(), UsuarioPessoa.class);
		telefoneUser = new TelefoneUser();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Excluído com sucesso!!"));
		return "";
	}

}
