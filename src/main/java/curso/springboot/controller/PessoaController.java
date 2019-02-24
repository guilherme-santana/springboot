package curso.springboot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Pessoa;
import curso.springboot.repository.PessoaRepository;

@Controller
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;

	@RequestMapping(method=RequestMethod.GET, value="/cadastropessoa")
	public ModelAndView inicio(){
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		//passa a lista de objetos vazia para poder carregar a tela de cadastro sem erros
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
	}

	//requestMapping intercepta a requisição sempre que a ação "salvarpessoa" for acionada
	@RequestMapping(method=RequestMethod.POST, value="**/salvarpessoa")
	public ModelAndView salvar(Pessoa pessoa){
		//persiste no banco
		pessoaRepository.save(pessoa);
		//instanciar o view para injetar os dados na tela
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		//Obtem do banco a lista de pessoas
		Iterable<Pessoa> PessoasIt = pessoaRepository.findAll();
		//adiciona na viw a lista de pessoas. Atributo pessoas criado no html
		andView.addObject("pessoas", PessoasIt);
		//passa a lista de objetos vazia para poder carregar a tela de cadastro sem erros
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	}

	@RequestMapping(method= RequestMethod.GET, value="/listapessoas")
	public ModelAndView listarPessoas(){
		//instanciar o view para injetar os dados na tela
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		//Obtem do banco a lista de pessoas
		Iterable<Pessoa> PessoasIt = pessoaRepository.findAll();
		//adiciona na viw a lista de pessoas. Atributo pessoas criado no html
		andView.addObject("pessoas", PessoasIt);
		//passa a lista de objetos vazia para poder carregar a tela de cadastro sem erros
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	}

	@GetMapping("/editarpessoa/{idpessoa}")
	//@PathVariable serve para pegar a variavel que vem na url
	public ModelAndView editar (@PathVariable("idpessoa") Long idpessoa){
		//carrega os dados de Pessoa do banco
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		//instanciar o view para injetar os dados na tela
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		//adiciona a pessoa obtida ao objeto pessoaobj
		modelAndView.addObject("pessoaobj", pessoa.get());
		return modelAndView;
	}

	@GetMapping("/excluirpessoa/{idpessoa}")
	//@PathVariable serve para pegar a variavel que vem na url
	public ModelAndView excluir (@PathVariable("idpessoa") Long idpessoa){
		// Método pronto para excluir pessoa
		pessoaRepository.deleteById(idpessoa);

		//instanciar o view para injetar os dados na tela
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		//Obtem do banco a lista de pessoas
		Iterable<Pessoa> PessoasIt = pessoaRepository.findAll();
		//adiciona na viw a lista de pessoas. Atributo pessoas criado no html
		andView.addObject("pessoas", PessoasIt);
		//passa a lista de objetos vazia para poder carregar a tela de cadastro sem erros
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	}




}
