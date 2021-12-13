package persistence.commons;

import java.util.List;

public interface GenericDAO<T> {

	public T find(Integer id);
	public List<T> findAll();
	public int insert(T t);
	public int update(T t);
	public int delete(Integer id);
}
