package com.kaioferreira.crud.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kaioferreira.crud.data.vo.ProdutoVO;
import com.kaioferreira.crud.entity.Produto;
import com.kaioferreira.crud.exception.ResourceNotFoundException;
import com.kaioferreira.crud.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	private final ProdutoRepository produtoRepository;
	
	@Autowired
	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	public ProdutoVO create(ProdutoVO produtoVO) {
		ProdutoVO proVoRetorno = ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
		return proVoRetorno;
	}
	
	public Page<ProdutoVO> findAll(Pageable pageable) {
		var page = produtoRepository.findAll(pageable);
		return page.map(this::convertToProdutoVO);
	}
	
	private ProdutoVO convertToProdutoVO(Produto produto) {
		return ProdutoVO.create(produto);
	}
	
	public ProdutoVO findById(Long id) {
		var entity = produtoRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No records found for this ID"));
		return ProdutoVO.create(entity);
	}
	
	public ProdutoVO update(ProdutoVO produtoVO) {
		final Optional<Produto> optionalProduto = produtoRepository.findById(produtoVO.getId());
		
		if(!optionalProduto.isPresent()) {
			new ResourceNotFoundException("No records found for this ID");
		}
		
		return ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
	}
	
	public void delete(Long id) {
		var entity = produtoRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No records found for this ID"));
		
		produtoRepository.delete(entity);
	}
	
}
