package aprendo.maven.hibernate.manageBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.google.gson.Gson;

import aprendo.maven.hibernate.dao.DaoEmails;
import aprendo.maven.hibernate.dao.DaoUsuario;
import aprendo.maven.hibernate.model.EmailUser;
import aprendo.maven.hibernate.model.UsuarioPessoa;
import datatablelazy.LazyDataTableModelUserPessoa;


@ManagedBean(name = "usuarioPessoaBean")
@ViewScoped
public class UsuarioPessoaBean {
	
	private UsuarioPessoa usuPessoa = new UsuarioPessoa();	
	private LazyDataTableModelUserPessoa<UsuarioPessoa> list = new LazyDataTableModelUserPessoa<UsuarioPessoa>();
	private DaoUsuario<UsuarioPessoa> daoGenerico = new DaoUsuario<UsuarioPessoa>();
	private BarChartModel barChartModel = new BarChartModel();
	private EmailUser emailUser = new EmailUser();
	private DaoEmails<EmailUser> daoEmail = new DaoEmails<EmailUser>();
	private String campoPesquisar;
	
	
	@PostConstruct
	public void init() {
		list.load(0, 5, null, null, null);
		montarGrafico();
		
	}



	private void montarGrafico() {
		barChartModel = new BarChartModel();
		
		
		ChartSeries userSalario = new ChartSeries();//Grupo de funcionarios
		for (UsuarioPessoa usuarioPessoa : list.list) {
			
		
			userSalario.set(usuarioPessoa.getNome(), usuarioPessoa.getSalario());//Add salários
			
		}
		barChartModel.addSeries(userSalario);//Add o grupo
		barChartModel.setTitle("Gráfico de Salários");
	}


	
	public LazyDataTableModelUserPessoa<UsuarioPessoa> getList() {
		montarGrafico();
		return list;
	}
	
	
	public UsuarioPessoa getUsuPessoa() {
		return usuPessoa;
	}

	public void setUsuPessoa(UsuarioPessoa usuPessoa) {
		this.usuPessoa = usuPessoa;
	}
	
	public BarChartModel getBarChartModel() {
		return barChartModel;
	}
	
	
	
	public void setBarChartModel(BarChartModel barChartModel) {
		this.barChartModel = barChartModel;
	}
	
	public EmailUser getEmailUser() {
		return emailUser;
	}
	
	
	
	public void setEmailUser(EmailUser emailUser) {
		this.emailUser = emailUser;
	}
	
	public String getCampoPesquisar() {
		return campoPesquisar;
	}
	
	
	
	public void setCampoPesquisar(String campoPesquisar) {
		this.campoPesquisar = campoPesquisar;
	}
	
	

	//Métodos de manipulação do banco de dados





	public String salvar() {
		
		daoGenerico.salvar(usuPessoa);
		list.list.add(usuPessoa);
		usuPessoa = new UsuarioPessoa();
		init();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Salvo com sucesso!!"));
		
		return "";
	}
	
	public String salvarMerge() {
		
		daoGenerico.salvarMerge(usuPessoa);
		list.list.add(usuPessoa);
		usuPessoa = new UsuarioPessoa();
		init();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Salvo com sucesso!!"));
		//limparCampos();
		
		return "";
		
	}
	// Upload de imagem
	public void upload(FileUploadEvent image) {
		String imagem = "data:image/png;base64," + DatatypeConverter
				.printBase64Binary(image.getFile().getContents());
		usuPessoa.setImagem(imagem);
	}
	
	//Download da imagem
	public void download() throws IOException {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
		String fileDownloadID = params.get("fileDownloadId");
		
		UsuarioPessoa pessoa = daoGenerico.pesquisar2(Long.parseLong(fileDownloadID), UsuarioPessoa.class);
		byte[] imagem = new Base64().decodeBase64(pessoa.getImagem().split("\\,")[1]);
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();
		
		response.addHeader("Content-Disposition", "attachment; filename=download.png");
		response.setContentType("application/octet-stream");
		response.setContentLength(imagem.length);
		response.getOutputStream().write(imagem);
		response.getOutputStream().flush();
		FacesContext.getCurrentInstance().responseComplete();
	
	}
	
	//Adicionando Emails aos usuaários
	public void addEmail() {
		emailUser.setUsuariopessoa(usuPessoa);
		emailUser = daoEmail.salvarMerge(emailUser);
		usuPessoa.getEmails_user().add(emailUser);
		emailUser = new EmailUser();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "E-mail adicionado!!!"));
		
	}
	//Removendo email do usuário
	public void removerEmail() throws Exception {
		String codEmail = FacesContext.getCurrentInstance().getExternalContext().
				getRequestParameterMap().get("codEmail");
		EmailUser removendo = new EmailUser();
		removendo.setId(Long.parseLong(codEmail));
		daoEmail.deletePorId(removendo);
		usuPessoa.getEmails_user().remove(removendo);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "E-mail Removido!!!"));
	}
	
	public String removerUser() {
		try {
			daoGenerico.removerUsuario(usuPessoa);
			list.list.remove(usuPessoa);
			init();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Excluído com sucesso!!"));
			
		} catch (Exception e) {
			if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Usuário não pode ser excluído, pois, possui telefone dependente!"));
			}else {
				e.printStackTrace();
			}
			
		}
	
	usuPessoa = new UsuarioPessoa();
		return "";
	}
	
	public String limparCampos() {
		
		usuPessoa = new UsuarioPessoa();
		return "";
		
	}
	
	//Consumindo apiRest
	public void pesquisaCep(AjaxBehaviorEvent event) {
		try {
			URL url = new URL("https://viacep.com.br/ws/"+usuPessoa.getCep()+"/json/");
			URLConnection connection = url.openConnection();
			InputStream inp = connection.getInputStream();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(inp, "UTF-8"));
			String cep ="";
			StringBuilder jsonCep = new StringBuilder();
			
			while((cep = buffer.readLine()) != null) {
				jsonCep.append(cep);
			}
			
			UsuarioPessoa gsonAux = new Gson().fromJson(jsonCep.toString(), UsuarioPessoa.class);
			
			usuPessoa.setCep(gsonAux.getCep());
			usuPessoa.setLogradouro(gsonAux.getLogradouro());
			usuPessoa.setComplemento(gsonAux.getComplemento());
			usuPessoa.setBairro(gsonAux.getBairro());
			usuPessoa.setLocalidade(gsonAux.getLocalidade());
			usuPessoa.setUf(gsonAux.getUf());
			usuPessoa.setIbge(gsonAux.getIbge());
			usuPessoa.setGia(gsonAux.getGia());
			usuPessoa.setDdd(gsonAux.getDdd());
			usuPessoa.setSiafi(gsonAux.getSiafi());
			
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Erro na validação!!"));
			
			//mostrarMensagen("Erro na validação do CEP...");
		}
	}
	
	public void pesquisarUser() {
		list.pesquisar(campoPesquisar);
		montarGrafico();
	}
	
	

}
