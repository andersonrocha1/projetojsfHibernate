package aprendo.maven.hibernate;

import java.util.List;

import org.junit.Test;

import aprendo.maven.hibernate.dao.DaoGenerico;
import aprendo.maven.hibernate.model.TelefoneUser;
import aprendo.maven.hibernate.model.UsuarioPessoa;

public class TesteHibernate {
	
	
	@Test
	public void testeHibernateUtil() {
		
		DaoGenerico<UsuarioPessoa> daogenerico = new DaoGenerico<UsuarioPessoa>();
		
		UsuarioPessoa usuPessoa = new UsuarioPessoa();
		
		usuPessoa.setNome("Para Testes4");
		usuPessoa.setSobrenome("Para Teste");
		usuPessoa.setLogin("teste.exclusao");
		usuPessoa.setSenha("123456");
		usuPessoa.setIdade(40);
		
		daogenerico.salvar(usuPessoa);
		
	}
	
	@Test
	public void testeBuscar() {
		DaoGenerico<UsuarioPessoa> daoGenerico = new DaoGenerico<UsuarioPessoa>();
		
		UsuarioPessoa usuPessoa = new UsuarioPessoa();
		usuPessoa.setId(1L);
		
		usuPessoa = daoGenerico.pesquisar(usuPessoa);
		
		System.out.println(usuPessoa);
		
	}
	
	@Test
	public void testeBuscar2() {
		DaoGenerico<UsuarioPessoa> daoGenerico = new DaoGenerico<UsuarioPessoa>();
	
		
		UsuarioPessoa usuPessoa = daoGenerico.pesquisar2(1L, UsuarioPessoa.class);
		
		System.out.println(usuPessoa);
		
	}
	
	@Test
	public void testeUpdate() {
		DaoGenerico<UsuarioPessoa> daoGenerico = new DaoGenerico<UsuarioPessoa>();
		
		UsuarioPessoa usuPessoa = daoGenerico.pesquisar2(1L, UsuarioPessoa.class);

		usuPessoa.setIdade(40);
		
		daoGenerico.salvarMerge(usuPessoa);

		System.out.println(usuPessoa);
		
	}
	
	@Test
	public void testeDelete() throws Exception {
		DaoGenerico<UsuarioPessoa> daoGenerico = new DaoGenerico<UsuarioPessoa>();
		
		UsuarioPessoa usuPessoa = daoGenerico.pesquisar2(6L, UsuarioPessoa.class);

		daoGenerico.deletePorId(usuPessoa);

		//System.out.println(usuPessoa);
		
	}
	
	@Test
	public void testeConsultaTodos() {//Lista todos antes do getEntityManager
		DaoGenerico<UsuarioPessoa> daoGenerico = new DaoGenerico<UsuarioPessoa>();
		
		List<UsuarioPessoa> list = daoGenerico.listarTodos(UsuarioPessoa.class);
		
		for (UsuarioPessoa usuarioPessoa : list) {
			
			System.out.println(usuarioPessoa);
			
			System.out.println("*************************************************************");
	
		}

	}
	
