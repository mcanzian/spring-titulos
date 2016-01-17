package com.mcanzian.cobranca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mcanzian.cobranca.model.Titulo;
import com.mcanzian.cobranca.repository.TituloRepository;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	@Autowired
	private TituloRepository titulos;

	@RequestMapping("/novo")
	public String novo() {
		return "CadastroTitulo";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(Titulo titulo) {
		titulos.save(titulo);
		
		ModelAndView mv = new ModelAndView("CadastroTitulo");
		mv.addObject("mensagemSucesso", "Título salvo com sucessso.");
		
		return mv;
	}
	
}
