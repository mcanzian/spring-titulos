package com.mcanzian.cobranca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mcanzian.cobranca.model.StatusTitulo;
import com.mcanzian.cobranca.model.Titulo;
import com.mcanzian.cobranca.repository.TituloRepository;

@Service
public class CadastroTituloService {

	@Autowired
	private TituloRepository titulos;
	
	public void salvar(Titulo titulo) {
		try {
			titulos.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido.");
		}
	}
	
	public void excluir(Long id) {
		titulos.delete(id);
	}

	public String atualizarStatus(Long id) {
		Titulo titulo = titulos.findOne(id);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		titulos.save(titulo);
		
		return StatusTitulo.RECEBIDO.name();
	}
	
}