	@Test
	public void testeQueryList() {// Lista todos, criada fora do DAO
		DaoGenerico<UsuarioPessoa> daoGenerico = new DaoGenerico<UsuarioPessoa>();
		
		List<UsuarioPessoa> list = daoGenerico.getEntityManager().createQuery("from UsuarioPessoa").getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			
			System.out.println(usuarioPessoa);
			
			System.out.println("******************************************************************************************************");
		}
	}
	
	@Test
	public void testeQueryList2() {// Lista com uma condição WHERE
		DaoGenerico<UsuarioPessoa> daoGenerico = new DaoGenerico<UsuarioPessoa>();
		
		List<UsuarioPessoa> list = daoGenerico.getEntityManager().createQuery("from UsuarioPessoa where nome= 'Anderson'").getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			
			System.out.println(usuarioPessoa);
			
			System.out.println("******************************************************************************************************");
		}
	}
	
	@Test
	public void testeQueryList3() {// Lista com uma ordenação e máximos de resultados definidos.
		DaoGenerico<UsuarioPessoa> daoGenerico = new DaoGenerico<UsuarioPessoa>();
		
		List<UsuarioPessoa> list = daoGenerico.getEntityManager().createQuery("from UsuarioPessoa order by id")
				.setMaxResults(5)
				.getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			
			System.out.println(usuarioPessoa);
			
			System.out.println("******************************************************************************************************");
		}
	}
	
	@Test
	public void testeQueryListParametro() {//Lista com parâmetros. 
		DaoGenerico<UsuarioPessoa> daoGenerico = new DaoGenerico<UsuarioPessoa>();
		
		List<UsuarioPessoa> list = daoGenerico.getEntityManager()
				.createQuery("from UsuarioPessoa where nome = :nome or sobrenome = :sobrenome")
				.setParameter("nome", "Pedro")
				.setParameter("sobrenome", "Alves Fernandes")
				.getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			
			System.out.println("******************************************************************************************************");
		}
	}
	
	@Test
	public void testeQuerySomaIdades() {//Soma todas as idades
		DaoGenerico<UsuarioPessoa> daoGenerico = new DaoGenerico<UsuarioPessoa>();
		
		Long somaIdade = (Long) daoGenerico.getEntityManager()
				.createQuery("select sum(u.idade) from UsuarioPessoa u")
				.getSingleResult();
		
		System.out.println("Soma dos Idades : " +somaIdade);
		
	}
	
	@Test
	public void testeQuerySomaId() {//Soma todos os Ids
		DaoGenerico<UsuarioPessoa> daoGenerico = new DaoGenerico<UsuarioPessoa>();
		
		Long somaId = (Long) daoGenerico.getEntityManager()
				.createQuery("select sum(u.id) from UsuarioPessoa u")
				.getSingleResult();
		
		System.out.println("Soma dos IDs : " +somaId);
		
	}
	
	@Test
	public void testeNamedQuery1() {// Lista todos definindo uma anotação NamedQueries na classe
		
		DaoGenerico<UsuarioPessoa> daoGenerico = new DaoGenerico<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGenerico.getEntityManager().createNamedQuery("UsuarioPessoa.todos")
				.getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}
	}
	
	@Test
	public void testeNamedQuery2() {
	/*
	 * Lista todos definindo uma anotação NamedQueries na classe passando parametro 
	*/
		
		DaoGenerico<UsuarioPessoa> daoGenerico = new DaoGenerico<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGenerico.getEntityManager().createNamedQuery("UsuarioPessoa.buscaPorNome")
				.setParameter("nome", "Anderson")
				.getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}
	}
	
	@Test
	public void testeGravaTelefone() {
		DaoGenerico daoGenerico = new DaoGenerico();
		
		UsuarioPessoa usuPessoa = (UsuarioPessoa) daoGenerico.pesquisar2(2L, UsuarioPessoa.class);
		
		TelefoneUser telefoneUser = new TelefoneUser();
		telefoneUser.setNumero("(27) 3343-2741");
		telefoneUser.setTipo("Residencial");
		telefoneUser.setUsuariopessoa(usuPessoa);
		
		daoGenerico.salvar(telefoneUser);
	}
	
	@Test
	public void testeConsultaTelefonePessoa() {
		DaoGenerico daoGenerico = new DaoGenerico();
		
		UsuarioPessoa usuPessoa = (UsuarioPessoa) daoGenerico.pesquisar2(1L, UsuarioPessoa.class);
		
		for (TelefoneUser telefoneUser : usuPessoa.getTelefones_user()) {
			
			System.out.println("NÚMERO : " +telefoneUser.getNumero());
			System.out.println("TIPO : " +telefoneUser.getTipo());
			System.out.println("USUÁRIO : " +telefoneUser.getUsuariopessoa().getNome());
			System.out.println("******************************************************************************************************");
			
		}
		

	}
}
