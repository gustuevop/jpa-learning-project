package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Pedido;

public class PedidoDao {

	private EntityManager em;

	public PedidoDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}
	
	public BigDecimal valorTotalVendido() {
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
		return this.em.createQuery(jpql, BigDecimal.class).getSingleResult();
	}
	
	public List<Object[]> relatorioDeVendas() {
		String jpql = "SELECT produto.nome, SUM(item.quantidade) as quantidade, MAX(pedido.data) "
					+ "FROM Pedido pedido "
					+ "JOIN pedido.itens item "
					+ "JOIN item.produto produto "
					+ "GROUP BY produto.nome "
					+ "ORDER BY quantidade DESC";
		return em.createQuery(jpql, Object[].class).getResultList();
	}

}