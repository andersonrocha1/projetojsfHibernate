package aprendo.maven.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import aprendo.maven.hibernate.HibernateUtil;

public class DaoGenerico<E> {

	private EntityManager entityManager = HibernateUtil.geEntityManager();

	public void salvar(E entidade) {

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entidade);
		transaction.commit();

	}

	public E salvarMerge(E entidade) {// atualiza os dados e salva

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E entidadeMerge = entityManager.merge(entidade);
		transaction.commit();

		return entidadeMerge;

	}

	public E pesquisar(E entidade) {

		Object id = HibernateUtil.getPrimaryKey(entidade);

		E e = (E) entityManager.find(entidade.getClass(), id);

		return e;

	}

	public E pesquisar2(Long id, Class<E> entidade) {
		entityManager.clear();
		E e = (E) entityManager.createQuery("from " + entidade.getSimpleName() + " where id = " + id).getSingleResult();

		return e;

	}
	
	
	public void deletePorId(E entidade) throws Exception {
		Object id = HibernateUtil.getPrimaryKey(entidade);
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		entityManager.createNativeQuery(
				"delete from " + entidade.getClass().getSimpleName().toLowerCase() +
				" where id =" + id).executeUpdate();//Deleta
		
		transaction.commit();
		
	}
	
	public List<E>listarTodos(Class<E> entidade){
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		List<E> lista = entityManager.createQuery("from " + entidade.getName()).getResultList();
		
		transaction.commit();
		
		return lista;
		
		
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
