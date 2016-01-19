package com.mcanzian.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mcanzian.cobranca.model.StatusTitulo;
import com.mcanzian.cobranca.model.Titulo;
import com.mcanzian.cobranca.repository.TituloRepository;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	@Autowired
	private TituloRepository titulos;
	private static String CADASTRO_VIEW = "CadastroTitulo";

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("titulo", new Titulo());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors())
			return CADASTRO_VIEW;
		
		titulos.save(titulo);
		attributes.addFlashAttribute("mensagemSucesso", "Título salvo com sucessso.");
		
		return "redirect:/titulos/novo";
	}
	
	@RequestMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("ListaTitulos");
		
		List<Titulo> todosTitulos = titulos.findAll();
		mv.addObject("titulos", todosTitulos);
		
		return mv;
	}
	
	@RequestMapping("{id}")
	public ModelAndView editar(@PathVariable("id") Titulo titulo) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		
		mv.addObject("titulo", titulo);
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
		titulos.delete(id);
		attributes.addFlashAttribute("mensagemSucesso", "Título excluido com sucesso.");
		return "redirect:/titulos";
	}
	
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}
	
}
